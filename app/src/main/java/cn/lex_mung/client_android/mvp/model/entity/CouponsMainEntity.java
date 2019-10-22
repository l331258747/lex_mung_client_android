package cn.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class CouponsMainEntity {

    List<EquitiesBuyListEntity> equity;
    BaseListEntity<CouponsEntity> list;

    public List<EquitiesBuyListEntity> getEquity() {
        return equity;
    }

    public BaseListEntity<CouponsEntity> getList() {
        return list;
    }
}
