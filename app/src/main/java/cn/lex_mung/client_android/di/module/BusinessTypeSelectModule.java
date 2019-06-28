package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.BusinessTypeSelectContract;
import cn.lex_mung.client_android.mvp.model.BusinessTypeSelectModel;


@Module
public class BusinessTypeSelectModule {

    private BusinessTypeSelectContract.View view;

    public BusinessTypeSelectModule(BusinessTypeSelectContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BusinessTypeSelectContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BusinessTypeSelectContract.Model provideMeModel(BusinessTypeSelectModel model) {
        return model;
    }
}
