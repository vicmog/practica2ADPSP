package com.example.practica2adpsp.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.practica2adpsp.R;
import com.example.practica2adpsp.model.entity.Amigo;
import com.example.practica2adpsp.model.entity.Llamada;
import com.example.practica2adpsp.model.entity.NumeroLlamadasAmigo;
import com.example.practica2adpsp.view.adapter.RecylcerAdapterListaAmigos;
import com.example.practica2adpsp.viewmodel.ViewModelCompartido;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ListaAmigosFragment extends Fragment{

    private final static int PERMISOACCEDERCONTACTOS = 1;
    private final static int PERMISOREGISTRALLAMADAS = 2;
    private boolean permisoContactos = false;
    private boolean permisoLlamadas = false;
    private Toolbar toolbar;
    private NavController navController;
    private ViewModelCompartido viewModel;
    private List<Amigo>listAmigos = new ArrayList<>();;
    private List<NumeroLlamadasAmigo>listaNumeroLlamadas = new ArrayList<>();;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_amigos, container, false);

        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.miToolbarFirstFragment);
        ((AppCompatActivity) this.getActivity()).setSupportActionBar(toolbar); //cargar menu en toolbar fragments.
        toolbar.inflateMenu(R.menu.menu_action_bar);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModelCompartido.class);
        navController = Navigation.findNavController(view);

        init();


    }

    private void init() {

        buscaListaAmigos();
        Button btAddAmigos = getView().findViewById(R.id.btAddAmigo);
        btAddAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compruebaPermisoRegistraLlamadas();
                navController.navigate(R.id.amigoAddFragment);
            }
        });

    }

    private void buscaListaAmigos() {

        RecyclerView recyclerView = getView().findViewById(R.id.miRecyclerFirstFragment);
        RecylcerAdapterListaAmigos adapter = new RecylcerAdapterListaAmigos (listaNumeroLlamadas,getActivity(),getView());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        viewModel.getLiveListNumeroLlamadas().observe(getActivity(), new Observer<List<NumeroLlamadasAmigo>>() {
            @Override
            public void onChanged(List<NumeroLlamadasAmigo> numeroLlamadasAmigos) {
                listaNumeroLlamadas.clear();
                listaNumeroLlamadas.addAll(numeroLlamadasAmigos);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_action_bar,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.importarContactos:

                compruebaPermisoContactos();

                if(permisoContactos){
                 navController.navigate(R.id.listaContactosFragment);
                }
                break;
        }
        return true;
    }

    private void compruebaPermisoContactos() {
        int permisoLeeContactos = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (permisoLeeContactos == PackageManager.PERMISSION_GRANTED)) {

            permisoContactos = true;

        } else {

            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                mostrarInfromacionDetalladaPermisoContactos();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISOACCEDERCONTACTOS);
            }

        }

    }
    private void compruebaPermisoRegistraLlamadas() {
        int permisoRegistraLlamadas = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (permisoRegistraLlamadas == PackageManager.PERMISSION_GRANTED)) {

            permisoLlamadas = true;

        } else {

            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                mostrarInfromacionDetalladaPermisoLlamada();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISOREGISTRALLAMADAS);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case PERMISOACCEDERCONTACTOS:
                int contador = 0;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        contador++;

                    }
                }
                if (contador == grantResults.length) {
                    permisoContactos = true;
                } else {

                }
                break;
            case PERMISOREGISTRALLAMADAS:
                int count = 0;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        count++;

                    }
                }
                if (count == grantResults.length) {
                    permisoLlamadas = true;
                } else {

                }
                break;

        }
    }

    private void mostrarInfromacionDetalladaPermisoContactos() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.str_titulo_permisos);
        builder.setMessage(R.string.str_explicacion_permiso);
        builder.setPositiveButton(R.string.str_aceptar, new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{ Manifest.permission.READ_CONTACTS}, PERMISOACCEDERCONTACTOS);
            }
        });
        builder.setNegativeButton(R.string.str_cancel, null);
        builder.show();

    }
    private void mostrarInfromacionDetalladaPermisoLlamada() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.str_titulo_permisos);
        builder.setMessage(R.string.str_permiso2);
        builder.setPositiveButton(R.string.str_aceptar, new DialogInterface.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISOREGISTRALLAMADAS);
            }
        });
        builder.setNegativeButton(R.string.str_cancel, null);
        builder.show();

    }

}