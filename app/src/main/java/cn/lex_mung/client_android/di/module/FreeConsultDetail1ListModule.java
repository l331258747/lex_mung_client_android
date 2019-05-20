package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.FreeConsultDetail1ListContract;
import cn.lex_mung.client_android.mvp.model.FreeConsultDetail1ListModel;


@Module
public class FreeConsultDetail1ListModule {

    private FreeConsultDetail1ListContract.View view;

    public FreeConsultDetail1ListModule(FreeConsultDetail1ListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FreeConsultDetail1ListContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FreeConsultDetail1ListContract.Model provideMeModel(FreeConsultDetail1ListModel model) {
        return model;
    }
}
