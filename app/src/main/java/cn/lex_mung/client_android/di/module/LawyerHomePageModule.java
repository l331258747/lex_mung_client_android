package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.LawyerHomePageContract;
import cn.lex_mung.client_android.mvp.model.LawyerHomePageModel;


@Module
public class LawyerHomePageModule {

    private LawyerHomePageContract.View view;

    public LawyerHomePageModule(LawyerHomePageContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LawyerHomePageContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LawyerHomePageContract.Model provideMeModel(LawyerHomePageModel model) {
        return model;
    }
}
