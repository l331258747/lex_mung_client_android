package me.zl.mvp.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import me.zl.mvp.integration.EventBusManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 基类Service
 */
public abstract class BaseService extends Service {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (useEventBus())
            EventBusManager.getInstance().register(this);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus())
            EventBusManager.getInstance().unregister(this);
        unDispose();//解除订阅
        this.mCompositeDisposable = null;
    }

    /**
     * 是否使用 EventBus
     *
     * @return true使用
     */
    public boolean useEventBus() {
        return true;
    }

    protected void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有subscription放入,集中处理
    }

    protected void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
        }
    }

    /**
     * 初始化
     */
    abstract public void init();
}
