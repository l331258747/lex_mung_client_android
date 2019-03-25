package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.SelectListItemContract;
import cn.lex_mung.client_android.mvp.model.SelectListItemModel;


@Module
public class SelectListItemModule {

    private SelectListItemContract.View view;

    public SelectListItemModule(SelectListItemContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SelectListItemContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SelectListItemContract.Model provideMeModel(SelectListItemModel model) {
        return model;
    }
}
