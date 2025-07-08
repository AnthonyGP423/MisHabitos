package com.sise.mishabitos.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sise.mishabitos.R;
import com.sise.mishabitos.entities.Habito;

import java.util.ArrayList;
import java.util.List;

public class HabitoAdapter extends RecyclerView.Adapter<HabitoAdapter.HabitoViewHolder> {

    private static final String TAG = "HabitoAdapter";

    private List<Habito> listaHabitos;
    private final Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditarClick(Habito habito);
        void onCompletarClick(Habito habito);
    }

    public HabitoAdapter(Context context, List<Habito> listaHabitos, OnItemClickListener listener) {
        this.context = context;
        this.listaHabitos = listaHabitos != null ? listaHabitos : new ArrayList<>();
        this.listener = listener;
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

        boolean completado = habito.isCompletadoLocal();

        if (completado) {
            holder.itemView.setBackgroundColor(Color.parseColor("#A5D6A7"));  // Verde
            holder.btnCompletar.setText("Completado ✅");
            holder.btnCompletar.setEnabled(false);
            holder.btnCompletar.setAlpha(0.5f);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.btnCompletar.setText("Completar");
            holder.btnCompletar.setEnabled(true);
            holder.btnCompletar.setAlpha(1f);
        }

        holder.btnEditar.setOnClickListener(v -> listener.onEditarClick(habito));

        holder.btnCompletar.setOnClickListener(v -> {
            if (!habito.isCompletadoLocal()) {
                listener.onCompletarClick(habito);
            } else {
                Toast.makeText(context, "Este hábito ya fue completado hoy ✅", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaHabitos != null ? listaHabitos.size() : 0;
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

    public void actualizarLista(List<Habito> nuevosHabitos) {
        if (nuevosHabitos == null) {
            Log.d(TAG, "Lista recibida es null, se asignará lista vacía.");
            this.listaHabitos = new ArrayList<>();
        } else {
            Log.d(TAG, "Actualizando lista de hábitos con " + nuevosHabitos.size() + " elementos.");
            this.listaHabitos = nuevosHabitos;
        }
        notifyDataSetChanged();
    }
}
