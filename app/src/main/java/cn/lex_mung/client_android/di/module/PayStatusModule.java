package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.PayStatusContract;
import cn.lex_mung.client_android.mvp.model.PayStatusModel;


@Module
public class PayStatusModule {

    private PayStatusContract.View view;

    public PayStatusModule(PayStatusContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PayStatusContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PayStatusContract.Model provideMeModel(PayStatusModel model) {
        return model;
    }
}
