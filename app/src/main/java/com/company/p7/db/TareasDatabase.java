package com.company.p7.db;

import android.content.Context;
import android.os.AsyncTask;

import com.company.p7.model.Prioridad;
import com.company.p7.model.Tarea;
import com.company.p7.model.TareaDetalle;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Tarea.class, Prioridad.class}, views = {TareaDetalle.class}, exportSchema = false, version = 1)
public abstract class TareasDatabase extends RoomDatabase {

    private static TareasDatabase INSTANCE;

    public abstract TareasDao tareasDao();

    public static TareasDatabase getInstance(final Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, TareasDatabase.class, "tareas-db")
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            insertarDatosIniciales(getInstance(context).tareasDao());
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }

    static void insertarDatosIniciales(final TareasDao tareasDao){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                tareasDao.insertarPrioridad(new Prioridad("ALTA"));
                tareasDao.insertarPrioridad(new Prioridad("MEDIA"));
                tareasDao.insertarPrioridad(new Prioridad("BAJA"));
            }
        });
    }
}