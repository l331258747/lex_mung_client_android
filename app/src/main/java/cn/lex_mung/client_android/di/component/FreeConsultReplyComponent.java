package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.FreeConsultReplyModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.FreeConsultReplyActivity;


@ActivityScope
@Component(modules = FreeConsultReplyModule.class, dependencies = AppComponent.class)
public interface FreeConsultReplyComponent {
    void inject(FreeConsultReplyActivity activity);
}
