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


public class AmigoUpdateFragment extends Fragment {

    private TextInputEditText etNombre,etTelefono,etFecha;
    private NavController navController;
    private ViewModelCompartido viewModelCompartido;
    private  Amigo aUpdate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_amigo_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModelCompartido = new ViewModelProvider(getActivity()).get(ViewModelCompartido.class);
        navController = Navigation.findNavController(view);
        etNombre = getView().findViewById(R.id.etNombreUpdate);
        etTelefono = getView().findViewById(R.id.etTelefonoUpdate);
        etFecha = getView().findViewById(R.id.etFechaNacimientoUpdate);
        aUpdate = viewModelCompartido.getAmigoUpdate();

        etNombre.setText(aUpdate.getNombre());
        String aupdateTelefono = Amigo.desProcesaNumero(aUpdate.getTelefono());
        etTelefono.setText(aupdateTelefono);
        etFecha.setText(aUpdate.getFechaNacimiento());

        Button btAtras = getView().findViewById(R.id.btAtrasUpdate);
        Button btUpdate = getView().findViewById(R.id.btActualizar);

        btAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navController.navigate(R.id.firstFragment);
                navController.popBackStack();
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAmigo();

            }
        });

    }

    private void updateAmigo() {
    String nombre = etNombre.getText().toString();
    String telefono = etTelefono.getText().toString();
    String fecha = etFecha.getText().toString();

    if(nombre.length()!=0 &&  telefono.length()!=0){
        if(Amigo.esTelefonoFormato(telefono)){
            telefono = Amigo.procesaNumero(telefono);
            aUpdate.setTelefono(telefono);
            aUpdate.setNombre(nombre);
            aUpdate.setFechaNacimiento(fecha);
            viewModelCompartido.updateAmigo(aUpdate);
            navController.navigate(R.id.firstFragment);
        }else{
            Toast.makeText(getContext(), "Formato del Telefono Incorrecto Ej:666555444", Toast.LENGTH_SHORT).show();
        }

    }else{
        Toast.makeText(getContext(), "Datos Vacios", Toast.LENGTH_SHORT).show();
    }






    }
}