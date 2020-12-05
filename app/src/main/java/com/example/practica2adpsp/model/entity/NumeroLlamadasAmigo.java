package com.example.practica2adpsp.model.entity;

import androidx.room.Embedded;

public class NumeroLlamadasAmigo {
    @Embedded
    private Amigo amigo;

    private long count;

    public NumeroLlamadasAmigo() {
    }

    public NumeroLlamadasAmigo(Amigo amigo, long count) {
        this.amigo = amigo;
        this.count = count;
    }

    public Amigo getAmigo() {
        return amigo;
    }

    public void setAmigo(Amigo amigo) {
        this.amigo = amigo;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return amigo +"Llamadas perdidas " + count ;
    }
}
