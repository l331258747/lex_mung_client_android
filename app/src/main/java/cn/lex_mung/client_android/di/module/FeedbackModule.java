package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.FeedbackContract;
import cn.lex_mung.client_android.mvp.model.FeedbackModel;
import com.tbruyelle.rxpermissions2.RxPermissions;


@Module
public class FeedbackModule {

    private FeedbackContract.View view;

    public FeedbackModule(FeedbackContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    FeedbackContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    FeedbackContract.Model provideMeModel(FeedbackModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
