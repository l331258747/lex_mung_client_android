package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.BuyEquityEvaluateContract;
import cn.lex_mung.client_android.mvp.model.BuyEquityEvaluateModel;


@Module
public class BuyEquityEvaluateModule {

    private BuyEquityEvaluateContract.View view;

    public BuyEquityEvaluateModule(BuyEquityEvaluateContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BuyEquityEvaluateContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BuyEquityEvaluateContract.Model provideMeModel(BuyEquityEvaluateModel model) {
        return model;
    }
}
