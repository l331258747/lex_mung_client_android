package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.PushJumpContract;
import cn.lex_mung.client_android.mvp.model.PushJumpModel;


@Module
public class PushJumpModule {

    private PushJumpContract.View view;

    public PushJumpModule(PushJumpContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PushJumpContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PushJumpContract.Model provideMeModel(PushJumpModel model) {
        return model;
    }
}
