package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.HomeTableContract;
import cn.lex_mung.client_android.mvp.model.HomeTableModel;


@Module
public class HomeTableModule {

    private HomeTableContract.View view;

    public HomeTableModule(HomeTableContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeTableContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeTableContract.Model provideMeModel(HomeTableModel model) {
        return model;
    }
}
