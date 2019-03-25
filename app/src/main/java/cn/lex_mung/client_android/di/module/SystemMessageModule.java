package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.SystemMessageContract;
import cn.lex_mung.client_android.mvp.model.SystemMessageModel;


@Module
public class SystemMessageModule {

    private SystemMessageContract.View view;

    public SystemMessageModule(SystemMessageContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    SystemMessageContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    SystemMessageContract.Model provideMeModel(SystemMessageModel model) {
        return model;
    }
}
