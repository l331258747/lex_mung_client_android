package me.zl.mvp.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zl.mvp.base.delegate.IFragment;
import me.zl.mvp.integration.cache.Cache;
import me.zl.mvp.integration.cache.CacheType;
import me.zl.mvp.integration.lifecycle.FragmentLifecycleable;
import me.zl.mvp.mvp.IPresenter;
import me.zl.mvp.utils.AppUtils;

import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * Fragment基类。一定要实现{@link IFragment}
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IFragment, FragmentLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> cache;
    protected Cache<String, Object> mCache;
    protected Context mContext;
    protected Activity mActivity;
    @Inject
    @NonNull
    protected P mPresenter;
    protected Bundle bundle;

    protected Dialog loading;
    protected View view;

    private long pair;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (cache == null) {
            cache = AppUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return cache;
    }

    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        bundle = new Bundle();
        mCache = AppUtils.obtainAppComponentFromContext(context).extras();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pair = System.currentTimeMillis() / 1000;

        mActivity = getActivity();
        return view = initView(inflater, container, savedInstanceState);
    }

    public long getPair(){
        return pair;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    /**
     * 是否使用 EventBus
     *
     * @return true使用。
     */
    @Override
    public boolean useEventBus() {
        return true;
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
