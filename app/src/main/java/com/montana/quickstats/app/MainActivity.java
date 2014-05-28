package com.montana.quickstats.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //If there are players, display them in a list.
        //If not go to the Add Player activity

        //String[] players = getResources().getStringArray(R.array.players);
        //if (players.length == 0){
           //Toast.makeText(getApplicationContext(), "HERE!", Toast.LENGTH_SHORT).show();
        //}
        String line;
        BufferedReader input = null;
        String FILENAME = getResources().getString(R.string.file_players);
        try{
            input = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
            StringBuffer buffer = new StringBuffer();
            line = input.readLine();
            if (line.length() > 0){
//TODO load the player list into a listView
                Toast.makeText(getApplicationContext(), "We've got  players!", Toast.LENGTH_SHORT).show();
                //delete it
                File dir = getFilesDir();
                File file = new File(dir, FILENAME);
                file.delete();
            }
        }
        catch ( Exception e ){
            Toast.makeText(getApplicationContext(), "no players!", Toast.LENGTH_SHORT).show();
            Intent addPlayerPage = new Intent(MainActivity.this, AddPlayer.class);
            MainActivity.this.startActivity(addPlayerPage);
            //create the file
            try {

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
                outputStreamWriter.write("Larsen, Kelley~CHI~5.09,170,13.25");
                outputStreamWriter.close();
            }
            catch (IOException writeE) {
                writeE.printStackTrace();
            }

        }
        finally {
            try {
                if ( input != null) input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
