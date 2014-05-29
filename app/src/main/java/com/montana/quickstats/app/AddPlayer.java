package com.montana.quickstats.app;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;


public class AddPlayer extends ActionBarActivity {
    private EditText txtSpd;
    private Button btnTimer;
    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private Handler timerHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        //Home intent
        final Intent mainPage = new Intent(AddPlayer.this, MainActivity.class );

        //show the keyboard on name
        EditText txtName  = (EditText)findViewById(R.id.txtName);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txtName, InputMethodManager.SHOW_IMPLICIT);

        //Hide it when in the HGT box
        EditText txtHgt = (EditText)findViewById(R.id.txtSpd);
        imm.hideSoftInputFromWindow(txtHgt.getWindowToken(), 0);


        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPlayer.this.startActivity(mainPage);
            }
        });

        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePlayer();
                AddPlayer.this.startActivity(mainPage);

            }
        });

        //set up the timer start/stop button
        //http://examples.javacodegeeks.com/android/core/os/handler/android-timer-example/
        txtSpd = (EditText)findViewById(R.id.txtSpd);
        btnTimer = (Button)findViewById(R.id.btnTimer);
        btnTimer.setOnClickListener( new View.OnClickListener(){
            public void onClick(View view) {
                //If the button is running. stop the timer
                if (btnTimer.getText() == getResources().getString(R.string.start)){
                    //change the button caption & kick off the timer thread
                    btnTimer.setText(getResources().getString(R.string.pause));

                    startTime = SystemClock.uptimeMillis();
                    timerHandler.postDelayed(updateTimerThread, 0);
                } else {
                    btnTimer.setText(getResources().getString(R.string.start));
                    timerHandler.removeCallbacks(updateTimerThread);
                }
            }
        } );

        //Load the pounds dropdown
        Spinner cboPounds = (Spinner)findViewById(R.id.cboWgt);
        String aLbls[] = new String[200];
        for(int i=0; i<aLbls.length; i++){
            aLbls[i] = "" + (i+100);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, aLbls);
        cboPounds.setAdapter(adapter);


    }

    private void savePlayer(){
        String FILENAME = getResources().getString(R.string.file_players);
        EditText txtName = (EditText)findViewById(R.id.txtName);
        EditText txtTeam = (EditText)findViewById(R.id.txtTeam);
        Spinner cboPos = (Spinner)findViewById(R.id.cboPos);
        Spinner cboFeet = (Spinner)findViewById(R.id.cboFeet);
        Spinner cboInches = (Spinner)findViewById(R.id.cboInches);
        Spinner cboWgt = (Spinner)findViewById(R.id.cboWgt);
        EditText txtSpd = (EditText)findViewById(R.id.txtSpd);

        String hgt = cboFeet.getSelectedItem().toString() + "."+  cboInches.getSelectedItem().toString();

        Player player = new Player(txtName.getText().toString(), txtTeam.getText().toString(), cboPos.getSelectedItem().toString(), hgt, cboWgt.getSelectedItem().toString(), txtSpd.getText().toString());

        try {
            String line;
            ArrayList<String> lines = new ArrayList<String>();
            BufferedReader input = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)));
            try{
                //StringBuffer buffer = new StringBuffer();
                while ((line = input.readLine()) != null) {
                    lines.add(line);
                }
            } catch (FileNotFoundException ex){
                //no file yet
                ex.printStackTrace();
            } finally {
                lines.add(player.Serialize());
                input.close();
            }

            //write them all back to disk
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            for (String s: lines)
                if (s.length() > 0){
                    bw.write(s);
                    bw.newLine();
                }
            bw.close();
            outputStreamWriter.close();
        }
        catch (IOException writeE) {
            writeE.printStackTrace();
            Toast.makeText(getApplicationContext(), writeE.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_player, menu);
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

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            txtSpd.setText("" + mins + ":"
             + String.format("%02d", secs) + ":"
             + String.format("%03d", milliseconds));
            timerHandler.postDelayed(this, 0);
        }
    };
}
