package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsExpertContract;
import cn.lex_mung.client_android.mvp.model.OrderDetailsExpertModel;


@Module
public class OrderDetailsExpertModule {

    private OrderDetailsExpertContract.View view;

    public OrderDetailsExpertModule(OrderDetailsExpertContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderDetailsExpertContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderDetailsExpertContract.Model provideMeModel(OrderDetailsExpertModel model) {
        return model;
    }
}
