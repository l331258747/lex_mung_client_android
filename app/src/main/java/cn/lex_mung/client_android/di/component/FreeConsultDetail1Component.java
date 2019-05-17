package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.FreeConsultDetail1Module;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultDetail1Activity;


@ActivityScope
@Component(modules = FreeConsultDetail1Module.class, dependencies = AppComponent.class)
public interface FreeConsultDetail1Component {
    void inject(FreeConsultDetail1Activity activity);
}
