package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.FreeConsultDetail1ListModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultDetail1ListActivity;


@ActivityScope
@Component(modules = FreeConsultDetail1ListModule.class, dependencies = AppComponent.class)
public interface FreeConsultDetail1ListComponent {
    void inject(FreeConsultDetail1ListActivity activity);
}
