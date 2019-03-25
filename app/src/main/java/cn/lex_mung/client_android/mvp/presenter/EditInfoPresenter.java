package cn.lex_mung.client_android.mvp.presenter;

import android.app.Application;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import me.zl.mvp.integration.AppManager;
import me.zl.mvp.di.scope.ActivityScope;
import me.zl.mvp.mvp.BasePresenter;
import me.zl.mvp.http.imageloader.ImageLoader;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.DataHelper;
import me.zl.mvp.utils.LogUtils;
import me.zl.mvp.utils.PermissionUtil;
import me.zl.mvp.utils.RegexUtils;
import me.zl.mvp.utils.RxLifecycleUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cn.lex_mung.client_android.mvp.contract.EditInfoContract;
import cn.lex_mung.client_android.mvp.model.entity.BaseResponse;
import cn.lex_mung.client_android.mvp.model.entity.IndustryEntity;
import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;
import cn.lex_mung.client_android.mvp.model.entity.UploadImageEntity;
import cn.lex_mung.client_android.mvp.model.entity.UserInfoDetailsEntity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

@ActivityScope
public class EditInfoPresenter extends BasePresenter<EditInfoContract.Model, EditInfoContract.View> {
    private static final int REQUEST_CODE_CAPTURE_CAMERA = 101;
    private static final int REQUEST_CODE_PICK_IMAGE = 102;

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RxPermissions mRxPermissions;

    private UserInfoDetailsEntity entity;
    private Uri imageUri;//拍照图片的路径
    private File saveFile;//裁剪完成的图片
    private String iconImage;//上传完成之后的地址
    private boolean isUploadNewAvatar = false;//是否选择了新头像

    private List<RegionEntity> list;
    private List<String> allProv = new ArrayList<>();//所有的省
    private Map<String, List<String>> cityMap = new HashMap<>();//key:省p---value:市n  value是一个集合
    private Map<String, List<String>> areaMap = new HashMap<>();//key:市n---value:区s    区也是一个集合
    private String province = "";
    private String city = "";
    private String area = "";
    private String industry = "";
    private String region = "";

    private List<IndustryEntity> industryEntityList = new ArrayList<>();
    private List<String> industryStringList = new ArrayList<>();

    @Inject
    public EditInfoPresenter(EditInfoContract.Model model, EditInfoContract.View rootView) {
        super(model, rootView);
    }

    public List<String> getAllProv() {
        return allProv;
    }

    public Map<String, List<String>> getCityMap() {
        return cityMap;
    }

