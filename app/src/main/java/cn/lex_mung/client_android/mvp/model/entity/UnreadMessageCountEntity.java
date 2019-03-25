package cn.lex_mung.client_android.mvp.model.entity;

import java.io.Serializable;

public class UnreadMessageCountEntity implements Serializable {

    /**
     * unreadReqMsgCount : 1
     * unreadSysMsgCount : 0
     * unreadOrderMsgCount : 0
     */

    private int unreadReqMsgCount;
    private int unreadSysMsgCount;
    private int unreadOrderMsgCount;

    public int getUnreadReqMsgCount() {
        return unreadReqMsgCount;
    }

    public void setUnreadReqMsgCount(int unreadReqMsgCount) {
        this.unreadReqMsgCount = unreadReqMsgCount;
    }

    public int getUnreadSysMsgCount() {
        return unreadSysMsgCount;
    }

    public void setUnreadSysMsgCount(int unreadSysMsgCount) {
        this.unreadSysMsgCount = unreadSysMsgCount;
    }

    public int getUnreadOrderMsgCount() {
        return unreadOrderMsgCount;
    }

    public void setUnreadOrderMsgCount(int unreadOrderMsgCount) {
        this.unreadOrderMsgCount = unreadOrderMsgCount;
    }
}
