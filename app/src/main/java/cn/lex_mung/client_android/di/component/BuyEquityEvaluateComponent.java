package cn.lex_mung.client_android.di.component;

import dagger.Component;
import me.zl.mvp.di.component.AppComponent;

import cn.lex_mung.client_android.di.module.BuyEquityEvaluateModule;

import me.zl.mvp.di.scope.ActivityScope;
import cn.lex_mung.client_android.mvp.ui.activity.BuyEquityEvaluateActivity;


@ActivityScope
@Component(modules = BuyEquityEvaluateModule.class, dependencies = AppComponent.class)
public interface BuyEquityEvaluateComponent {
    void inject(BuyEquityEvaluateActivity activity);
}
