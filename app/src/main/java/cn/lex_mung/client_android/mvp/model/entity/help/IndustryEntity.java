package cn.lex_mung.client_android.mvp.model.entity.help;

import java.io.Serializable;

public class IndustryEntity implements Serializable {
    /**
     * industryId : 21
     * industryNo : E01
     * industryName : 建筑业／房地产
     * parentId : 0
     * sortOrder : 1
     * status : 1
     * dateAdded : 2017-09-21 15:55:20
     * dateModified : 2018-07-18 15:23:56
     * industryDescription :
     */

    private int industryId;
    private String industryNo;
    private String industryName;
    private int parentId;
    private int sortOrder;
    private int status;
    private String dateAdded;
    private String dateModified;
    private String industryDescription;

    public int getId() {
        return industryId;
    }

    public String getText() {
        return industryName;
    }

}
