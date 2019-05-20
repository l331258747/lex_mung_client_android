package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.FreeConsultReplyContract;
import cn.lex_mung.client_android.mvp.model.FreeConsultReplyModel;


@Module
public class FreeConsultReplyModule {

    private FreeConsultReplyContract.View view;

    public FreeConsultReplyModule(FreeConsultReplyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FreeConsultReplyContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FreeConsultReplyContract.Model provideMeModel(FreeConsultReplyModel model) {
        return model;
    }
}
