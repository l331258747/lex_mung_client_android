package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.SolutionLIstContract;
import cn.lex_mung.client_android.mvp.model.SolutionLIstModel;


@Module
public class SolutionLIstModule {

    private SolutionLIstContract.View view;

    public SolutionLIstModule(SolutionLIstContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    SolutionLIstContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    SolutionLIstContract.Model provideMeModel(SolutionLIstModel model) {
        return model;
    }
}
