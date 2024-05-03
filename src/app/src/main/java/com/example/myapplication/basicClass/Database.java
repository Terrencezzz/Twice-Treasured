package com.example.myapplication.basicClass;

import com.google.firebase.database.FirebaseDatabase;

public class Database {
    private static FirebaseDatabase database = null;
    private Database() {

    }

    /**
     *  Lazy singleton pattern used in database connection
     * */
    public static FirebaseDatabase getDatabase(){
        if(database == null){
            database = FirebaseDatabase.getInstance();
        }else{
            System.out.println("Database instance has been created yet.");
        }

        return database;
    }
}
