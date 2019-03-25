package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MapContract;
import cn.lex_mung.client_android.mvp.model.MapModel;


@Module
public class MapModule {

    private MapContract.View view;

    public MapModule(MapContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MapContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MapContract.Model provideMeModel(MapModel model) {
        return model;
    }
}
