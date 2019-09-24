package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MyEntrustListContract;
import cn.lex_mung.client_android.mvp.model.MyEntrustListModel;


@Module
public class MyEntrustListModule {

    private MyEntrustListContract.View view;

    public MyEntrustListModule(MyEntrustListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyEntrustListContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyEntrustListContract.Model provideMeModel(MyEntrustListModel model) {
        return model;
    }
}
