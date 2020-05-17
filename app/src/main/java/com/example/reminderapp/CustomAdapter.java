package com.example.reminderapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private List<Yapilacak> yapilacakList;

    private OnNoteListener mOnNoteListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView notName,notIcerik,notTarih,notSaat,notDurumu;


        OnNoteListener onNoteListener;

        public MyViewHolder(View v, OnNoteListener onNoteListener) {
            super(v);

            this.onNoteListener = onNoteListener;
            //Düzenlenecek layoutta
            notName = v.findViewById(R.id.notName);
            notIcerik = v.findViewById(R.id.notIcerik);
            notTarih = v.findViewById(R.id.notTarih);
            notSaat=v.findViewById(R.id.notSaat);
            notDurumu = v.findViewById(R.id.durum);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomAdapter(List<Yapilacak> yapilacakList, OnNoteListener onNoteListener) {
        this.yapilacakList = yapilacakList;
        this.mOnNoteListener = onNoteListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);

        return new MyViewHolder(itemView,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Yapilacak yap = yapilacakList.get(position);
        holder. notName.setText("Not Başlık:"+yap.getYapilacak());
        holder.notIcerik.setText("Açıklama:"+yap.getEtiket());
        holder.notTarih.setText("Tarih:"+yap.getTarih());
        holder.notSaat.setText("Saat:"+yap.getSaat());
        holder.notDurumu.setText(yap.getDurum());


        if (yap.getDurum().equals("1")){
            holder.notDurumu.setText("Görev Tamamlandı");
            holder.notDurumu.setTextColor(Color.parseColor("#008000"));
        }
        else{
            holder.notDurumu.setText("Görev Tamamlanmadı");
            holder.notDurumu.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        return yapilacakList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}