package cn.lex_mung.client_android.di.module;

import dagger.Module;
import dagger.Provides;
import me.zl.mvp.di.scope.ActivityScope;

import cn.lex_mung.client_android.mvp.contract.TradingListDetailsContract;
import cn.lex_mung.client_android.mvp.model.TransactionListDetailsModel;


@Module
public class TradingListDetailsModule {

    private TradingListDetailsContract.View view;

    public TradingListDetailsModule(TradingListDetailsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    TradingListDetailsContract.View provideMeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    TradingListDetailsContract.Model provideMeModel(TransactionListDetailsModel model) {
        return model;
    }
}
