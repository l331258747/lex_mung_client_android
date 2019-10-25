package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsBuyEquityContract;
import cn.lex_mung.client_android.mvp.model.OrderDetailsBuyEquityModel;


@Module
public class OrderDetailsBuyEquityModule {

    private OrderDetailsBuyEquityContract.View view;

    public OrderDetailsBuyEquityModule(OrderDetailsBuyEquityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderDetailsBuyEquityContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderDetailsBuyEquityContract.Model provideMeModel(OrderDetailsBuyEquityModel model) {
        return model;
    }
}
