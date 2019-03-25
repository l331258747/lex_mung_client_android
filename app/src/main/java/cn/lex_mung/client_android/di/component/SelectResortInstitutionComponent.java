package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.SelectResortInstitutionModule;

import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.ui.activity.SelectResortInstitutionActivity;


@ActivityScope
@Component(modules = SelectResortInstitutionModule.class, dependencies = AppComponent.class)
public interface SelectResortInstitutionComponent {
    void inject(SelectResortInstitutionActivity activity);
}
