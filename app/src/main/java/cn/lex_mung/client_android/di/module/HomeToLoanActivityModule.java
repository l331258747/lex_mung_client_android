package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.HomeToLoanActivityContract;
import cn.lex_mung.client_android.mvp.model.HomeToLoanActivityModel;


@Module
public class HomeToLoanActivityModule {

    private HomeToLoanActivityContract.View view;

    public HomeToLoanActivityModule(HomeToLoanActivityContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HomeToLoanActivityContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HomeToLoanActivityContract.Model provideMeModel(HomeToLoanActivityModel model) {
        return model;
    }
}
