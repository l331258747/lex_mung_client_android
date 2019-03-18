package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.ReleaseDemandContract;
import com.lex_mung.client_android.mvp.model.ReleaseDemandModel;
import com.tbruyelle.rxpermissions2.RxPermissions;


@Module
public class ReleaseDemandModule {

    private ReleaseDemandContract.View view;

    public ReleaseDemandModule(ReleaseDemandContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ReleaseDemandContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ReleaseDemandContract.Model provideMeModel(ReleaseDemandModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
