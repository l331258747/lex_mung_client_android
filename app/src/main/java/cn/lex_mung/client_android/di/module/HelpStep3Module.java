package cn.lex_mung.client_android.di.module;

import cn.lex_mung.client_android.mvp.model.HelpStep3Model;
import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.HelpStep3Contract;


@Module
public class HelpStep3Module {

    private HelpStep3Contract.View view;

    public HelpStep3Module(HelpStep3Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    HelpStep3Contract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    HelpStep3Contract.Model provideMeModel(HelpStep3Model model) {
        return model;
    }
}
