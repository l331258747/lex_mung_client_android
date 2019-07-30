package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.HomeSolutionContract;
import cn.lex_mung.client_android.mvp.model.HomeSolutionModel;


@Module
public class HomeSolutionModule {

    private HomeSolutionContract.View view;

    public HomeSolutionModule(HomeSolutionContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeSolutionContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeSolutionContract.Model provideMeModel(HomeSolutionModel model) {
        return model;
    }
}
