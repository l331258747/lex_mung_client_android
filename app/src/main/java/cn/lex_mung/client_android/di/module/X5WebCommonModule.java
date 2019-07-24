package cn.lex_mung.client_android.di.module;

import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.X5WebCommonContract;
import cn.lex_mung.client_android.mvp.model.X5WebCommonModel;


@Module
public class X5WebCommonModule {

    private X5WebCommonContract.View view;

    public X5WebCommonModule(X5WebCommonContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    X5WebCommonContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    X5WebCommonContract.Model provideMeModel(X5WebCommonModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
