package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.PhoneSubContract;
import cn.lex_mung.client_android.mvp.model.PhoneSubModel;


@Module
public class PhoneSubModule {

    private PhoneSubContract.View view;

    public PhoneSubModule(PhoneSubContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PhoneSubContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PhoneSubContract.Model provideMeModel(PhoneSubModel model) {
        return model;
    }
}
