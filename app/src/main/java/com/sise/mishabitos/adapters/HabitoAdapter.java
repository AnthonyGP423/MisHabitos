package com.sise.mishabitos.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sise.mishabitos.R;
import com.sise.mishabitos.activities.EditarHabitoActivity;
import com.sise.mishabitos.entities.Habito;

import java.util.List;

public class HabitoAdapter extends RecyclerView.Adapter<HabitoAdapter.HabitoViewHolder> {

    private List<Habito> listaHabitos;
    private Context context;

    public HabitoAdapter(Context context, List<Habito> listaHabitos) {
        this.context = context;
        this.listaHabitos = listaHabitos;
    }

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habito, parent, false);
        return new HabitoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitoViewHolder holder, int position) {
        Habito habito = listaHabitos.get(position);
        holder.txtNombre.setText(habito.getNombre());
        holder.txtDescripcion.setText(habito.getDescripcion());
        holder.txtHora.setText("Hora: " + habito.getHoraSugerida());

        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditarHabitoActivity.class);
            intent.putExtra("habito", habito);
            context.startActivity(intent);
        });

        holder.btnCompletar.setOnClickListener(v -> {
            // TODO: lógica para marcar como completado
            // Por ejemplo: actualizar seguimiento, mostrar mensaje, etc.
        });
    }

    @Override
    public int getItemCount() {
        return listaHabitos.size();
    }

    public static class HabitoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtDescripcion, txtHora;
        Button btnEditar, btnCompletar;

        public HabitoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreHabito);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionHabito);
            txtHora = itemView.findViewById(R.id.txtHoraSugerida);
            btnEditar = itemView.findViewById(R.id.btnEditarHabito);
            btnCompletar = itemView.findViewById(R.id.btnCompletarHabito);
        }
    }
}
