package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.TabOrderContractContract;
import cn.lex_mung.client_android.mvp.model.TabOrderContractModel;


@Module
public class TabOrderContractModule {

    private TabOrderContractContract.View view;

    public TabOrderContractModule(TabOrderContractContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    TabOrderContractContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    TabOrderContractContract.Model provideMeModel(TabOrderContractModel model) {
        return model;
    }
}
