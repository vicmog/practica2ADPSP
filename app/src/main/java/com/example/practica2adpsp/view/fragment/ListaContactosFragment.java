package com.example.practica2adpsp.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.practica2adpsp.R;
import com.example.practica2adpsp.model.entity.Amigo;
import com.example.practica2adpsp.view.adapter.RecyclerViewContactosAdapter;
import com.example.practica2adpsp.viewmodel.ViewModelCompartido;

import java.util.ArrayList;
import java.util.List;


public class ListaContactosFragment extends Fragment {
private ViewModelCompartido viewModel;
private List<Amigo>contactos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_lista_contactos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getView().findViewById(R.id.miToolbarContactosAgenda);
        ((AppCompatActivity) this.getActivity()).setSupportActionBar(toolbar);
        NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerContactos);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModelCompartido.class);
        obtenerListaContactos();

        RecyclerViewContactosAdapter adapter = new RecyclerViewContactosAdapter(contactos,getActivity(),getView());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    private void obtenerListaContactos() {

        viewModel.buscaListaContactos();
        contactos = viewModel.getContactos();

    }
}