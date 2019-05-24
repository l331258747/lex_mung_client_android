package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.HelpStep2Contract;
import cn.lex_mung.client_android.mvp.model.HelpStep2Model;


@Module
public class HelpStep2Module {

    private HelpStep2Contract.View view;

    public HelpStep2Module(HelpStep2Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    HelpStep2Contract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    HelpStep2Contract.Model provideMeModel(HelpStep2Model model) {
        return model;
    }
}
