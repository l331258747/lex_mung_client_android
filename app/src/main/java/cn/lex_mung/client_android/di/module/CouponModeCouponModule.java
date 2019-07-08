package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.CouponModeCouponContract;
import cn.lex_mung.client_android.mvp.model.CouponModeCouponModel;


@Module
public class CouponModeCouponModule {

    private CouponModeCouponContract.View view;

    public CouponModeCouponModule(CouponModeCouponContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CouponModeCouponContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CouponModeCouponContract.Model provideMeModel(CouponModeCouponModel model) {
        return model;
    }
}
