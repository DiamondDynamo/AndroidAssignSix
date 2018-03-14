package com.example.charley.assignfive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_db);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);

        ArrayList<String> list = new ArrayList<>();
        if(preferences.contains("fName"))
            list.add(preferences.getString("fName", ""));
        if(preferences.contains("lName"))
            list.add(preferences.getString("lName", ""));
        if(preferences.contains("lName"))
            list.add(preferences.getString("email", ""));
        if(preferences.contains("lName"))
            list.add(preferences.getString("phone", ""));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_item, R.id.listItem, list);

        final ListView listView = findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        TextView emptyView = findViewById(android.R.id.empty);
        listView.setEmptyView(emptyView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) listView.getItemAtPosition(position);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), selectedItem, duration);
                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_person, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.menuAct1){
            Intent intent = new Intent(getApplicationContext(), DisplayDb.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.menuAct3) {
            SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            ArrayList<String> list = new ArrayList<>();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.row_item, R.id.listItem, list);
            final ListView listView = findViewById(android.R.id.list);
            listView.setAdapter(adapter);
            TextView emptyView = findViewById(android.R.id.empty);
            listView.setEmptyView(emptyView);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
