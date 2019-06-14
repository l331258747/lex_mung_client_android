package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.OrderContractContract;
import cn.lex_mung.client_android.mvp.model.OrderContractModel;


@Module
public class OrderContractModule {

    private OrderContractContract.View view;

    public OrderContractModule(OrderContractContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderContractContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderContractContract.Model provideMeModel(OrderContractModel model) {
        return model;
    }
}
