package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.contract.TradingListContract;
import com.lex_mung.client_android.mvp.model.TradingListModel;


@Module
public class TradingListModule {

    private TradingListContract.View view;

    public TradingListModule(TradingListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TradingListContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TradingListContract.Model provideMeModel(TradingListModel model) {
        return model;
    }
}
