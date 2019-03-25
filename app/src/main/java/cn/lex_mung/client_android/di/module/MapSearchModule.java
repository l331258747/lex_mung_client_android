package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MapSearchContract;
import cn.lex_mung.client_android.mvp.model.MapSearchModel;


@Module
public class MapSearchModule {

    private MapSearchContract.View view;

    public MapSearchModule(MapSearchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MapSearchContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MapSearchContract.Model provideMeModel(MapSearchModel model) {
        return model;
    }
}
