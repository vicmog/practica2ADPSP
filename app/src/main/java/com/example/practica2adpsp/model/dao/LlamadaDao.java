package com.example.practica2adpsp.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.practica2adpsp.model.entity.Amigo;
import com.example.practica2adpsp.model.entity.Llamada;
import com.example.practica2adpsp.model.entity.NumeroLlamadasAmigo;

import java.util.List;

@Dao
public interface LlamadaDao {

    @Delete
    int delete(Llamada llamada);

    @Query("select * from llamadas where id = :id")
    Llamada get(int id);

    @Query("select * from llamadas")
    LiveData<List<Llamada>> getAll();
    @Insert
    long insert(Llamada llamadas);
    @Update
    int update (Llamada llamada);

    @Query("select a.id, a.telefono, a.nombre,a.fechaNacimiento, count(l.id) count from amigos a left join llamadas l on a.id = l.id_amigo group by a.id, a.telefono, a.nombre,a.fechaNacimiento order by l.fecha_llamada")
    LiveData<List<NumeroLlamadasAmigo>> getAllNumeroLlamadas();

}
