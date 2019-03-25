package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.ServicePriceContract;
import cn.lex_mung.client_android.mvp.model.ServicePriceModel;


@Module
public class ServicePriceModule {

    private ServicePriceContract.View view;

    public ServicePriceModule(ServicePriceContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ServicePriceContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ServicePriceContract.Model provideMeModel(ServicePriceModel model) {
        return model;
    }
}
