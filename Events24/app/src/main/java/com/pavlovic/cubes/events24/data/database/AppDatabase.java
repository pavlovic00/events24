package com.pavlovic.cubes.events24.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.pavlovic.cubes.events24.data.database.dao.EventDAO;
import com.pavlovic.cubes.events24.data.model.Event;

@Database(entities = Event.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract EventDAO eventDAO();

    public static AppDatabase getInstance(Context context){
        if (instance == null){
             instance = Room.databaseBuilder(context,
                    AppDatabase.class, "database-events24").allowMainThreadQueries().build();
        }
        return instance;
    }

}
