package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.FreeConsultDetail1Contract;
import cn.lex_mung.client_android.mvp.model.FreeConsultDetail1Model;


@Module
public class FreeConsultDetail1Module {

    private FreeConsultDetail1Contract.View view;

    public FreeConsultDetail1Module(FreeConsultDetail1Contract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FreeConsultDetail1Contract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FreeConsultDetail1Contract.Model provideMeModel(FreeConsultDetail1Model model) {
        return model;
    }
}