    public Map<String, List<String>> getAreaMap() {
        return areaMap;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRegion() {
        if ("钓鱼岛".equals(province)) {
            return region = province;
        } else {
            return region = province + "-" + city + "-" + area;
        }
    }

    public List<String> getIndustryStringList() {
        return industryStringList;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustry() {
        return industry;
    }

    public void onCreate() {
        new Thread(this::initJsonData).start();
        getIndustryList();
    }

    private void getIndustryList() {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", 0);
        mModel.getIndustryList(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<IndustryEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<IndustryEntity>> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            industryEntityList.clear();
                            industryEntityList.addAll(baseResponse.getData());
                            for (IndustryEntity entity : industryEntityList) {
                                industryStringList.add(entity.getIndustryName());
                            }
                            getUserInfoDetail();
                        }
                    }
                });
    }

    private void getUserInfoDetail() {
        mModel.getUserInfoDetail()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfoDetailsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfoDetailsEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            setEntity(baseResponse.getData());
                        }
                    }
                });
    }

    public void setEntity(UserInfoDetailsEntity entity) {
        this.entity = entity;
        try {
            if (!TextUtils.isEmpty(entity.getIconImage())) {
                mRootView.setAvatar(entity.getIconImage());
            }
            if (!TextUtils.isEmpty(entity.getMemberName())) {
                mRootView.setUserName(entity.getMemberName());
            }
            if (entity.getSex() != 0) {
                mRootView.setUserSex(entity.getSex() == 1 ? "男" : "女");
            }
            if (!TextUtils.isEmpty(entity.getBirthday())
                    && !"1970-01-01 00:00:01".equals(entity.getBirthday())) {
                mRootView.setBirthday(entity.getBirthday().split(" ")[0]);
            }
            if (!TextUtils.isEmpty(entity.getAddressExtend().getProvince())
                    && !TextUtils.isEmpty(entity.getAddressExtend().getCity())
                    && !TextUtils.isEmpty(entity.getAddressExtend().getArea())) {
                province = entity.getAddressExtend().getProvince();
                city = entity.getAddressExtend().getCity();
                area = entity.getAddressExtend().getArea();
                region = province + "-" + city + "-" + area;
                mRootView.setRegion(region);
            }
            if (!TextUtils.isEmpty(entity.getIndustryName())) {
                industry = entity.getIndustryName();
                mRootView.setIndustryName(industry);
            }
            if (!TextUtils.isEmpty(entity.getInstitutionName())) {
                mRootView.setInstitutionName(entity.getInstitutionName());
            }
            if (!TextUtils.isEmpty(entity.getMemberPositionName())) {
                mRootView.setPositionName(entity.getMemberPositionName());
            }
            if (!TextUtils.isEmpty(entity.getEmail())) {
                mRootView.setEmail(entity.getEmail());
            }
        } catch (Exception ignored) {
        }
    }

    public void getLaunchCameraPermission() {
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.showSelectPhotoDialog();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("您拒绝了权限，无法打开摄像头/本地相册");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showToAppInfoDialog();
            }
        }, mRxPermissions, mErrorHandler);
    }

    /**
     * 拍照
     */
    public void getImageFromCamera() {
        Intent intent;
        File file = new File(AppUtils.obtainAppComponentFromContext(mApplication).cacheFile().getAbsoluteFile() + "/user_avatar_" + System.currentTimeMillis() + ".png");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            imageUri = mRootView.getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageUri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        mRootView.launchActivity(intent, REQUEST_CODE_CAPTURE_CAMERA);
    }

    /**
     * 本地相册
     */
    public void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        mRootView.launchActivity(intent, REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * 裁剪
     */
    private void crop(Uri uri) {
        String destinationFileName = "user_avatar_" + System.currentTimeMillis() + ".png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(mRootView.getActivity().getCacheDir(), destinationFileName)));
        uCrop = uCrop.withAspectRatio(500, 500);
        uCrop = uCrop.withMaxResultSize(500, 500);
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        uCrop.start(mRootView.getActivity());
    }

    /**
     * 选择图片回调
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        数据
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {//从相册
            if (data == null || data.getData() == null) {
                mRootView.showMessage("获取照片失败!请重试");
                return;
            }
            crop(data.getData());
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMERA && resultCode == RESULT_OK) {//拍照
            if (imageUri == null) {
                mRootView.showMessage("获取照片失败!请重试");
                return;
            }
            crop(imageUri);
        } else if (requestCode == UCrop.REQUEST_CROP) {//裁剪
            try {
                Uri resultUri = UCrop.getOutput(data);
                String downloadsDirectoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                String filename = String.format("%d_%s", Calendar.getInstance().getTimeInMillis(), resultUri.getLastPathSegment());
                saveFile = new File(downloadsDirectoryPath, filename);
                FileInputStream inStream = new FileInputStream(new File(resultUri.getPath()));
                FileOutputStream outStream = new FileOutputStream(saveFile);
                FileChannel inChannel = inStream.getChannel();
                FileChannel outChannel = outStream.getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inStream.close();
                outStream.close();
                compressionImage(saveFile);
            } catch (Exception ignored) {
                mRootView.showMessage("获取照片失败!请重试");
            }
        }
    }

    /**
     * 压缩图片
     */
    private void compressionImage(File file) {
        Luban.with(mApplication)
                .load(file)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        mRootView.showLoading("");
                    }

                    @Override
                    public void onSuccess(File file) {
                        mRootView.hideLoading();
                        uploadImage(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRootView.hideLoading();
                    }
                })
                .launch();
    }

    /**
     * 上传图片
     *
     * @param file 压缩后的图片
     */
    private void uploadImage(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), "2");
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        mModel.uploadImage(requestBody, MultipartBody.Part.createFormData("file", file.getName(), requestFile))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UploadImageEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UploadImageEntity> baseResponse) {
                        if (baseResponse.isSuccess()) {
                            iconImage = baseResponse.getData().getDburl();
                            mRootView.setAvatar(saveFile.getPath());
                            isUploadNewAvatar = true;
                        }
                    }
                });
    }

    /**
     * 从assert文件夹中获取json数据
     */
    private void initJsonData() {
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = mApplication.getAssets().open("city.json");//打开json数据
            byte[] by = new byte[is.available()];//转字节
            int len;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, StandardCharsets.UTF_8));//根据字节长度设置编码
            }
            is.close();//关闭流
            list = new Gson().fromJson(sb.toString(), new TypeToken<List<RegionEntity>>() {
            }.getType());
            for (RegionEntity entity : list == null ? new ArrayList<RegionEntity>() : list) {
                allProv.add(entity.getName());//封装所有的省
                //所有的市
                List<String> allCity = new ArrayList<>();//所有市的长度
                for (RegionEntity entity1 : entity.getChild() == null ? new ArrayList<RegionEntity>() : entity.getChild()) {
                    allCity.add(entity1.getName());//封装市集合
                    List<String> allArea = new ArrayList<>();//所有的区
                    for (RegionEntity entity2 : entity1.getChild() == null ? new ArrayList<RegionEntity>() : entity1.getChild()) {
                        allArea.add(entity2.getName());//封装起来
                    }
                    areaMap.put(entity1.getName(), allArea);//某个市取出所有的区集合
                }
                cityMap.put(entity.getName(), allCity);//某个省取出所有的市,
            }
        } catch (Exception e) {
            LogUtils.debugInfo(e.getMessage());
        }
    }

    public void updateUserInfo(String userName
            , String userSex
            , String userDate
            , String userEnterprise
            , String userPosition
            , String userEmail) {
        Map<String, Object> map = new HashMap<>();
        map.put("memberId", entity.getMemberId());
        if (!TextUtils.isEmpty(userName)) {
            map.put("memberName", userName);
        }
        if (!TextUtils.isEmpty(iconImage)
                && isUploadNewAvatar) {
            map.put("iconImage", iconImage);
        }
        if (!TextUtils.isEmpty(userSex)) {
            map.put("sex", "男".endsWith(userSex) ? 1 : 2);
        }
        if (!TextUtils.isEmpty(userDate)) {
            map.put("birthday1", userDate + " 00:00:00");
        }
        if (!TextUtils.isEmpty(province)
                && !TextUtils.isEmpty(city)
                && !TextUtils.isEmpty(area)) {
            int regionId = 0;
            for (RegionEntity entity : list) {
                if (entity.getName().equals(province)) {
                    for (RegionEntity entity1 : entity.getChild()) {
                        if (entity1.getName().equals(city)) {
                            for (RegionEntity entity2 : entity1.getChild()) {
                                if (entity2.getName().equals(area)) {
                                    regionId = entity2.getRegionId();
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            map.put("regionId", regionId);
        }
        if (!TextUtils.isEmpty(industry)) {
            int industryId = 0;
            for (IndustryEntity entity : industryEntityList) {
                if (industry.equals(entity.getIndustryName())) {
                    industryId = entity.getIndustryId();
                    break;
                }
            }
            map.put("industryId", industryId);
        }
        if (!TextUtils.isEmpty(userEnterprise)) {
            map.put("institutionName", userEnterprise);
        }
        if (!TextUtils.isEmpty(userPosition)) {
            map.put("memberPositionName", userPosition);
        }
        if (!TextUtils.isEmpty(userEmail)) {
            if (RegexUtils.isEmail(userEmail)) {
                map.put("email", userEmail);
            } else {
                mRootView.showMessage("请输入正确的邮箱账号");
                return;
            }
        }
        mModel.updateUserInfo(RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(map)))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))
                .doOnSubscribe(disposable -> mRootView.showLoading(""))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()) {
                            UserInfo info = JMessageClient.getMyInfo();
                            if (info != null) {
                                info.setNickname(userName);
                                info.setGender("男".equals(userSex) ? UserInfo.Gender.male : UserInfo.Gender.female);
                                JMessageClient.updateMyInfo(UserInfo.Field.nickname, info, null);
                            }
                            DataHelper.setStringSF(mApplication, "BIRTHDAY", userDate);
                            JMessageClient.updateUserAvatar(saveFile, null);

                            mRootView.showMessage("保存成功");
                            mRootView.killMyself();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        this.list = null;
        this.allProv = null;
        this.areaMap = null;
        this.cityMap = null;
    }
}
