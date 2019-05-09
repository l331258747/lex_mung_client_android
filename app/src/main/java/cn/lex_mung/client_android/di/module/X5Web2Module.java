package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.X5Web2Contract;
import cn.lex_mung.client_android.mvp.model.X5Web2Model;


@Module
public class X5Web2Module {

    private X5Web2Contract.View view;

    public X5Web2Module(X5Web2Contract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    X5Web2Contract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    X5Web2Contract.Model provideMeModel(X5Web2Model model) {
        return model;
    }
}
