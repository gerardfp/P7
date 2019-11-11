package com.company.p7.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.company.p7.R;
import com.company.p7.model.Prioridad;
import com.company.p7.model.Tarea;
import com.company.p7.viewmodel.TareasViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class NuevaTareaFragment extends Fragment {

    TareasViewModel tareasViewModel;
    EditText editTextDescripcion;
    Spinner spinnerPrioridades;

    int prioridadId;

    public NuevaTareaFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nueva_tarea, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextDescripcion = view.findViewById(R.id.tarea_descripcion);
        spinnerPrioridades = view.findViewById(R.id.tarea_prioridad);

        tareasViewModel = ViewModelProviders.of(this).get(TareasViewModel.class);

        view.findViewById(R.id.crear_tarea).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextDescripcion.getText().toString().isEmpty()){
                    editTextDescripcion.setError("Introduzca la descripción");
                    return;
                }
                tareasViewModel.insertarTarea(new Tarea(editTextDescripcion.getText().toString(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), prioridadId));
                Navigation.findNavController(view).popBackStack();
            }
        });

        tareasViewModel.getPrioridades().observe(this, new Observer<List<Prioridad>>() {
            @Override
            public void onChanged(final List<Prioridad> prioridads) {
                spinnerPrioridades.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, prioridads));
                spinnerPrioridades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        prioridadId = prioridads.get(i).id;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });
            }
        });
    }
}
