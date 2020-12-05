package com.example.practica2adpsp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica2adpsp.R;
import com.example.practica2adpsp.model.entity.Amigo;
import com.example.practica2adpsp.model.entity.NumeroLlamadasAmigo;
import com.example.practica2adpsp.view.fragment.AmigoUpdateFragment;
import com.example.practica2adpsp.viewmodel.ViewModelCompartido;

import java.util.List;

public class RecylcerAdapterListaAmigos extends RecyclerView.Adapter<RecylcerAdapterListaAmigos.ViewHolder> implements PopupMenu.OnMenuItemClickListener {

    private List<NumeroLlamadasAmigo> numeroLlamadas;
    private ViewModelCompartido viewModelCompartido;
    private Activity activity;
    private View vista;
    private Amigo amigo;
    private NavController navController;

    public RecylcerAdapterListaAmigos(List<NumeroLlamadasAmigo> numeroLlamadasAmigo, Activity activity, View vista) {
        this.numeroLlamadas = numeroLlamadasAmigo;
        this.activity = activity;
        this.vista = vista;
        navController = Navigation.findNavController(vista);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemamigo,parent,false);
        viewModelCompartido = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModelCompartido.class);
        RecylcerAdapterListaAmigos.ViewHolder holder = new RecylcerAdapterListaAmigos.ViewHolder(vista);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNombre.setText(numeroLlamadas.get(position).toString());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumeroLlamadasAmigo numeroLlamadasAmigo = numeroLlamadas.get(position);
                amigo = numeroLlamadasAmigo.getAmigo();
                showMenu(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return numeroLlamadas.size();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){

            case R.id.UpdatePopup:
                viewModelCompartido.setAmigoUpdate(amigo);
                navController.navigate(R.id.amigoUpdateFragment);

                break;
            case R.id.deletePopup:
                viewModelCompartido.delete(amigo);
                break;

        }
        return true;
    }
    public void showMenu(View anchor) {
        PopupMenu popup = new PopupMenu(activity, anchor);
        popup.setOnMenuItemClickListener(this);
        popup.getMenuInflater().inflate(R.menu.menupopup, popup.getMenu());
        popup.show();

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
