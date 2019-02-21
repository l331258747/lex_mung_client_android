package com.lex_mung.client_android.mvp.model.entity;

import java.util.List;

public class RegionEntity {

    /**
     * regionId : 110000
     * name : 北京
     * child : [{"regionId":110100,"name":"北京市","child":[{"regionId":110101,"name":"东城区"},{"regionId":110102,"name":"西城区"},{"regionId":110105,"name":"朝阳区"},{"regionId":110106,"name":"丰台区"},{"regionId":110107,"name":"石景山区"},{"regionId":110108,"name":"海淀区"},{"regionId":110109,"name":"门头沟区"},{"regionId":110111,"name":"房山区"},{"regionId":110112,"name":"通州区"},{"regionId":110113,"name":"顺义区"},{"regionId":110114,"name":"昌平区"},{"regionId":110115,"name":"大兴区"},{"regionId":110116,"name":"怀柔区"},{"regionId":110117,"name":"平谷区"},{"regionId":110228,"name":"密云县"},{"regionId":110229,"name":"延庆县"}]}]
     */

    private int regionId;
    private String name;
    private List<RegionEntity> child;

    public RegionEntity(int regionId, String name) {
        this.regionId = regionId;
        this.name = name;
    }

    public RegionEntity() {
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RegionEntity> getChild() {
        return child;
    }

    public void setChild(List<RegionEntity> child) {
        this.child = child;
    }
}
