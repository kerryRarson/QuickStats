package com.montana.quickstats.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //If there are players, display them in a list.
        //If not go to the Add Player activity

        String line;
        BufferedReader input = null;
        String FILENAME = getResources().getString(R.string.file_players);
        final Intent addPlayerPage = new Intent(MainActivity.this, AddPlayer.class);

        try{
            boolean havePlayers = false;
            input = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
            StringBuffer buffer = new StringBuffer();
//TODO load the player list into a listView
            ArrayList<Player> playerList = new ArrayList<Player>();
            while ((line = input.readLine()) != null) {
                havePlayers = true;
                Player p = new Player();
                p.Deserialize(line);
                playerList.add(p);
            }
            if ( havePlayers ){
                //Toast.makeText(getApplicationContext(), "We've got  players!", Toast.LENGTH_SHORT).show();
                //ArrayAdapter<Player> adapter = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, playerList);
                //ListView lv = (ListView)findViewById(R.id.lvPlayers);
                //lv.setAdapter(adapter);

                //Initialize ListView
                ListView lv = (ListView)findViewById(R.id.lvPlayers);
                //Initialize our array adapter notice how it references the listitems.xml layout
                arrayAdapter = new playerlistAdapter(MainActivity.this, R.layout.playerlistitem,playerList);

                //Set the above adapter as the adapter of choice for our list
                lv.setAdapter(arrayAdapter);

            } else {
                MainActivity.this.startActivity(addPlayerPage);
            }
        }
        catch ( Exception e ){
            Toast.makeText(getApplicationContext(), "no players!", Toast.LENGTH_SHORT).show();
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
        return super.onOptionsItemSelected(item);
    }
}
