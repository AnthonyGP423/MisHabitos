package com.sise.mishabitos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sise.mishabitos.R;
import com.sise.mishabitos.entities.Seguimiento;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private List<Seguimiento> listaSeguimientos;

    public HistorialAdapter(List<Seguimiento> listaSeguimientos) {
        this.listaSeguimientos = listaSeguimientos;
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        return new HistorialViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        Seguimiento seguimiento = listaSeguimientos.get(position);

        holder.txtNombreHabito.setText(seguimiento.getHabito().getNombre());
        holder.txtFechaSeguimiento.setText("Fecha: " + seguimiento.getFechaSeguimiento());
        holder.txtEstadoSeguimiento.setText(seguimiento.isCompletado() ? "✅ Completado" : "❌ No completado");
    }

    @Override
    public int getItemCount() {
        return listaSeguimientos.size();
    }

    public static class HistorialViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreHabito, txtFechaSeguimiento, txtEstadoSeguimiento;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreHabito = itemView.findViewById(R.id.txtNombreHabito);
            txtFechaSeguimiento = itemView.findViewById(R.id.txtFechaSeguimiento);
            txtEstadoSeguimiento = itemView.findViewById(R.id.txtEstadoSeguimiento);
        }
    }
}
