package me.passos.talks.judcon;

import android.app.Application;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class TalkApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            URL baseURL = new URL("http://10.0.2.2:3000/");

        } catch (MalformedURLException e) {
            Log.e("JudCon", e.getMessage());
        }

    }

}