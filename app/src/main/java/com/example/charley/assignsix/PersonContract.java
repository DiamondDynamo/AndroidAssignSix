package com.example.charley.assignsix;


import android.provider.BaseColumns;

public final class PersonContract {

    public PersonContract() {

    }

    public static abstract class PersonEntry implements BaseColumns {
        public static final String TABLE_NAME = "person";
        public static final String COLUMN_NAME_FIRST = "firstName";
        public static final String COLUMN_NAME_LAST = "lastName";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
    }
}
