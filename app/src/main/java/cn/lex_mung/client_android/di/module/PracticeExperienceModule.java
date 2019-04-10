package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.FragmentScope;

import cn.lex_mung.client_android.mvp.contract.PracticeExperienceContract;
import cn.lex_mung.client_android.mvp.model.PracticeExperienceModel;


@Module
public class PracticeExperienceModule {

    private PracticeExperienceContract.View view;

    public PracticeExperienceModule(PracticeExperienceContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    PracticeExperienceContract.View provideMeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    PracticeExperienceContract.Model provideMeModel(PracticeExperienceModel model) {
        return model;
    }
}
