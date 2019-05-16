package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.FreeConsultMainContract;
import cn.lex_mung.client_android.mvp.model.FreeConsultMainModel;


@Module
public class FreeConsultMainModule {

    private FreeConsultMainContract.View view;

    public FreeConsultMainModule(FreeConsultMainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FreeConsultMainContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FreeConsultMainContract.Model provideMeModel(FreeConsultMainModel model) {
        return model;
    }
}
