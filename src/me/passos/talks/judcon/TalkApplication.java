package me.passos.talks.judcon;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import org.jboss.aerogear.android.Pipeline;
import org.jboss.aerogear.android.impl.pipeline.PipeConfig;
import org.jboss.aerogear.android.pipeline.LoaderPipe;

import java.net.MalformedURLException;
import java.net.URL;

public class TalkApplication extends Application {

    Pipeline pipeline;

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            URL baseURL = new URL("http://10.0.2.2:3000/");

            pipeline = new Pipeline(baseURL);

            PipeConfig pipeConfig = new PipeConfig(baseURL, Talk.class);
            pipeConfig.setEndpoint("ag");

            pipeline.pipe(Talk.class, pipeConfig);


        } catch (MalformedURLException e) {
            Log.e("JudCon", e.getMessage());
        }

    }

    public LoaderPipe<Talk> getPipe(Activity activity) {
        return pipeline.get("talk", activity);
    }

}