package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.HomeTableQygwContract;
import cn.lex_mung.client_android.mvp.model.HomeTableQygwModel;


@Module
public class HomeTableQygwModule {

    private HomeTableQygwContract.View view;

    public HomeTableQygwModule(HomeTableQygwContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeTableQygwContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeTableQygwContract.Model provideMeModel(HomeTableQygwModel model) {
        return model;
    }
}
