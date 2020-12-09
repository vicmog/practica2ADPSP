package com.example.practica2adpsp.model.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.practica2adpsp.model.Repository;
import com.example.practica2adpsp.model.entity.Amigo;
import com.example.practica2adpsp.model.entity.Llamada;
import com.example.practica2adpsp.viewmodel.ViewModelCompartido;

import java.util.Calendar;

public class InComingCallsReceiver extends BroadcastReceiver {

private Repository repos;
private static String number;
private Context ctx;


    @Override
    public void onReceive(Context context, Intent intent) {
        ctx = context;
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){

             number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);


        }else if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){

            repos = new Repository(context);
            number =  Amigo.procesaNumero(number);
            repos.getAmigoLlamada(number);
        }
        }
    }
