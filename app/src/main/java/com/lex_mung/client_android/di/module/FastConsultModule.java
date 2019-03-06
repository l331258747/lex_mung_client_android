package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.FastConsultContract;
import com.lex_mung.client_android.mvp.model.FastConsultModel;
import com.tbruyelle.rxpermissions2.RxPermissions;


@Module
public class FastConsultModule {

    private FastConsultContract.View view;

    public FastConsultModule(FastConsultContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FastConsultContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FastConsultContract.Model provideMeModel(FastConsultModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
