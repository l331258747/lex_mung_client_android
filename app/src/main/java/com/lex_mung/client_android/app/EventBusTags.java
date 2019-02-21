package com.lex_mung.client_android.app;

/**
 * 放置AndroidEventBus的Tag
 */
public interface EventBusTags {
    interface USER_DETAILS {
        String USER_DETAILS = "edit_user_details";
        int EDIT_BASIC_INFO = 100;
        int EDIT_CERTIFICATION_INFO = EDIT_BASIC_INFO + 1;
        int EDIT_SKILLS = EDIT_CERTIFICATION_INFO + 1;
        int EDIT_COURT_NUM = EDIT_SKILLS + 1;
        int EDIT_PROCURATORATE_NUM = EDIT_COURT_NUM + 1;
        int EDIT_WORK = EDIT_PROCURATORATE_NUM + 1;
        int EDIT_EDUCATION = EDIT_WORK + 1;
        int EDIT_INDUSTRY = EDIT_EDUCATION + 1;
        int EDIT_FIELD = EDIT_INDUSTRY + 1;
        int EDIT_ORGANIZATION = EDIT_FIELD + 1;

        int ADD_SOCIAL = 200;
        int ADD_HONOR = ADD_SOCIAL + 1;
        int ADD_QUALIFICATION = ADD_HONOR + 1;
        int ADD_WORK = ADD_QUALIFICATION + 1;//工作
        int ADD_EDUCATION = ADD_WORK + 1;//教育


        int DELETE_HONOR = 300;
        int DELETE_SOCIAL = DELETE_HONOR + 1;
        int DELETE_QUALIFICATION = DELETE_SOCIAL + 1;
        int DELETE_WORK = DELETE_QUALIFICATION + 1;//工作
        int DELETE_EDUCATION = DELETE_WORK + 1;//教育
    }

    interface CERTIFICATION_INFO {
        String CERTIFICATION_INFO = "certification_info";
        int EDIT_LAWS_OFFICE = 100;
        int EDIT_LAWS_FIRMS = 101;
        int EDIT_LAWS_FIRMS_NAME = 102;
    }

    interface OTHER_INFO {
        String OTHER_INFO = "other_info";
        int EDIT_SOCIAL = 100;
        int EDIT_HONOR = 101;
        int EDIT_ORGANIZATION = 102;
        int EDIT_QUALIFICATION = 103;
    }

    interface RESORT_ORGANIZATION {
        String RESORT_ORGANIZATION = "resort_organization";
        int EDIT_RESORT_ORGANIZATION = 100;
    }

    interface EDIT_IMAGE {
        String EDIT_IMAGE = "edit_image";
        int EDIT_BG_IMAGE = 100;
        int OPEN_CAMERA = 101;
    }

    interface FEEDBACK_INFO {
        String FEEDBACK_INFO = "feedback_info";
        int FEEDBACK_INFO_TYPE = 100;
    }

    interface PEER_SCREEN_INFO {
        String PEER_SCREEN_INFO = "feedback_info";
        int PEER_SCREEN_INFO_TYPE = 100;
        int PEER_SCREEN_INFO_INSTITUTIONS = 101;
        int PEER_SCREEN_INFO_LIST = 102;
        int PEER_SCREEN_INFO_LIST_1 = 103;
    }

    interface MESSAGE_INFO {
        String UN_READ_MESSAGE_NUM = "un_read_message_num";
        int SET_ORDER_UN_READ_MESSAGE_NUM = 100;
        int SET_CUSTOMER_UN_READ_MESSAGE_NUM = 101;
        int SET_SYSTEM_UN_READ_MESSAGE_NUM = 102;
        int SET_UN_READ_WORK_NUM = 104;
        int UPDATE_ORDER_UN_READ_MESSAGE_NUM = 105;
        int UPDATE_SYSTEM_UN_READ_MESSAGE_NUM = 106;
    }

    interface CONSULT_INFO {
        String EDIT_REPLY_COUNT = "edit_reply_count";
        int EDIT_CONSULT_DETAILS_REPLY_COUNT = 100;
    }

    interface REFRESH {
        String REFRESH = "refresh";
        int REFRESH_CONSULT_DETAILS = 100;
        int REFRESH_UNREAD_MESSAGES_NUMBER = 101;
        int REFRESH_DIALOG = 102;
    }

    interface WORK_INFO {
        String WORK_INFO = "work_info";
        int REMIN_REPLY = 100;
    }

    interface SWITCH_PAGE {
        String SWITCH_PAGE = "switch_page";
        int HOME_PAGE_1 = 100;
        int HOME_PAGE_2 = 101;
        int HOME_PAGE_3 = 102;
        int HOME_PAGE_4 = 103;
        int WORK_PAGE_1 = 200;
        int WORK_PAGE_2 = 201;
        int WORK_PAGE_3 = 202;
        int MESSAGE_PAGE_1 = 300;
        int MESSAGE_PAGE_2 = 301;
        int MESSAGE_PAGE_3 = 302;
        int FOUND_PAGE = 400;
        int ME_PAGE = 500;
    }
    interface MAP_INFO {
        String MAP_INFO = "map_info";
        int SELECT_PLACE = 100;
    }
}
