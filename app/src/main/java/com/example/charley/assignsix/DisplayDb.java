package com.example.charley.assignsix;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

import static android.R.id.list;

public class DisplayDb extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_db);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), AddPersonActivity.class);
                startActivity(intent);
            }
        });

        PersonDbHelper dbHelper = new PersonDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                PersonContract.PersonEntry.COLUMN_NAME_FIRST,
                PersonContract.PersonEntry.COLUMN_NAME_LAST,
                PersonContract.PersonEntry.COLUMN_PHONE,
                PersonContract.PersonEntry.COLUMN_EMAIL
        };

        String[] bind = {
                PersonContract.PersonEntry._ID,
                PersonContract.PersonEntry.COLUMN_NAME_FIRST,
                PersonContract.PersonEntry.COLUMN_NAME_LAST,
                PersonContract.PersonEntry.COLUMN_PHONE,
                PersonContract.PersonEntry.COLUMN_EMAIL
        };

        Cursor cursor = db.query(PersonContract.PersonEntry.TABLE_NAME, bind, null, null, null, null, PersonContract.PersonEntry.COLUMN_NAME_LAST + " ASC");

        int [] to = new int[]{R.id.first, R.id.last, R.id.phone, R.id.email};

        SimpleCursorAdapter adapter  = new SimpleCursorAdapter(getApplicationContext(), R.layout.row_item, cursor, projection, to, 0);

        final ListView listView = findViewById(list);
        listView.setAdapter(adapter);

        TextView emptyView = findViewById(android.R.id.empty);
        listView.setEmptyView(emptyView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                String[] selectedEmail = {(String) cursor.getString(cursor.getColumnIndex(PersonContract.PersonEntry.COLUMN_EMAIL))};
                String selectedLastName = (String) cursor.getString(cursor.getColumnIndex(PersonContract.PersonEntry.COLUMN_NAME_LAST));
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, selectedEmail);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, selectedLastName);

                PackageManager pacman = getPackageManager();
                List<ResolveInfo> activities = pacman.queryIntentActivities(emailIntent, 0);
                boolean isIntentSafe = activities.size() > 0;

                if(isIntentSafe)
                    startActivity(emailIntent);

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_display_db, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.enterValues) {
            Intent intent = new Intent(getApplicationContext(), AddPersonActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
