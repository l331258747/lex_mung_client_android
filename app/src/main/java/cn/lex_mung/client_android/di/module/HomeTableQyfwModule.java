package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.HomeTableQyfwContract;
import cn.lex_mung.client_android.mvp.model.HomeTableQyfwModel;


@Module
public class HomeTableQyfwModule {

    private HomeTableQyfwContract.View view;

    public HomeTableQyfwModule(HomeTableQyfwContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeTableQyfwContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeTableQyfwContract.Model provideMeModel(HomeTableQyfwModel model) {
        return model;
    }
}
