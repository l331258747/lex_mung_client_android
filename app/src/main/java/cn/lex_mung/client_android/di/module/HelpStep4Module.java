package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.HelpStep4Contract;
import cn.lex_mung.client_android.mvp.model.HelpStep4Model;


@Module
public class HelpStep4Module {

    private HelpStep4Contract.View view;

    public HelpStep4Module(HelpStep4Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    HelpStep4Contract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    HelpStep4Contract.Model provideMeModel(HelpStep4Model model) {
        return model;
    }
}
