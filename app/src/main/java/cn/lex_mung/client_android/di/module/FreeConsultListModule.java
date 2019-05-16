package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.FreeConsultListContract;
import cn.lex_mung.client_android.mvp.model.FreeConsultListModel;


@Module
public class FreeConsultListModule {

    private FreeConsultListContract.View view;

    public FreeConsultListModule(FreeConsultListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FreeConsultListContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FreeConsultListContract.Model provideMeModel(FreeConsultListModel model) {
        return model;
    }
}
