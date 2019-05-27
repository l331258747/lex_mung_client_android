package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.HelpStepLawyerContract;
import cn.lex_mung.client_android.mvp.model.HelpStepLawyerModel;


@Module
public class HelpStepLawyerModule {

    private HelpStepLawyerContract.View view;

    public HelpStepLawyerModule(HelpStepLawyerContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HelpStepLawyerContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HelpStepLawyerContract.Model provideMeModel(HelpStepLawyerModel model) {
        return model;
    }
}
