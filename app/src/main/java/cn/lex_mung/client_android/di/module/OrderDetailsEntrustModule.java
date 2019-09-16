package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.OrderDetailsEntrustContract;
import cn.lex_mung.client_android.mvp.model.OrderDetailsEntrustModel;


@Module
public class OrderDetailsEntrustModule {

    private OrderDetailsEntrustContract.View view;

    public OrderDetailsEntrustModule(OrderDetailsEntrustContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderDetailsEntrustContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderDetailsEntrustContract.Model provideMeModel(OrderDetailsEntrustModel model) {
        return model;
    }
}
