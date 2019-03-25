package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.DemandMessageContract;
import cn.lex_mung.client_android.mvp.model.DemandMessageModel;


@Module
public class DemandMessageModule {

    private DemandMessageContract.View view;

    public DemandMessageModule(DemandMessageContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    DemandMessageContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    DemandMessageContract.Model provideMeModel(DemandMessageModel model) {
        return model;
    }
}
