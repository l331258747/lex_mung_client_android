package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.WebSolutionSelectContract;
import cn.lex_mung.client_android.mvp.model.WebSolutionSelectModel;


@Module
public class WebSolutionSelectModule {

    private WebSolutionSelectContract.View view;

    public WebSolutionSelectModule(WebSolutionSelectContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    WebSolutionSelectContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    WebSolutionSelectContract.Model provideMeModel(WebSolutionSelectModel model) {
        return model;
    }
}
