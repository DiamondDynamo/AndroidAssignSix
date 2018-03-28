package com.example.charley.assignfive;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddPersonActivity extends AppCompatActivity {

    EditText fName, lName, email,phone;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        submit = findViewById(R.id.subBut);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDbHelper myDbHelper = new PersonDbHelper(getApplicationContext());
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(PersonContract.PersonEntry.COLUMN_NAME_FIRST, fName.getText().toString());
                values.put(PersonContract.PersonEntry.COLUMN_NAME_LAST, lName.getText().toString());
                values.put(PersonContract.PersonEntry.COLUMN_EMAIL, email.getText().toString());
                values.put(PersonContract.PersonEntry.COLUMN_PHONE, PhoneNumberUtils.formatNumber(phone.getText().toString(), "US"));

                long newRowId = db.insert(PersonContract.PersonEntry.TABLE_NAME, null, values);

                int duration = Toast.LENGTH_LONG;
                String result;

                if (newRowId != -1)
                {
                    result = "New Person Added";
                }
                else
                {
                    result="ERROR";
                }

                Toast toast = Toast.makeText(getApplicationContext(), result, duration);
                toast.show();


                fName.setText("");
                lName.setText("");
                email.setText("");
                phone.setText("");
                fName.requestFocus();
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

        if (id == R.id.display){
            Intent intent = new Intent(getApplicationContext(), DisplayDb.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.clearDb) {
            PersonDbHelper myDbHelper = new PersonDbHelper(getApplicationContext());
            SQLiteDatabase db = myDbHelper.getWritableDatabase();
            db.delete(PersonContract.PersonEntry.TABLE_NAME, "1", null);

            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getApplicationContext(), "Database cleared", duration);
            toast.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
