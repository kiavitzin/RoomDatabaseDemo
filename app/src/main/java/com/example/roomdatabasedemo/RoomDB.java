package com.example.roomdatabasedemo;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Agregamos las entidades de la base de datos
@Database(entities = {MainData.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    // creamos las instancias de la base de datos
    private  static RoomDB database;
    //Definimos el nombre de la base de datos
    private static String DATABASE_NAME = "database";

    public  synchronized static RoomDB getInstance(Context context){
        // Verificamos una condición
        if (database == null){
            // Cuando la base de datos es null
            //inicializamos database
            database = Room.databaseBuilder(context.getApplicationContext()
            ,RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        //Return databse
        return database;
    }

    //Creamos el DAO
    public abstract MainDao mainDao();
}
