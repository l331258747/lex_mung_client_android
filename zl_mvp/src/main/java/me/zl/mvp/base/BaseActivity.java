package me.zl.mvp.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import me.zl.mvp.base.delegate.IActivity;
import me.zl.mvp.integration.cache.Cache;
import me.zl.mvp.integration.cache.CacheType;
import me.zl.mvp.integration.lifecycle.ActivityLifecycleable;
import me.zl.mvp.mvp.IPresenter;
import me.zl.mvp.utils.AppUtils;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zl.mvp.R;

import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.zl.mvp.utils.StatusBarUtil;

/**
 * Activity基类。一定要实现{@link IActivity}接口
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity, ActivityLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> cache;
    @NonNull
    protected Cache<String, Object> mCache;
    private Unbinder mUnbinder;
    @Inject
    @NonNull
    protected P mPresenter;

    protected Activity mActivity;
    protected Bundle bundle;
    protected Bundle bundleIntent;

    protected Dialog loading;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (cache == null) {
            cache = AppUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return cache;
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        ActivityCollect.getAppCollect().addActivity(this);

        mCache = AppUtils.obtainAppComponentFromContext(mActivity).extras();
        bundle = new Bundle();
        bundleIntent = getIntent().getExtras();
        try {
            int layoutResID = initView(savedInstanceState);
            if (layoutResID != 0) {
                setContentView(layoutResID);
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        StatusBarUtil.setColor(mActivity, AppUtils.getColor(mActivity, R.color.theme), 0);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
//        hintKeyBoard();
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        ActivityCollect.getAppCollect().finishActivity(this);
    }

    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 是否使用 EventBus
     *
     * @return true注册。默认true
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * 是否会使用Fragment
     *
     * @return 默认不会
     */
    @Override
    public boolean useFragment() {
        return false;
    }

    private static final int MIN_DELAY_TIME = 500;  // 两次点击间隔不能少于500ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
