package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.OrderCouponContract;
import cn.lex_mung.client_android.mvp.model.OrderCouponModel;


@Module
public class OrderCouponModule {

    private OrderCouponContract.View view;

    public OrderCouponModule(OrderCouponContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderCouponContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderCouponContract.Model provideMeModel(OrderCouponModel model) {
        return model;
    }
}
