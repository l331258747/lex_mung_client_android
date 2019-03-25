package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.ActiveContract;
import cn.lex_mung.client_android.mvp.model.ActiveModel;


@Module
public class ActiveModule {

    private ActiveContract.View view;

    public ActiveModule(ActiveContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ActiveContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ActiveContract.Model provideMeModel(ActiveModel model) {
        return model;
    }
}
