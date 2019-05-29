package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.ReleaseDemandHistoryContract;
import cn.lex_mung.client_android.mvp.model.ReleaseDemandHistoryModel;


@Module
public class ReleaseDemandHistoryModule {

    private ReleaseDemandHistoryContract.View view;

    public ReleaseDemandHistoryModule(ReleaseDemandHistoryContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ReleaseDemandHistoryContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ReleaseDemandHistoryContract.Model provideMeModel(ReleaseDemandHistoryModel model) {
        return model;
    }
}
