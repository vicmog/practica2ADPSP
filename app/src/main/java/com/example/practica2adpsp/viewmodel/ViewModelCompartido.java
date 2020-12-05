package com.example.practica2adpsp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.practica2adpsp.model.Repository;
import com.example.practica2adpsp.model.entity.Amigo;
import com.example.practica2adpsp.model.entity.Llamada;
import com.example.practica2adpsp.model.entity.NumeroLlamadasAmigo;

import java.util.List;

public class ViewModelCompartido extends AndroidViewModel {

    private Repository repos;
    private LiveData<List<Amigo>>liveListAmigos;
    private LiveData<List<NumeroLlamadasAmigo>>liveListNumeroLlamadas;
    private Amigo amigoUpdate;

    public ViewModelCompartido(@NonNull Application application) {
        super(application);
        repos = new Repository(application);
        liveListAmigos = repos.getLiveListAmigos();
        liveListNumeroLlamadas = repos.getLiveNumeroLlamadasAmigo();
    }

    public void buscaListaContactos() {
        repos.obtieneContactosAgenda();
    }

    public List<Amigo> getContactos() {
        return repos.getListaContactosAgenda();
    }

    public LiveData<List<Amigo>> getLiveListAmigos() {
        return liveListAmigos;
    }

    public void insertAmigo(Amigo a) {
        repos.insertAmigo(a);
    }

    public LiveData<List<NumeroLlamadasAmigo>> getLiveListNumeroLlamadas() {
        return liveListNumeroLlamadas;
    }

    public LiveData<List<Llamada>> getLiveListLlamadas() {
        return repos.getLiveListLlamadas();
    }

    public void delete(Amigo a) {
        repos.deleteAmigo(a);
    }

    public Amigo getAmigoUpdate() {
        return amigoUpdate;
    }

    public void setAmigoUpdate(Amigo amigoUpdate) {
        this.amigoUpdate = amigoUpdate;
    }

    public void updateAmigo(Amigo a) {
        repos.updateAmigo(a);
    }
}
