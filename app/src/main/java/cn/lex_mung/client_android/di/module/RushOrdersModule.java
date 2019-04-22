package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.RushOrdersContract;
import cn.lex_mung.client_android.mvp.model.RushOrdersModel;


@Module
public class RushOrdersModule {

    private RushOrdersContract.View view;

    public RushOrdersModule(RushOrdersContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RushOrdersContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RushOrdersContract.Model provideMeModel(RushOrdersModel model) {
        return model;
    }
}
