package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.FreeConsultDetailsListContract;
import cn.lex_mung.client_android.mvp.model.FreeConsultDetailsListModel;


@Module
public class FreeConsultDetailsListModule {

    private FreeConsultDetailsListContract.View view;

    public FreeConsultDetailsListModule(FreeConsultDetailsListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FreeConsultDetailsListContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FreeConsultDetailsListContract.Model provideMeModel(FreeConsultDetailsListModel model) {
        return model;
    }
}
