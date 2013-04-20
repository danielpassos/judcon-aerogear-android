package me.passos.talks.judcon;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.jboss.aerogear.android.Callback;
import org.jboss.aerogear.android.pipeline.AbstractActivityCallback;
import org.jboss.aerogear.android.pipeline.LoaderPipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TalkListActivity extends ListActivity {

    private TalkApplication talkApplication;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.talk_list);

        talkApplication = (TalkApplication) getApplication();

        listView = this.getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Talk selectedTalk = (Talk) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(TalkListActivity.this, TalkFormActivity.class);
                intent.putExtra("Talk", selectedTalk);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Talk selectedTalk = (Talk) adapterView.getItemAtPosition(position);
                showDeleteConfirmation(selectedTalk);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        listTalksOnDevice();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                startActivity(new Intent(this, TalkFormActivity.class));
                return false;
            case R.id.menu_refresh:
                listTalksOnDevice();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void listTalksOnDevice() {

        LoaderPipe<Talk> pipe = talkApplication.getPipe(this);
        pipe.read(new Callback<List<Talk>>() {
            @Override
            public void onSuccess(List<Talk> data) {
                List<Talk> allTalks = new ArrayList<Talk>(data);
                ArrayAdapter adapter = new ArrayAdapter(TalkListActivity.this, android.R.layout.simple_list_item_1, allTalks);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(TalkListActivity.this, "Ops, We have a problem", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

    private void showDeleteConfirmation(final Talk talk) {
        new AlertDialog.Builder(this)
            .setMessage("Delete '" + talk.getTitle() + "'?")
            .setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            LoaderPipe<Talk> pipe = talkApplication.getPipe(TalkListActivity.this);
                            pipe.remove(String.valueOf(talk.getId()), new Callback<Void>() {
                                @Override
                                public void onSuccess(Void data) {
                                    listTalksOnDevice();
                                }

                                @Override
                                public void onFailure(Exception e) {

                                }
                            });
                        }
                    }).setNegativeButton("Cancel", null)
            .show();
    }

}
