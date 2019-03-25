package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MyCouponsContract;
import cn.lex_mung.client_android.mvp.model.MyCouponsModel;


@Module
public class MyCouponsModule {

    private MyCouponsContract.View view;

    public MyCouponsModule(MyCouponsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyCouponsContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyCouponsContract.Model provideMeModel(MyCouponsModel model) {
        return model;
    }
}
