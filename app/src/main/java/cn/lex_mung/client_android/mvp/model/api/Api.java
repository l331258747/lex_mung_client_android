package cn.lex_mung.client_android.mvp.model.api;

import cn.lex_mung.client_android.BuildConfig;

/**
 * 存放一些与API有关的东西,如BaseUrl,请求码等
 */
public interface Api {
//    String J_PUSH = "57ec233e4815f11235b32395";
//    String APP_DOMAIN = "https://api-test.lex-mung.com/";
//        String J_PUSH = "0c63ef9cdb0385d0ab13f6dd";
//    String APP_DOMAIN = "https://api.lex-mung.com/";

    String J_PUSH_TEST = "57ec233e4815f11235b32395";
    String APP_DOMAIN_TEST = "https://api-test.lex-mung.com/";

    String J_PUSH_PROD = "0c63ef9cdb0385d0ab13f6dd";
    String APP_DOMAIN_PROD = "https://api.lex-mung.com/";

    String J_PUSH = BuildConfig.IS_PROD ? J_PUSH_PROD : J_PUSH_TEST;
    String APP_DOMAIN = BuildConfig.IS_PROD ? APP_DOMAIN_PROD : APP_DOMAIN_TEST;

    int RequestSuccess = 0;
}
