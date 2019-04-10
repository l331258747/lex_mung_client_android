package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MoreSocialPositionContract;
import cn.lex_mung.client_android.mvp.model.MoreSocialPositionModel;


@Module
public class MoreSocialPositionModule {

    private MoreSocialPositionContract.View view;

    public MoreSocialPositionModule(MoreSocialPositionContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MoreSocialPositionContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MoreSocialPositionContract.Model provideMeModel(MoreSocialPositionModel model) {
        return model;
    }
}
