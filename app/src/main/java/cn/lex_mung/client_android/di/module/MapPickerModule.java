package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.MapPickerContract;
import cn.lex_mung.client_android.mvp.model.MapPickerModel;


@Module
public class MapPickerModule {

    private MapPickerContract.View view;

    public MapPickerModule(MapPickerContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MapPickerContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MapPickerContract.Model provideMeModel(MapPickerModel model) {
        return model;
    }
}
