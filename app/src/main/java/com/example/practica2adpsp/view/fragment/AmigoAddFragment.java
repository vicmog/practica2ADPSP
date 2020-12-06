package com.example.practica2adpsp.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.practica2adpsp.R;
import com.example.practica2adpsp.model.entity.Amigo;
import com.example.practica2adpsp.viewmodel.ViewModelCompartido;
import com.google.android.material.textfield.TextInputEditText;

public class AmigoAddFragment extends Fragment {
    private TextInputEditText etNombre,etTelefono,etFecha;
    private NavController navController;
    private ViewModelCompartido viewModelCompartido;
    private Amigo aAdd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_amigo_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelCompartido = new ViewModelProvider(getActivity()).get(ViewModelCompartido.class);
        navController = Navigation.findNavController(view);
        etNombre = getView().findViewById(R.id.etNombreAdd);
        etTelefono = getView().findViewById(R.id.etTelefonoAdd);
        etFecha = getView().findViewById(R.id.etFechaAdd);
        Button btAtras = getView().findViewById(R.id.btAtrasAdd);
        Button btAdd = getView().findViewById(R.id.btAdd);
        btAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.firstFragment);
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadeNuevoAmigo();
            }
        });
    }

    private void añadeNuevoAmigo() {

        String nombre = etNombre.getText().toString();
        String telefono = etTelefono.getText().toString();
        String fecha = etFecha.getText().toString();

        if(nombre.length()!=0 &&  telefono.length()!=0){
            if(Amigo.esTelefonoFormato(telefono)){
                aAdd = new Amigo();
                telefono = Amigo.procesaNumero(telefono);
                aAdd.setTelefono(telefono);
                aAdd.setNombre(nombre);
                aAdd.setFechaNacimiento(fecha);
                viewModelCompartido.insertAmigo(aAdd);
                navController.navigate(R.id.firstFragment);
            }else{
                Toast.makeText(getContext(), "Formato del Telefono Incorrecto Ej:666555444", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getContext(), "Datos Vacios", Toast.LENGTH_SHORT).show();
        }





    }
}