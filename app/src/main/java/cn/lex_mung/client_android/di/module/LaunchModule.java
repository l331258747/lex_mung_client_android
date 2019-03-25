package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.LaunchContract;
import cn.lex_mung.client_android.mvp.model.LaunchModel;
import com.tbruyelle.rxpermissions2.RxPermissions;


@Module
public class LaunchModule {

    private LaunchContract.View view;

    public LaunchModule(LaunchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    LaunchContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    LaunchContract.Model provideMeModel(LaunchModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
