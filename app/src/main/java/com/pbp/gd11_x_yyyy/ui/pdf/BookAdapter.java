package com.pbp.gd11_x_yyyy.ui.pdf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pbp.gd11_x_yyyy.R;
import com.pbp.gd11_x_yyyy.ui.api.BookAPI;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.adapterBukuViewHolder> {
    private List<Book> bukuList;
    private Context context;
    private View view;

    public BookAdapter(Context context, List<Book> bukuList){
        this.context = context;
        this.bukuList = bukuList;
    }


    @NonNull
    @Override
    public BookAdapter.adapterBukuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_book_rv, parent, false);
        return new adapterBukuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.adapterBukuViewHolder holder, int position) {
        final Book buku = bukuList.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");

        holder.tvJudul.setText(buku.getNamaBuku());
        holder.tvPengarang.setText(buku.getPengarang());
        holder.tvHarga.setText("Rp "+ formatter.format(buku.getHarga()));
        Glide.with(context)
                .load(BookAPI.URL_IMAGE+buku.getGambar())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.ivGambar);
        //Log.i("Adapter Buku", "onBindViewHolder: "+buku.getNamaBuku());


    }

    @Override
    public int getItemCount() {
        return bukuList.size();
    }

    public class adapterBukuViewHolder extends RecyclerView.ViewHolder{
        private TextView tvJudul, tvPengarang, tvHarga;
        private ImageView ivGambar;
        public adapterBukuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudulBuku);
            tvPengarang = itemView.findViewById(R.id.tvNamaPengarang);
            tvHarga = itemView.findViewById(R.id.tvHarga);
            ivGambar = itemView.findViewById(R.id.ivGambarBuku);

        }
    }
}
