package cn.lex_mung.client_android.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;

import com.yalantis.ucrop.util.FileUtils;

import java.io.File;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocUploadEntity;
import cn.lex_mung.client_android.utils.LogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.lex_mung.client_android.mvp.contract.TabOrderContractContract;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@FragmentScope
public class TabOrderContractPresenter extends BasePresenter<TabOrderContractContract.Model, TabOrderContractContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TabOrderContractPresenter(TabOrderContractContract.Model model, TabOrderContractContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 上传图片
     *
     * @param file 压缩后的图片
     */
    private void docUpload(String order_no, File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), order_no);
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        mModel.docUpload(requestBody, MultipartBody.Part.createFormData("file", file.getName(), requestFile))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DocUploadEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DocUploadEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
//                            iconImage = baseResponse.getData().getDburl();
//                            mRootView.setAvatar(saveFile.getPath());
//                            isUploadNewAvatar = true;

                            //刷新列表
                            //存在自己的目录下

                        }else{
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    //点击列表item，在自己的目录下查看有没有，没有的话下载，下载完成后进入查看器


    public void getList(String order_no){
        mModel.docGet(order_no)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<DocGetEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<DocGetEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
//                            iconImage = baseResponse.getData().getDburl();
//                            mRootView.setAvatar(saveFile.getPath());
//                            isUploadNewAvatar = true;




                        }else{
                            mRootView.showMessage(baseResponse.getMessage());


                        }
                    }
                });
    }

    //-----文件选择
    private static final int FILE_SELECT_CODE = 0;
    /**
     * 选择图片回调
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        数据h
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    LogUtil.e("File Uri: " + uri.toString());
                    // Get the path
                    String path = FileUtils.getPath(mRootView.getFragment().getActivity(), uri);
                    LogUtil.e("File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload

                    //"doc"|"docx"|"ppt"|"pptx"|"xls"|"xlsx"|"pdf"
                    if (path.endsWith("png")
                            || path.endsWith("jpg")
                            || path.endsWith("jpeg")
                            || path.endsWith("doc")
                            || path.endsWith("docx")
                            || path.endsWith("ppt")
                            || path.endsWith("pptx")
                            || path.endsWith("xls")
                            || path.endsWith("xlsx")
                            || path.endsWith("pdf")) {
                        docUpload("aaaaaa111",new File(path));

                    }else{
                        mRootView.showMessage("无效的文件类型");
                    }

                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
