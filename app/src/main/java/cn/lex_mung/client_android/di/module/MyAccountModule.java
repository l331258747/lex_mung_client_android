package cn.lex_mung.client_android.di.module;

import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MyAccountContract;
import cn.lex_mung.client_android.mvp.model.MyAccountModel;

@Module
public class MyAccountModule {

    private MyAccountContract.View view;

    public MyAccountModule(MyAccountContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyAccountContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyAccountContract.Model provideMeModel(MyAccountModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
