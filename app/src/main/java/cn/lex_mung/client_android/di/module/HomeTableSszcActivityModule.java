package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.HomeTableSszcActivityContract;
import cn.lex_mung.client_android.mvp.model.HomeTableSszcActivityModel;


@Module
public class HomeTableSszcActivityModule {

    private HomeTableSszcActivityContract.View view;

    public HomeTableSszcActivityModule(HomeTableSszcActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeTableSszcActivityContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeTableSszcActivityContract.Model provideMeModel(HomeTableSszcActivityModel model) {
        return model;
    }
}
