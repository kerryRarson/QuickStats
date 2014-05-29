package com.montana.quickstats.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ListView lstView;
    playerlistAdapter arrayAdapter;
    ProgressDialog dlg;
    String FILENAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //If there are players, display them in a list.
        //If not go to the Add Player activity

        FILENAME = getResources().getString(R.string.file_players);
        String line;
        BufferedReader input = null;
        final Intent addPlayerPage = new Intent(MainActivity.this, AddPlayer.class);

        try{
            boolean havePlayers = false;
            input = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
            StringBuffer buffer = new StringBuffer();
            ArrayList<Player> playerList = new ArrayList<Player>();
            while ((line = input.readLine()) != null) {
                havePlayers = true;
                Player p = new Player();
                p.Deserialize(line);
                playerList.add(p);
            }
            if ( havePlayers ){
                //Initialize ListView
                ListView lv = (ListView)findViewById(R.id.lvPlayers);
                //Initialize our array adapter notice how it references the playerlistitems.xml layout
                arrayAdapter = new playerlistAdapter(MainActivity.this, R.layout.playerlistitem,playerList);

                //Set the above adapter as the adapter of choice for our list
                lv.setAdapter(arrayAdapter);

            } else {
                MainActivity.this.startActivity(addPlayerPage);
            }
        }
        catch ( Exception e ){
            //Toast.makeText(getApplicationContext(), "no players!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            MainActivity.this.startActivity(addPlayerPage);
        }
        finally {
            try {
                if ( input != null) input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(addPlayerPage);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if ( id == R.id.action_upload ){
             dlg = ProgressDialog.show(this, "Uploading...", "Uploading Players & measurables to front office...");
            new Thread( new Runnable() {
                @Override
                public void run() {
                    //TODO sleep 5 seconds...
                    SystemClock.sleep(5000);
                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            dlg.dismiss();
                        }
                    });
                    File dir = getFilesDir();
                    File file = new File(dir, FILENAME);
                    file.delete();
                    //refresh the activity
                    Intent intent = getIntent();
                    finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                }
            }).start();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
