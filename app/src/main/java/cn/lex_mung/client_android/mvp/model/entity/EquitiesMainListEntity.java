package cn.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class EquitiesMainListEntity {

   List<EquitiesListEntity> list;
   List<EquitiesBuyListEntity> equity;

    public List<EquitiesListEntity> getList() {
        return list;
    }

    public List<EquitiesBuyListEntity> getEquity() {
        return equity;
    }
}
