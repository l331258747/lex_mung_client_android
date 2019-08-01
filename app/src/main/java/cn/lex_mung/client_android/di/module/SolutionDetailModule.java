package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.SolutionDetailContract;
import cn.lex_mung.client_android.mvp.model.SolutionDetailModel;


@Module
public class SolutionDetailModule {

    private SolutionDetailContract.View view;

    public SolutionDetailModule(SolutionDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SolutionDetailContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SolutionDetailContract.Model provideMeModel(SolutionDetailModel model) {
        return model;
    }
}
