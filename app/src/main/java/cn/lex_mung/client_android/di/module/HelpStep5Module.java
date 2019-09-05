package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.HelpStep5Contract;
import cn.lex_mung.client_android.mvp.model.HelpStep5Model;


@Module
public class HelpStep5Module {

    private HelpStep5Contract.View view;

    public HelpStep5Module(HelpStep5Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    HelpStep5Contract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    HelpStep5Contract.Model provideMeModel(HelpStep5Model model) {
        return model;
    }
}
