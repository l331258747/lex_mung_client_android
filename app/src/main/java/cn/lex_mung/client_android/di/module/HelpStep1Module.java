package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.HelpStep1Contract;
import cn.lex_mung.client_android.mvp.model.HelpStep1Model;


@Module
public class HelpStep1Module {

    private HelpStep1Contract.View view;

    public HelpStep1Module(HelpStep1Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    HelpStep1Contract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    HelpStep1Contract.Model provideMeModel(HelpStep1Model model) {
        return model;
    }
}
