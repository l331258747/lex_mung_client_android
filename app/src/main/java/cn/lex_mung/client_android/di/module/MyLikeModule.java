package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MyLikeContract;
import cn.lex_mung.client_android.mvp.model.MyLikeModel;


@Module
public class MyLikeModule {

    private MyLikeContract.View view;

    public MyLikeModule(MyLikeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyLikeContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyLikeContract.Model provideMeModel(MyLikeModel model) {
        return model;
    }
}
