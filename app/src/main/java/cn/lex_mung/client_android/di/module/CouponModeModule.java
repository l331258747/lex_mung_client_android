package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.CouponModeContract;
import cn.lex_mung.client_android.mvp.model.CouponModeModel;


@Module
public class CouponModeModule {

    private CouponModeContract.View view;

    public CouponModeModule(CouponModeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CouponModeContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CouponModeContract.Model provideMeModel(CouponModeModel model) {
        return model;
    }
}
