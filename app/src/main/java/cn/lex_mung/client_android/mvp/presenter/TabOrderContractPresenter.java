package cn.lex_mung.client_android.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yalantis.ucrop.util.FileUtils;

import java.io.File;
import java.util.List;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.Constants;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocUploadEntity;
import cn.lex_mung.client_android.mvp.ui.adapter.MyCouponsAdapter;
import cn.lex_mung.client_android.mvp.ui.adapter.TabOrderContractAdapter;
import cn.lex_mung.client_android.utils.FileUtil2;
import cn.lex_mung.client_android.utils.LogUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.base.App;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.FragmentScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;
import javax.sql.RowSetListener;

import cn.lex_mung.client_android.mvp.contract.TabOrderContractContract;
import me.zl.mvp.utils.FileUtil;
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

    private String orderNo;

    private String FILE_CACHE_PATH;

    private SmartRefreshLayout smartRefreshLayout;
    TabOrderContractAdapter adapter;
    private int pageNum;
    private int totalNum;


    @Inject
    public TabOrderContractPresenter(TabOrderContractContract.Model model, TabOrderContractContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout,String orderNo) {
        FILE_CACHE_PATH = FileUtil2.getFolder(mApplication, Constants.FILE_PATH).getAbsolutePath();

        this.smartRefreshLayout = smartRefreshLayout;
        this.orderNo = orderNo;

        initAdapter();

        getList(false);
    }

    private void initAdapter() {
        adapter = new TabOrderContractAdapter(mImageLoader);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageNum < totalNum) {
                    pageNum = pageNum + 1;
                    getList(true);
                } else {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getList(false);
            }
        });
        mRootView.initRecyclerView(adapter);
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
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DocUploadEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DocUploadEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if(!FileUtil2.isFileExist(new File(FILE_CACHE_PATH + File.separator + file.getName()))){
                                LogUtil.e("不存在");
                                FileUtil2.copyFile(file.getAbsolutePath() ,FILE_CACHE_PATH + File.separator + file.getName());
                            }else{
                                LogUtil.e("已存在");
                            }
                            mRootView.hideLoading();
                        }else{
                            mRootView.showMessage(baseResponse.getMessage());

                            mRootView.hideLoading();
                        }
                    }
                });
    }

    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }

    //点击列表item，在自己的目录下查看有没有，没有的话下载，下载完成后进入查看器

    public void getList(boolean isAdd){
        mModel.docGet(orderNo,1)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DocGetEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DocGetEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPaginated().getPage_num();
                            pageNum = baseResponse.getData().getPaginated().getPages();

                            if (isAdd) {
                                adapter.addData(baseResponse.getData().getList());
                                smartRefreshLayout.finishLoadMore();
                            } else {
                                smartRefreshLayout.finishRefresh();
                                adapter.setNewData(baseResponse.getData().getList());
                                if (totalNum == pageNum) {
                                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                }
                            }
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
                        docUpload(orderNo,new File(path));

                    }else{
                        mRootView.showMessage("无效的文件类型");
                    }

                }
                break;
        }
    }


    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            mRootView.getFragment().startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            LogUtil.e("Please install a File Manager.");
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
