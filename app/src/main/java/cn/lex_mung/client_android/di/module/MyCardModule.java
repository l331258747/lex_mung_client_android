package cn.lex_mung.client_android.di.module;

import cn.lex_mung.client_android.mvp.contract.MyCardContract;
import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.model.MyCardModel;


@Module
public class MyCardModule {

    private MyCardContract.View view;

    public MyCardModule(MyCardContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyCardContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyCardContract.Model provideMeModel(MyCardModel model) {
        return model;
    }
}
