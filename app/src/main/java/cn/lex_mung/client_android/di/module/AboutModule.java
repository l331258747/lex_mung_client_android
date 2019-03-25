package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.AboutContract;
import cn.lex_mung.client_android.mvp.model.AboutModel;


@Module
public class AboutModule {

    private AboutContract.View view;

    public AboutModule(AboutContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AboutContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AboutContract.Model provideMeModel(AboutModel model) {
        return model;
    }
}
