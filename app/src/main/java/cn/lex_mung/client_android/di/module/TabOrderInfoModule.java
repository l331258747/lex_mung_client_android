package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.TabOrderInfoContract;
import cn.lex_mung.client_android.mvp.model.TabOrderInfoModel;


@Module
public class TabOrderInfoModule {

    private TabOrderInfoContract.View view;

    public TabOrderInfoModule(TabOrderInfoContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    TabOrderInfoContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    TabOrderInfoContract.Model provideMeModel(TabOrderInfoModel model) {
        return model;
    }
}
