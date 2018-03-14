package com.example.charley.assignfour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserPreferences extends AppCompatActivity {

    EditText in1, in2, in3, in4, in5, in6;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        in1 = findViewById(R.id.userIn1);
        in2 = findViewById(R.id.userIn2);
        in3 = findViewById(R.id.userIn3);
        in4 = findViewById(R.id.userIn4);
        in5 = findViewById(R.id.userIn5);
        in6 = findViewById(R.id.userIn6);

        submit = findViewById(R.id.saveBut);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                if(in1.getText().toString().trim().length() > 0)
                    editor.putString("in1", in1.getText().toString());
                if(in2.getText().toString().trim().length() > 0)
                    editor.putString("in2", in2.getText().toString());
                if(in3.getText().toString().trim().length() > 0)
                    editor.putString("in3", in3.getText().toString());
                if(in4.getText().toString().trim().length() > 0)
                    editor.putString("in4", in4.getText().toString());
                if(in5.getText().toString().trim().length() > 0)
                    editor.putString("in5", in5.getText().toString());
                if(in6.getText().toString().trim().length() > 0)
                    editor.putString("in6", in6.getText().toString());

                editor.apply();

                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), "Data Saved", duration);
                toast.show();

                in1.setText("");
                in2.setText("");
                in3.setText("");
                in4.setText("");
                in5.setText("");
                in6.setText("");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_for_screens, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menuAct2){
            Intent intent = new Intent(getApplicationContext(), DisplayPreferences.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.menuAct3){
            SharedPreferences preferences = getSharedPreferences("preferences",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
