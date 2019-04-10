package cn.lex_mung.client_android.app;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cn.lex_mung.client_android.mvp.model.entity.RegionEntity;

public class RegionUtils {
    private List<RegionEntity> list = new ArrayList<>();//所有的数据
    private Context context;

    @SuppressLint("StaticFieldLeak")
    private volatile static RegionUtils INSTANCE;

    public static RegionUtils getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RegionUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RegionUtils(context);
                }
            }
        }
        return INSTANCE;
    }

    private RegionUtils(Context context) {
        try {
            this.context = context;
            StringBuilder sb = new StringBuilder();
            InputStream is = context.getAssets().open("city.json");
            byte[] by = new byte[is.available()];
            int len;
            while ((len = is.read(by)) != -1) {
                sb.append(new String(by, 0, len, StandardCharsets.UTF_8));
            }
            is.close();
            list = new Gson().fromJson(sb.toString(), new TypeToken<List<RegionEntity>>() {
            }.getType());
        } catch (Exception ignored) {
        }
    }

    public List<RegionEntity> getList() {
        return list;
    }
}
