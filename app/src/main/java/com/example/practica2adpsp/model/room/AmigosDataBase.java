package com.example.practica2adpsp.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.practica2adpsp.model.dao.AmigoDao;
import com.example.practica2adpsp.model.dao.LlamadaDao;
import com.example.practica2adpsp.model.entity.Amigo;
import com.example.practica2adpsp.model.entity.Llamada;

@Database(entities = {Amigo.class, Llamada.class}, version = 9, exportSchema = false)
public abstract class AmigosDataBase extends RoomDatabase {

    public abstract AmigoDao getAmigoDao();
    public abstract LlamadaDao getLlamadaDao();

    private volatile static AmigosDataBase INSTANCE;

    public static AmigosDataBase getDb(Context context){

        if(INSTANCE==null){
        INSTANCE = Room.databaseBuilder(context,AmigosDataBase.class,"amigosdb.sqlite").fallbackToDestructiveMigration().build();

        }
        return INSTANCE;
    }




}
