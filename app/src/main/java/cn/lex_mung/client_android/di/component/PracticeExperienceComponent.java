package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.PracticeExperienceModule;

import me.zl.mvp.di.scope.FragmentScope;
import cn.lex_mung.client_android.mvp.ui.fragment.PracticeExperienceFragment;


@FragmentScope
@Component(modules = PracticeExperienceModule.class, dependencies = AppComponent.class)
public interface PracticeExperienceComponent {
    void inject(PracticeExperienceFragment fragment);
}
