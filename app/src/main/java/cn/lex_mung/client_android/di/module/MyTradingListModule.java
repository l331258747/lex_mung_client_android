package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MyTradingListContract;
import cn.lex_mung.client_android.mvp.model.MyTradingListModel;


@Module
public class MyTradingListModule {

    private MyTradingListContract.View view;

    public MyTradingListModule(MyTradingListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyTradingListContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyTradingListContract.Model provideMeModel(MyTradingListModel model) {
        return model;
    }
}
