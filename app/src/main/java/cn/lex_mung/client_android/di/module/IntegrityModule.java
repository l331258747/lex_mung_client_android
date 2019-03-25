package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.IntegrityContract;
import cn.lex_mung.client_android.mvp.model.IntegrityModel;


@Module
public class IntegrityModule {

    private IntegrityContract.View view;

    public IntegrityModule(IntegrityContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    IntegrityContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    IntegrityContract.Model provideMeModel(IntegrityModel model) {
        return model;
    }
}
