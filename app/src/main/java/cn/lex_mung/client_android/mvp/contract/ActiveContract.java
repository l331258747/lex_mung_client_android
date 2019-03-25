package cn.lex_mung.client_android.mvp.contract;

import cn.lex_mung.client_android.mvp.model.entity.LawsHomePagerBaseEntity;

import java.util.List;

import me.zl.mvp.mvp.IView;
import me.zl.mvp.mvp.IModel;

public interface ActiveContract {
    interface View extends IView {
        void setAdapter(List<LawsHomePagerBaseEntity.DynamicInfoBean> activityInfo);

        void noDataLayout();
    }

    interface Model extends IModel {

    }
}
