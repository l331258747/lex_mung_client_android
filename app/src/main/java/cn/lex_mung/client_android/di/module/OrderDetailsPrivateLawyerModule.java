package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsPrivateLawyerContract;
import cn.lex_mung.client_android.mvp.model.OrderDetailsPrivateLawyerModel;


@Module
public class OrderDetailsPrivateLawyerModule {

    private OrderDetailsPrivateLawyerContract.View view;

    public OrderDetailsPrivateLawyerModule(OrderDetailsPrivateLawyerContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderDetailsPrivateLawyerContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderDetailsPrivateLawyerContract.Model provideMeModel(OrderDetailsPrivateLawyerModel model) {
        return model;
    }
}
