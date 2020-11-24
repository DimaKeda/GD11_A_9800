package com.pbp.gd11_x_yyyy.ui.download;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import java.io.File;
import java.util.List;

public class DownloadFragment extends Fragment {
    Button btnStartPertama;
    Button btnCancelPertama;
    Button btnStartKedua;
    Button btnCancelKedua;
    TextView tvProgressPertama;
    TextView tvProgressKedua;
    ProgressBar pb1;
    ProgressBar pb2;
    private static String dirPath;
    int downloadIdPertama, downloadIdKedua;
    private DownloadViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(DownloadViewModel.class);
        View root = inflater.inflate(R.layout.fragment_download, container, false);
        //Inisialisasi PRDownloader
        PRDownloader.initialize(getContext());
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getContext(), config);
        dirPath = UtilityPR.getRootDirPath(getActivity());
        btnStartPertama=root.findViewById(R.id.btnStartPertama);
        btnCancelPertama=root.findViewById(R.id.btnCancelPertama);
        tvProgressPertama=root.findViewById(R.id.tvProgressPertama);
        tvProgressKedua=root.findViewById(R.id.tvProgressKedua);
        pb1=root.findViewById(R.id.pb1);
        pb2=root.findViewById(R.id.pb2);
        btnStartKedua=root.findViewById(R.id.btnStartKedua);
        btnCancelKedua=root.findViewById(R.id.btnCancelKedua);
//action cancel
        btnCancelPertama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PRDownloader.cancel(downloadIdPertama);
            }
        });
        btnCancelKedua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PRDownloader.cancel(downloadIdKedua);
            }
        });
