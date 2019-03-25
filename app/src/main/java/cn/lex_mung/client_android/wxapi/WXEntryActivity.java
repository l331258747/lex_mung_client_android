package cn.lex_mung.client_android.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.lex_mung.client_android.app.Constants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import me.zl.mvp.utils.AppUtils;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        api.registerApp(Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                AppUtils.makeText(this, "分享成功");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                AppUtils.makeText(this, "失败");
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                AppUtils.makeText(this, "用户取消");
                finish();
                break;
        }
    }
}
