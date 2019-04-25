package cn.lex_mung.client_android.di.module;

import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.RushLoanPayContract;
import cn.lex_mung.client_android.mvp.model.RushLoanPayModel;


@Module
public class RushLoanPayModule {

    private RushLoanPayContract.View view;

    public RushLoanPayModule(RushLoanPayContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RushLoanPayContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RushLoanPayContract.Model provideMeModel(RushLoanPayModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}
