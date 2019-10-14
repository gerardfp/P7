package com.company.p7.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import com.company.p7.db.TareasDao;
import com.company.p7.db.TareasDatabase;
import com.company.p7.model.Prioridad;
import com.company.p7.model.Tarea;
import com.company.p7.model.TareaDetalle;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TareasViewModel extends AndroidViewModel {
    TareasDao tareasDao;

    public TareasViewModel(@NonNull Application application) {
        super(application);

        tareasDao = TareasDatabase.getInstance(application).tareasDao();
    }

    public LiveData<List<Prioridad>> getPrioridades(){
        return tareasDao.getPrioridades();
    }

    public LiveData<List<TareaDetalle>> getTareasDetalle(){
        return tareasDao.getTareasDetalle();
    }

    public void insertarTarea(final Tarea tarea){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                tareasDao.insertarTarea(tarea);
            }
        });
    }

    public void deleteTarea(final int id){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                tareasDao.deleteTarea(id);
            }
        });
    }
}
