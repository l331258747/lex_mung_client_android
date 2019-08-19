package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.PublicLawyerContract;
import cn.lex_mung.client_android.mvp.model.PublicLawyerModel;


@Module
public class PublicLawyerModule {

    private PublicLawyerContract.View view;

    public PublicLawyerModule(PublicLawyerContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PublicLawyerContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PublicLawyerContract.Model provideMeModel(PublicLawyerModel model) {
        return model;
    }
}
