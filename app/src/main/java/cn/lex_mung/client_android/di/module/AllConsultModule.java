package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.AllConsultContract;
import cn.lex_mung.client_android.mvp.model.AllConsultModel;


@Module
public class AllConsultModule {

    private AllConsultContract.View view;

    public AllConsultModule(AllConsultContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AllConsultContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    AllConsultContract.Model provideMeModel(AllConsultModel model) {
        return model;
    }
}
