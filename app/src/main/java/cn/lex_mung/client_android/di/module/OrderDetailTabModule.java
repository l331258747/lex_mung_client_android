package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.OrderDetailTabContract;
import cn.lex_mung.client_android.mvp.model.OrderDetailTabModel;


@Module
public class OrderDetailTabModule {

    private OrderDetailTabContract.View view;

    public OrderDetailTabModule(OrderDetailTabContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderDetailTabContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderDetailTabContract.Model provideMeModel(OrderDetailTabModel model) {
        return model;
    }
}