//action download
        btnStartPertama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Status.RUNNING == PRDownloader.getStatus(downloadIdPertama)) {
                    PRDownloader.pause(downloadIdPertama);
                    return;
                }
                pb1.setIndeterminate(true);
                pb1.getIndeterminateDrawable().setColorFilter(Color.BLUE,
                        PorterDuff.Mode.SRC_IN);
                if (Status.PAUSED == PRDownloader.getStatus(downloadIdPertama)) {
                    PRDownloader.resume(downloadIdPertama);
                    return;
                }
                downloadIdPertama =
                        PRDownloader.download("https://pelangidb.com/pbp/download/punten_pecel.jpg",
                                dirPath, "punten_pecel.jpg")
                                .build()
                                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                    @Override
                                    public void onStartOrResume() {
                                        pb1.setIndeterminate(false);
                                        btnStartPertama.setEnabled(true);
                                        btnCancelPertama.setEnabled(true);
                                        btnStartPertama.setText("Hentikan");
                                        FancyToast.makeText(getContext(),"Download dimulai!",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                                    }
                                })
                                .setOnPauseListener(new OnPauseListener() {
                                    @Override
                                    public void onPause() {
                                        btnStartPertama.setText("Teruskan");
                                        FancyToast.makeText(getContext(),"Download dihentikan sementara!",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                                    }
                                })
                                .setOnCancelListener(new OnCancelListener() {
                                    @Override
                                    public void onCancel() {
                                        btnStartPertama.setEnabled(true);
                                        btnCancelPertama.setEnabled(false);
                                        btnStartPertama.setText("Download");
                                        tvProgressPertama.setText("");
                                        downloadIdPertama=0;
                                        pb1.setProgress(0);
                                        pb1.setIndeterminate(false);
                                        FancyToast.makeText(getContext(),"File batal didownload !",FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();
                                    }
                                })
                                .setOnProgressListener(new OnProgressListener() {
                                    @Override
                                    public void onProgress(Progress progress) {
                                        tvProgressPertama.setText(UtilityPR.getProgressDisplayLine(progress.currentBytes,
                                                progress.totalBytes));
                                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                        pb1.setProgress((int) progressPercent);
                                        pb1.setIndeterminate(false);
                                    }
                                })
                                .start(new OnDownloadListener() {
                                    @Override
                                    public void onDownloadComplete() {
                                        btnStartPertama.setEnabled(false);
                                        btnCancelPertama.setEnabled(false);
                                        btnStartPertama.setBackgroundColor(Color.GRAY);
                                        btnCancelPertama.setText("Berhasil");
                                        btnStartPertama.setText("Downloaded");
                                        FancyToast.makeText(getContext(),"File berhasil didownload!",FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,false).show();
                                    }
                                    @Override
                                    public void onError(Error error) {
                                        btnStartPertama.setEnabled(true);
                                        btnCancelPertama.setEnabled(false);
                                        btnStartPertama.setText("Download");
                                        tvProgressPertama.setText("");
                                        downloadIdPertama=0;
                                        pb1.setIndeterminate(false);
                                        pb1.setProgress(0);
                                        FancyToast.makeText(getContext(),"Kesalahan Jaringan!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                                    }
                                });
            }
        });
        btnStartKedua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Status.RUNNING == PRDownloader.getStatus(downloadIdKedua)) {
                    PRDownloader.pause(downloadIdKedua);
                    return;
                }
                pb2.setIndeterminate(true);
                pb2.getIndeterminateDrawable().setColorFilter(Color.BLUE,
                        PorterDuff.Mode.SRC_IN);
                if (Status.PAUSED == PRDownloader.getStatus(downloadIdKedua)) {
                    PRDownloader.resume(downloadIdKedua);
                    return;
                }
                downloadIdKedua =
                        PRDownloader.download("https://pelangidb.com/pbp/download/Passionate_Anthem_Roselia.mp3",
                                dirPath, "Passionate_Anthem_Roselia.mp3")
                                .build()
                                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                    @Override
                                    public void onStartOrResume() {
                                        pb1.setIndeterminate(false);
                                        btnStartKedua.setEnabled(true);
                                        btnCancelKedua.setEnabled(true);
                                        btnStartKedua.setText("Hentikan");
                                        FancyToast.makeText(getContext(),"Download dimulai!",FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();
                                    }
                                })
                                .setOnPauseListener(new OnPauseListener() {
                                    @Override
                                    public void onPause() {
                                        btnStartKedua.setText("Teruskan");
                                        FancyToast.makeText(getContext(),"Download dihentikan sementara!",FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();
                                    }
                                })
                                .setOnCancelListener(new OnCancelListener() {
                                    @Override
                                    public void onCancel() {
                                        btnStartKedua.setEnabled(true);
                                        btnCancelKedua.setEnabled(false);
                                        btnStartKedua.setText("Download");
                                        tvProgressKedua.setText("");
                                        downloadIdKedua=0;
                                        pb2.setProgress(0);
                                        pb2.setIndeterminate(false);
                                        FancyToast.makeText(getContext(),"File batal didownload !",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                                    }
                                })
                                .setOnProgressListener(new OnProgressListener() {
                                    @Override
                                    public void onProgress(Progress progress) {
                                        tvProgressKedua.setText(UtilityPR.getProgressDisplayLine(progress.currentBytes,
                                                progress.totalBytes));
                                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                        pb2.setProgress((int) progressPercent);
                                        pb2.setIndeterminate(false);
                                    }
                                })
                                .start(new OnDownloadListener() {
                                    @Override
                                    public void onDownloadComplete() {
                                        btnStartKedua.setEnabled(false);
                                        btnCancelKedua.setEnabled(false);
                                        btnStartKedua.setBackgroundColor(Color.GRAY);
                                        btnCancelKedua.setText("Berhasil");
                                        btnStartKedua.setText("Downloaded");
                                        FancyToast.makeText(getContext(),"File berhasil didownload!",FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show();
                                    }
                                    @Override
                                    public void onError(Error error) {
                                        btnStartKedua.setEnabled(true);
                                        btnCancelKedua.setEnabled(false);
                                        btnStartKedua.setText("Download");
                                        tvProgressKedua.setText("");
                                        downloadIdKedua=0;
                                        pb2.setIndeterminate(false);
                                        pb2.setProgress(0);
                                        FancyToast.makeText(getContext(),"Kesalahan Jaringan!",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                                    }
                                });
            }
        });
        return root;
    }


}