package com.pbp.gd11_x_yyyy.ui.home;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.pbp.gd11_x_yyyy.R;

import java.util.HashSet;
import java.util.Set;

import io.github.bleoo.windowImageView.WindowImageView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView rv;

    //TODO 1 - Ubah XXXX dengan Nama dan YYYYYYYYY dengan NIM pada textview di window0.xml
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        init();

        rv=root.findViewById(R.id.rvHome);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new MyAdapter());

        return root;
    }

    //inisialisasi Fresco
    private void init(){
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getContext())
                // other setters
                .setRequestListeners(requestListeners)
                .build();
        Fresco.initialize(getContext(), config);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);

    }

    //letakkan inner class MyAdapter di sini
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
        //menentukan view untuk memilih recycler view mana yang digunakan
        @Override
        public int getItemViewType(int position) {
            if (position==1 || position==3 || position==5 || position==7 || position==9) {
                return 1;
            }
            return 0;
        }
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == 1) {
                view = View.inflate(getContext(), R.layout.window1, null);
            } else {
                view = View.inflate(getContext(), R.layout.window0, null);
                RecyclerView.LayoutParams lp = new
                        RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(lp);
            }
            return new MyHolder(view);
        }
        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            if (position==1 || position==5 || position==9) {
                //menampilkan gambar pada recycler view dengan resource drawable
                holder.windowImageView.bindRecyclerView(rv);
                holder.windowImageView.setFrescoEnable(false);
            } else if (position==3 || position==7) {
                //menampilkan gambar pada recycler view dengan resource luar menggunakan fresco
                holder.windowImageView.bindRecyclerView(rv);
                holder.windowImageView.setFrescoEnable(true);
                holder.windowImageView.setImageURI(Uri.parse("https://unitepaper.com/wpcontent/uploads/2020/02/720x1600-wallpaper-hd-for-phone-536.jpg"));
            } else {
                holder.itemView.setBackgroundColor(Color.rgb((int) (Math.random() * 255), (int)
                        (Math.random() * 255), (int) (Math.random() * 255)));
            }
        }
        //Jika secara manual, kita bisa menentukan berapa jumlah item yang akan ditampilkan pada recycler view
        @Override
        public int getItemCount() {
            return 10;
        }
        class MyHolder extends RecyclerView.ViewHolder {
            WindowImageView windowImageView;
            public MyHolder(View itemView) {
                super(itemView);
                windowImageView = itemView.findViewById(R.id.wiv);
            }
        }
    }
}