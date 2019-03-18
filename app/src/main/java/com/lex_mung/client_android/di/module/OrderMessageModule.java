package com.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import com.lex_mung.client_android.mvp.contract.OrderMessageContract;
import com.lex_mung.client_android.mvp.model.OrderMessageModel;


@Module
public class OrderMessageModule {

    private OrderMessageContract.View view;

    public OrderMessageModule(OrderMessageContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    OrderMessageContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    OrderMessageContract.Model provideMeModel(OrderMessageModel model) {
        return model;
    }
}
