package cn.lex_mung.client_android.mvp.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.tencent.smtt.sdk.TbsReaderView;

import cn.lex_mung.client_android.R;
import cn.lex_mung.client_android.mvp.ui.widget.TitleView;
import me.zl.mvp.utils.AppUtils;
import me.zl.mvp.utils.StatusBarUtil;

public class X5WebActivity extends AppCompatActivity implements TbsReaderView.ReaderCallback {

    LinearLayout ll_parent;
    TbsReaderView mTbsReaderView;
    String filePath;
    String fileName;
    ImageView iv_img;
    String fileType;
    TitleView titleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtil.setColor(this, AppUtils.getColor(this, com.zl.mvp.R.color.theme), 0);

        setContentView(R.layout.activity_x5web);

        ll_parent = findViewById(R.id.ll_parent);
        iv_img = findViewById(R.id.iv_img);
        findViewById(R.id.titleView).setOnClickListener(v -> finish());

        filePath = getIntent().getStringExtra("x5web_file_path");
        fileName = getIntent().getStringExtra("x5web_file_name");

        String[] sli = fileName.split("\\.");
        fileType = sli[sli.length - 1];

        if (fileType.equals("png") || fileType.equals("jpg") || fileType.equals("jpeg")) {
            iv_img.setVisibility(View.VISIBLE);
            Glide.with(this).load(filePath)
                    .into(iv_img);
        } else {
            iv_img.setVisibility(View.GONE);

            mTbsReaderView = new TbsReaderView(this, this);
            ll_parent.addView(mTbsReaderView, new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            displayFile();
        }
    }


    private void displayFile() {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", filePath);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory().getPath());
        boolean result = mTbsReaderView.preOpen(parseFormat(fileName), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTbsReaderView !=null)
            mTbsReaderView.onStop();
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }
}
