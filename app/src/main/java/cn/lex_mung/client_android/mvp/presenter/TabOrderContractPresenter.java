package cn.lex_mung.client_android.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.smtt.sdk.TbsReaderView;
import com.yalantis.ucrop.util.FileUtils;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.List;

import cn.lex_mung.client_android.app.BundleTags;
import cn.lex_mung.client_android.app.Constants;
import cn.lex_mung.client_android.app.DataHelperTags;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocGetEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.DocUploadEntity;
import cn.lex_mung.client_android.mvp.model.entity.order.ListBean;
import cn.lex_mung.client_android.mvp.ui.activity.X5WebActivity;
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
import me.zl.mvp.utils.DataHelper;
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
    ConstraintLayout cl_help;
    TabOrderContractAdapter adapter;
    private int pageNum = 1;
    private int totalNum;//总
    private String helpLink;

    UserInfoDetailsEntity userInfoDetailsEntity;

    @Inject
    public TabOrderContractPresenter(TabOrderContractContract.Model model, TabOrderContractContract.View rootView) {
        super(model, rootView);
    }

    public void onCreate(SmartRefreshLayout smartRefreshLayout, String orderNo) {
        FILE_CACHE_PATH = FileUtil2.getFolder(mApplication, Constants.FILE_PATH).getAbsolutePath();

        this.smartRefreshLayout = smartRefreshLayout;
        this.orderNo = orderNo;

        initAdapter();

        getList(false);

        userInfoDetailsEntity = new Gson().fromJson(DataHelper.getStringSF(mApplication, DataHelperTags.USER_INFO_DETAIL), UserInfoDetailsEntity.class);
    }

    private void initAdapter() {
        adapter = new TabOrderContractAdapter(mImageLoader);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (isFastClick()) return;
            ListBean bean = adapter.getItem(position);
            if (bean == null) return;
            fileClick(bean.getName(), bean.getLink());
            if (bean.getCreate_member_id() != userInfoDetailsEntity.getMemberId() && bean.getIs_read() == 0) {
                docRead(position, bean.getRepository_id());
            }
        });
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

    public void fileClick(String fileName, String fileLink) {
        String filePath = FILE_CACHE_PATH + File.separator + fileName;
        if (!FileUtil2.isFileExist(new File(filePath))) {
            mRootView.showLoading("下载中...");
            FileUtil2.downloadFile3(fileLink
                    , filePath
                    , new FileUtil2.DowloadListener() {
                        @Override
                        public void onSuccess() {
                            mRootView.showMessage("下载成功");
                            mRootView.hideLoading();
                            LogUtil.e("下载成功...打开");
                            previewFile(filePath, fileName);
                        }

                        @Override
                        public void onFailed() {
                            mRootView.showMessage("下载失败");
                            mRootView.hideLoading();
                        }
                    });
        } else {
            LogUtil.e("直接打开");
            previewFile(filePath, fileName);
        }
    }

    //通过腾讯---预览文件
    public void previewFile(String filePath, String fileName) {
        Bundle bundle = new Bundle();
        bundle.putString("x5web_file_path", filePath);
        bundle.putString("x5web_file_name", fileName);
        mRootView.launchActivity(new Intent(mApplication, X5WebActivity.class), bundle);
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
                .retryWhen(new RetryWithDelay(0, 0))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DocUploadEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DocUploadEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            if (!FileUtil2.isFileExist(new File(FILE_CACHE_PATH + File.separator + file.getName()))) {
                                mRootView.showLoading("");
                                LogUtil.e("不存在");
                                FileUtil2.copyFile(file.getAbsolutePath(), FILE_CACHE_PATH + File.separator + file.getName());
                                mRootView.hideLoading();
                            } else {
                                LogUtil.e("已存在");
                            }
                            getList(false);
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        try {
                            if (t instanceof SocketTimeoutException) {//请求超时
                                mRootView.showMessage("网络连接超时");
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        } finally {
                            LogUtil.e("error:" + t.getMessage());
                        }
                    }
                });
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    //点击列表item，在自己的目录下查看有没有，没有的话下载，下载完成后进入查看器

    public void getList(boolean isAdd) {
        mModel.docGet(orderNo, pageNum)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(0, 0))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DocGetEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DocGetEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            totalNum = baseResponse.getData().getPaginated().getPages();
                            pageNum = baseResponse.getData().getPaginated().getPage_num();

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
                            setHelpHide(helpLink = baseResponse.getData().getHelp_link());
                        } else {
                            mRootView.showMessage(baseResponse.getMessage());
                        }
                    }
                });
    }

    public void docRead(int position, String repositoryId) {
        mModel.docRead(repositoryId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            adapter.getData().get(position).setIs_read(3);
                            adapter.notifyItemChanged(position);
                        }
                    }
                });
    }

    public String getHelpLink() {
        return helpLink;
    }

    boolean isHelpHide;//第一次进入才有用

    private void setHelpHide(String helpLink) {
        if (isHelpHide)
            return;
        if (!TextUtils.isEmpty(helpLink)) {
            cl_help.setVisibility(View.VISIBLE);
        } else {
            cl_help.setVisibility(View.GONE);
        }
        isHelpHide = true;
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
                        docUpload(orderNo, new File(path));

                    } else {
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

    public void setHelpView(ConstraintLayout cl_help) {
        this.cl_help = cl_help;
    }
}
