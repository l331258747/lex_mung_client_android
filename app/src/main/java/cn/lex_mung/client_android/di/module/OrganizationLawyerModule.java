package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.OrganizationLawyerContract;
import cn.lex_mung.client_android.mvp.model.OrganizationLawyerModel;


@Module
public class OrganizationLawyerModule {

    private OrganizationLawyerContract.View view;

    public OrganizationLawyerModule(OrganizationLawyerContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrganizationLawyerContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrganizationLawyerContract.Model provideMeModel(OrganizationLawyerModel model) {
        return model;
    }
}
