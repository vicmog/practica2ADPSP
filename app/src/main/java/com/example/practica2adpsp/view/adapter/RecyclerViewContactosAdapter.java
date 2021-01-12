package com.example.practica2adpsp.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica2adpsp.R;
import com.example.practica2adpsp.model.entity.Amigo;
import com.example.practica2adpsp.viewmodel.ViewModelCompartido;

import java.util.List;

public class RecyclerViewContactosAdapter extends RecyclerView.Adapter<RecyclerViewContactosAdapter.ViewHolder> {

    private List<Amigo> contactos;
    private ViewModelCompartido viewModelCompartido;
    private Activity activity;
    private View vista;


    public RecyclerViewContactosAdapter(List<Amigo> contactos, Activity act,View vista) {
        this.contactos = contactos;
        this.activity = act;
        this.vista = vista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemamigo,parent,false);
        viewModelCompartido = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModelCompartido.class);
        ViewHolder holder = new ViewHolder(vista);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NavController navController = Navigation.findNavController(vista);
        holder.tvNombre.setText(contactos.get(position).toString());

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = contactos.get(position).getTelefono();
                if(Amigo.esTelefonoFormato(tel)){
                    tel = Amigo.procesaNumero(tel);
                }
                contactos.get(position).setTelefono(tel);
                viewModelCompartido.insertAmigo(contactos.get(position));
                navController.navigate(R.id.firstFragment);

            }
        });
    }

    @Override
    public int getItemCount() {
        int count=0;
        if(contactos != null) {
            count=contactos.size();
        }
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombre;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            parent_layout = itemView.findViewById(R.id.clItemAmigo);
        }
    }
}
