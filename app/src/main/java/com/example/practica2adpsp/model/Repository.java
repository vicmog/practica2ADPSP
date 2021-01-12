package com.example.practica2adpsp.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.lifecycle.LiveData;

import com.example.practica2adpsp.model.dao.AmigoDao;
import com.example.practica2adpsp.model.dao.LlamadaDao;
import com.example.practica2adpsp.model.entity.Amigo;
import com.example.practica2adpsp.model.entity.Llamada;
import com.example.practica2adpsp.model.entity.NumeroLlamadasAmigo;
import com.example.practica2adpsp.model.room.AmigosDataBase;
import com.example.practica2adpsp.util.UtilThread;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Repository {

    private Context context;
    private AmigosDataBase db;
    private AmigoDao amigoDao;
    private LlamadaDao llamadaDao;
    private List<Amigo> listaContactosAgenda = new ArrayList<>();;
    private LiveData<List<Amigo>>liveListAmigos;
    private LiveData<List<NumeroLlamadasAmigo>>liveNumeroLlamadasAmigo;
    private LiveData<List<Llamada>>liveListLlamadas;



    public Repository(Context c) {

    context = c;
    db = AmigosDataBase.getDb(c);
    amigoDao = db.getAmigoDao();
    llamadaDao = db.getLlamadaDao();
    liveListAmigos = amigoDao.getAll();
    liveNumeroLlamadasAmigo = llamadaDao.getAllNumeroLlamadas();
    liveListLlamadas = llamadaDao.getAll();
    }

    public LiveData<List<Llamada>> getLiveListLlamadas() {
        return liveListLlamadas;
    }

    public LiveData<List<NumeroLlamadasAmigo>> getLiveNumeroLlamadasAmigo() {
        return liveNumeroLlamadasAmigo;
    }

    public void obtieneContactosAgenda(){
        listaContactosAgenda.clear();
        Cursor cur = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String numero="";
        String fecha_nacimiento="";
        String nombre="";
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                nombre = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor cur2 = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id}, null);

                    while (cur2.moveToNext()) {
                        numero = cur2.getString(cur2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                    }
                    String columns[] = {
                            ContactsContract.CommonDataKinds.Event.START_DATE,
                            ContactsContract.CommonDataKinds.Event.TYPE,
                            ContactsContract.CommonDataKinds.Event.MIMETYPE,
                    };

                    String where = ContactsContract.CommonDataKinds.Event.TYPE + "=" + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY +
                            " and " + ContactsContract.CommonDataKinds.Event.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' and "+ ContactsContract.Data.CONTACT_ID + " = " + id;

                    String[] selectionArgs = null;
                    String sortOrder = ContactsContract.Contacts.DISPLAY_NAME;

                    Cursor birthdayCur = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, columns, where, selectionArgs, sortOrder);
                    if (birthdayCur.getCount() > 0) {
                        while (birthdayCur.moveToNext()) {
                           fecha_nacimiento = birthdayCur.getString(birthdayCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                        }
                    }

                    Amigo posibleAmigo = new Amigo(nombre,numero,fecha_nacimiento);
                    listaContactosAgenda.add(posibleAmigo);

                    birthdayCur.close();
                    cur2.close();
                }
            }
        }

    }

    public List<Amigo> getListaContactosAgenda(){

        return listaContactosAgenda;
    }
    public void deleteAmigo(Amigo a){
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                amigoDao.delete(a);
            }
        });

    }
    public void updateAmigo(Amigo a){
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                amigoDao.update(a);
            }
        });
    }

    public LiveData<List<Amigo>> getLiveListAmigos() {
        return liveListAmigos;
    }

    public void insertAmigo(Amigo a){

        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
                try{

                    amigoDao.insert(a);

                }catch (Exception exception){

                }

            }
        });

    }

    public void getAmigoLlamada(String number){

    Thread threadAmigoLlamada = new Thread(){
        @Override
        public void run() {
            super.run();
            int idAmigoLlamada = amigoDao.getIdAmigoLlamada(number);

            if(idAmigoLlamada!=0){
                Calendar calendar = Calendar.getInstance();
                int año = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH) + 1;
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                String fecha = dia+"/"+mes+"/"+año;
                Llamada llamada = new Llamada(idAmigoLlamada,fecha);
                llamadaDao.insert(llamada);
            }



        }
    };
    threadAmigoLlamada.start();
    }

    public void insertLlamada(Llamada llamada){
        UtilThread.threadExecutorPool.execute(new Runnable() {
            @Override
            public void run() {
            llamadaDao.insert(llamada);
            }
        });
    }




}

