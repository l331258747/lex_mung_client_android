package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MyOrderContract;
import cn.lex_mung.client_android.mvp.model.MyOrderModel;


@Module
public class MyOrderModule {

    private MyOrderContract.View view;

    public MyOrderModule(MyOrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyOrderContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyOrderContract.Model provideMeModel(MyOrderModel model) {
        return model;
    }
}
