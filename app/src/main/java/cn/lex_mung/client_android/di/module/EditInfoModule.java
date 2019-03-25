package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.EditInfoContract;
import cn.lex_mung.client_android.mvp.model.EditInfoModel;
import com.tbruyelle.rxpermissions2.RxPermissions;


@Module
public class EditInfoModule {

    private EditInfoContract.View view;

    public EditInfoModule(EditInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    EditInfoContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    EditInfoContract.Model provideMeModel(EditInfoModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }

}
