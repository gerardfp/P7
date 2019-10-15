package com.company.p7.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.p7.R;
import com.company.p7.model.TareaDetalle;
import com.company.p7.viewmodel.TareasViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    TareasViewModel tareasViewModel;
    TareasAdapter tareasAdapter;
    List<TareaDetalle> tareaDetalleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tareasViewModel = ViewModelProviders.of(this).get(TareasViewModel.class);

        findViewById(R.id.nuevaTarea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NuevaTareaActivity.class));
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_tareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(tareasAdapter = new TareasAdapter());

        tareasViewModel.getTareasDetalle().observe(this, new Observer<List<TareaDetalle>>() {
            @Override
            public void onChanged(List<TareaDetalle> queryResult) {
                tareaDetalleList = queryResult;
                tareasAdapter.notifyDataSetChanged();
            }
        });
    }

    class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView descripcion, fecha, prioridad;
        ImageView trash;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.tarea_descripcion);
            fecha = itemView.findViewById(R.id.tarea_fecha);
            prioridad = itemView.findViewById(R.id.tarea_prioridad);
            trash = itemView.findViewById(R.id.trash);
        }
    }

    class TareasAdapter extends RecyclerView.Adapter<TareaViewHolder>{
        @NonNull
        @Override
        public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TareaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_tarea, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
            final TareaDetalle tareaDetalle = tareaDetalleList.get(position);

            holder.descripcion.setText(tareaDetalle.descripcion);
            holder.fecha.setText(tareaDetalle.fecha);
            holder.prioridad.setText(tareaDetalle.prioridad);

            holder.trash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tareasViewModel.deleteTarea(tareaDetalle.id);
                }
            });
        }

        @Override
        public int getItemCount() {
            return tareaDetalleList != null ? tareaDetalleList.size() : 0;
        }
    }
}
