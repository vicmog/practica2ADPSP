package com.example.practica2adpsp.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "amigos", indices = {@Index(value = {"telefono"}, unique = true)})
public class Amigo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String nombre;

    @NonNull
    @ColumnInfo(name = "telefono")
    private String telefono;


    @ColumnInfo(name = "fechaNacimiento")
    private String fechaNacimiento;

    public Amigo() {
        this("","","");

    }
    public Amigo(@NonNull String nombre, @NonNull String telefono,String fechaNacimiento) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NonNull String telefono) {
        this.telefono = telefono;
    }

    @NonNull
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return  nombre +"   " + telefono + "   " + fechaNacimiento + "\n";

    }
    public static String procesaNumero(String phoneNumber){
    char[]numeroProcesado = new char[]{'(',phoneNumber.charAt(0),phoneNumber.charAt(1),phoneNumber.charAt(2),')',' ',phoneNumber.charAt(3),phoneNumber.charAt(4),phoneNumber.charAt(5),'-',phoneNumber.charAt(6),phoneNumber.charAt(7),phoneNumber.charAt(8)};
    String numero="";

        for (int i = 0; i <numeroProcesado.length ; i++) {
            numero += numeroProcesado[i];
        }
    return numero;
    }

    public static String desProcesaNumero(String phoneNumber){
        String numero="";
        try{
            char[]numeroProcesado = new char[]{phoneNumber.charAt(1),phoneNumber.charAt(2),phoneNumber.charAt(3),phoneNumber.charAt(6),phoneNumber.charAt(7),phoneNumber.charAt(8),phoneNumber.charAt(10),phoneNumber.charAt(11),phoneNumber.charAt(12)};

            for (int i = 0; i <numeroProcesado.length ; i++) {
                numero += numeroProcesado[i];
            }

        }catch (NullPointerException ex){

        }

        return numero;
    }


    public static boolean esTelefonoFormato(String num) {
        if (num.length()==9){
            for (int i = 0; i < num.length(); i++) {
                if (!Character.isDigit(num.charAt(i))){
                    return false;
                }
            }
        }else{
            return false;
        }

        return true;
    }

}
