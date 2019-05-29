package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.RecommendLawyerContract;
import cn.lex_mung.client_android.mvp.model.RecommendLawyerModel;


@Module
public class RecommendLawyerModule {

    private RecommendLawyerContract.View view;

    public RecommendLawyerModule(RecommendLawyerContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RecommendLawyerContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RecommendLawyerContract.Model provideMeModel(RecommendLawyerModel model) {
        return model;
    }
}
