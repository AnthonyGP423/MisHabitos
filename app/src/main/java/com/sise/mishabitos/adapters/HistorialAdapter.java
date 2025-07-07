package com.sise.mishabitos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sise.mishabitos.R;
import com.sise.mishabitos.entities.Seguimiento;

import java.text.SimpleDateFormat;
import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private List<Seguimiento> listaHistorial;

    public HistorialAdapter(List<Seguimiento> listaHistorial) {
        this.listaHistorial = listaHistorial;
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        return new HistorialViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        Seguimiento s = listaHistorial.get(position);

        holder.txtNombreHabito.setText(s.getHabito().getNombre());

        if (s.getFecha() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaStr = sdf.format(s.getFecha());
            holder.txtFechaSeguimiento.setText(fechaStr);
        } else {
            holder.txtFechaSeguimiento.setText("Sin fecha");
        }

        holder.txtEstadoSeguimiento.setText(s.getEstadoAuditoria() ? "✅ Completado" : "❌ Incompleto");
    }


    @Override
    public int getItemCount() {
        return listaHistorial.size();
    }

    public void actualizarLista(List<Seguimiento> nuevaLista) {
        this.listaHistorial.clear();
        this.listaHistorial.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    static class HistorialViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreHabito, txtFechaSeguimiento, txtEstadoSeguimiento;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreHabito = itemView.findViewById(R.id.txtNombreHabito);
            txtFechaSeguimiento = itemView.findViewById(R.id.txtFechaSeguimiento);
            txtEstadoSeguimiento = itemView.findViewById(R.id.txtEstadoSeguimiento);
        }
    }
}
