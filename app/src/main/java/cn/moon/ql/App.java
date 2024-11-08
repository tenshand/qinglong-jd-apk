package cn.moon.ql;

import android.app.Application;
import android.content.SharedPreferences;

import cn.moon.ql.data.model.QLLoginData;
import cn.moon.ql.data.model.QLSettingsData;
import cn.moon.ql.data.model.QLStoreData;

public class App extends Application {
    private static App instance;
    private static SharedPreferences sharedPreferences;
    private static final String QL_DATA_FILE_NAME = "qinglong";
    private static QLStoreData qlStoreData;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initApp();
    }

    public static App getInstance() {
        return instance;
    }

    public static QLStoreData getQLStoreData() {
        return qlStoreData;
    }


    private void initApp() {
        sharedPreferences = getSharedPreferences(QL_DATA_FILE_NAME, MODE_PRIVATE);
        String url = sharedPreferences.getString("url", "https://qingl.vip.cpolar.cn/");
        String cid = sharedPreferences.getString("cid", "GynbEPML1b-Q");
        String csk = sharedPreferences.getString("csk", "Qaa3FbAfZc1A0V8ufa-j5WKd");

        String tokenType = sharedPreferences.getString("token_type", "Bearer");
        String token = sharedPreferences.getString("token", null);

        QLLoginData loginData = new QLLoginData(tokenType, token);
        QLSettingsData settingsData = new QLSettingsData(url, cid, csk);
        qlStoreData = new QLStoreData(loginData, settingsData);
    }



    public static void storeQLData(QLSettingsData settingsData, QLLoginData loginData) {
        qlStoreData = new QLStoreData(loginData, settingsData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("url", settingsData.getUrl());
        editor.putString("cid", settingsData.getCid());
        editor.putString("csk", settingsData.getCsk());
        editor.putString("token_type", loginData.getTokenType());
        editor.putString("token", loginData.getToken());
        editor.apply();
    }
}
