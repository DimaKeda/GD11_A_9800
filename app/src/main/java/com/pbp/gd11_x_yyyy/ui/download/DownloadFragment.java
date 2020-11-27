package com.pbp.gd11_x_yyyy.ui.download;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.downloader.Status;
import com.pbp.gd11_x_yyyy.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class DownloadFragment extends Fragment {
    Button btnStartPertama;
    Button btnCancelPertama;
    TextView tvProgressPertama;
    ProgressBar pb1;
    RecyclerView recyclerView;
    DownloadAdapter adapter;
    View root;
    List<Download> downloadList;
    private static String dirPath;
    int downloadIdPertama;
    private DownloadViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(DownloadViewModel.class);
        root = inflater.inflate(R.layout.fragment_download, container, false);

        //Inisialisasi PRDownloader
        PRDownloader.initialize(getContext());
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getContext(), config);

        //inisialisasi item list
        downloadList = new DownloadDummy().DOWNLOAD;
        recyclerView = root.findViewById(R.id.rvDownload);
        adapter = new DownloadAdapter(root.getContext(), downloadList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        dirPath = UtilityPR.getRootDirPath(getActivity());
        tvProgressPertama = root.findViewById(R.id.tvSize);
        pb1 = root.findViewById(R.id.pb1);
        btnStartPertama = root.findViewById(R.id.btnStartPertama);
        btnCancelPertama = root.findViewById(R.id.btnCancelPertama);
        return root;
    }
}