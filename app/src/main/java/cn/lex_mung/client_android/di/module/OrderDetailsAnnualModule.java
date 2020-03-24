package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsAnnualContract;
import cn.lex_mung.client_android.mvp.model.OrderDetailsAnnualModel;


@Module
public class OrderDetailsAnnualModule {

    private OrderDetailsAnnualContract.View view;

    public OrderDetailsAnnualModule(OrderDetailsAnnualContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderDetailsAnnualContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderDetailsAnnualContract.Model provideMeModel(OrderDetailsAnnualModel model) {
        return model;
    }
}
