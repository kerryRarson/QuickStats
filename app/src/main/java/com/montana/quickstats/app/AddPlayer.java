package com.montana.quickstats.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.io.OutputStreamWriter;


public class AddPlayer extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        //Home intent
        final Intent mainPage = new Intent(AddPlayer.this, MainActivity.class );

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

        String hgt = cboFeet.getSelectedItem().toString() + "."+  cboInches.getSelectedItem().toString();

        Player player = new Player(txtName.getText().toString(), txtTeam.getText().toString(), cboPos.getSelectedItem().toString(), hgt, cboWgt.getSelectedItem().toString(), "");

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(player.Serialize());
            outputStreamWriter.close();
        }
        catch (IOException writeE) {
            writeE.printStackTrace();
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
}
