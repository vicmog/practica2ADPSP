package com.example.practica2adpsp.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "llamadas")
public class Llamada {

    @PrimaryKey(autoGenerate = true)
    private int id;

    /*foreignKeys = @ForeignKey(
            entity = Amigo.class,
            parentColumns = "id",
            childColumns = "id_amigo",
            onDelete = ForeignKey.RESTRICT)*/
    @NonNull
    @ColumnInfo(name = "id_amigo")
    private int id_amigo;

    @NonNull
    @ColumnInfo(name = "fecha_llamada")
    private String fecha_llamada;

    public Llamada() {
    }

    public Llamada(int id_amigo, @NonNull String fecha_llamada) {
        this.id_amigo = id_amigo;
        this.fecha_llamada = fecha_llamada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_amigo() {
        return id_amigo;
    }

    public void setId_amigo(int id_amigo) {
        this.id_amigo = id_amigo;
    }

    @NonNull
    public String getFecha_llamada() {
        return fecha_llamada;
    }

    public void setFecha_llamada(@NonNull String fecha_llamada) {
        this.fecha_llamada = fecha_llamada;
    }

    @Override
    public String toString() {
        return "Llamada{" +
                "id=" + id +
                ", id_amigo=" + id_amigo +
                ", fecha_llamada='" + fecha_llamada + '\'' +
                '}';
    }
}
