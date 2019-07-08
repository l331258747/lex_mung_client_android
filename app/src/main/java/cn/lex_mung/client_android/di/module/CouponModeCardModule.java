package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.CouponModeCardContract;
import cn.lex_mung.client_android.mvp.model.CouponModeCardModel;


@Module
public class CouponModeCardModule {

    private CouponModeCardContract.View view;

    public CouponModeCardModule(CouponModeCardContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    CouponModeCardContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    CouponModeCardContract.Model provideMeModel(CouponModeCardModel model) {
        return model;
    }
}
