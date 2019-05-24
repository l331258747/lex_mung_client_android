package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.HelpStepContract;
import cn.lex_mung.client_android.mvp.model.HelpStepModel;


@Module
public class HelpStepModule {

    private HelpStepContract.View view;

    public HelpStepModule(HelpStepContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HelpStepContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HelpStepContract.Model provideMeModel(HelpStepModel model) {
        return model;
    }
}
