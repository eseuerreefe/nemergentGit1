package com.jaure.nemergentgit1;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.List;

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.MiViewHolder> {

    List<Foto> lista;
    Context ctx;
    OnFotoClickListener escuchador;

    public interface OnFotoClickListener {
        void onFotoClick(Foto f, int pos);
        void onBorrarClick(Foto f, int pos);
    }

    public FotoAdapter(Context c, List<Foto> l, OnFotoClickListener listener) {
        ctx = c;
        lista = l;
        escuchador = listener;
    }

    @Override
    public MiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_foto, parent, false);
        return new MiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MiViewHolder holder, int position) {
        Foto foto = lista.get(position);

        holder.fecha.setText(foto.getFechacaptura());
        holder.imagen.setImageURI(Uri.parse(foto.getRutafoto()));

        holder.itemView.setOnClickListener(v -> escuchador.onFotoClick(foto, position));

        holder.btn.setOnClickListener(v -> escuchador.onBorrarClick(foto, position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void refrescar(List<Foto> nueva) {
        lista = nueva;
        notifyDataSetChanged();
    }

    public static class MiViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView fecha;
        Button btn;

        public MiViewHolder(View v) {
            super(v);
            imagen = v.findViewById(R.id.imgFoto);
            fecha = v.findViewById(R.id.tvFecha);
            btn = v.findViewById(R.id.btnBorrar);
        }
    }
}