package me.passos.talks.judcon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.pipeline.AbstractActivityCallback;
import org.jboss.aerogear.android.pipeline.LoaderPipe;

public class TalkFormActivity extends Activity {

    private TalkApplication talkApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk_form);

        talkApplication = (TalkApplication) getApplication();

        final EditText title = (EditText) findViewById(R.id.title);
        final Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        final Button buttonCancel = (Button) findViewById(R.id.buttonCancel);

        Talk talk = (Talk) getIntent().getSerializableExtra("Talk");
        if( talk != null )  {
            buttonAdd.setText(getString(R.string.update));
            title.setText(talk.getTitle());
        }

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TalkFormActivity.this.finish();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Talk talk = new Talk(title.getText().toString());
                LoaderPipe<Talk> pipe = talkApplication.getPipe(TalkFormActivity.this);
                pipe.save(talk, new Callback<Talk>() {
                    @Override
                    public void onSuccess(Talk data) {
                        TalkFormActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        TalkFormActivity.this.finish();
                    }
                });
            }
        });

    }
}
