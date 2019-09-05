package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.HelpStep6Contract;
import cn.lex_mung.client_android.mvp.model.HelpStep6Model;


@Module
public class HelpStep6Module {

    private HelpStep6Contract.View view;

    public HelpStep6Module(HelpStep6Contract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    HelpStep6Contract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    HelpStep6Contract.Model provideMeModel(HelpStep6Model model) {
        return model;
    }
}
