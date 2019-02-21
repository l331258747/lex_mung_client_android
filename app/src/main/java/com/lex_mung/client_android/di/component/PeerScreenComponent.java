package com.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import com.lex_mung.client_android.di.module.PeerScreenModule;

import me.zl.mvp.di.scope.ActivityScope;

import com.lex_mung.client_android.mvp.ui.activity.PeerScreenActivity;


@ActivityScope
@Component(modules = PeerScreenModule.class, dependencies = AppComponent.class)
public interface PeerScreenComponent {
    void inject(PeerScreenActivity activity);
}
