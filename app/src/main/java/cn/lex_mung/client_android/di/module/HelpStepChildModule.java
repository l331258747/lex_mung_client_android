package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.HelpStepChildContract;
import cn.lex_mung.client_android.mvp.model.HelpStepChildModel;


@Module
public class HelpStepChildModule {

    private HelpStepChildContract.View view;

    public HelpStepChildModule(HelpStepChildContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HelpStepChildContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HelpStepChildContract.Model provideMeModel(HelpStepChildModel model) {
        return model;
    }
}
