package com.pbp.gd11_x_yyyy.ui.download;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.pbp.gd11_x_yyyy.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.adapterDownloadViewHolder> {
    private List<Download> download;
    private Context context;
    private View view;
    private int obj;
    //Context context;
    private static String dirPath;

    public DownloadAdapter(Context context, List<Download> download){
        this.context = context;
        this.download = download;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @NonNull
    @Override
    public DownloadAdapter.adapterDownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_download_rv, parent, false);
        dirPath = UtilityPR.getRootDirPath(context);
        return new adapterDownloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.adapterDownloadViewHolder holder, int position) {
        final Download downloadList = download.get(position);
        obj = downloadList.getId();

        holder.tvNama.setText(downloadList.getJudul());

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PRDownloader.cancel(obj);
            }
        });

        holder.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Status.RUNNING==PRDownloader.getStatus(obj)){
                    PRDownloader.pause(obj);
                    return;
                }
                holder.pb.setIndeterminate(true);
                holder.pb.getIndeterminateDrawable().setColorFilter(Color.BLUE,
                        PorterDuff.Mode.SRC_IN);
                if(Status.PAUSED == PRDownloader.getStatus(obj)){
                    PRDownloader.resume(obj);
                    return;
                }
                obj =
                        PRDownloader.download(downloadList.getLink(), dirPath, downloadList.getNamaFile())
                                .build()
                                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                    @Override
                                    public void onStartOrResume() {
                                        holder.pb.setIndeterminate(false);
                                        holder.btnStart.setEnabled(true);
                                        holder.btnCancel.setEnabled(true);
                                        holder.btnStart.setText("Hentikan");
                                        FancyToast.makeText(context,"Download dimulai!",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                                    }
                                })
                                .setOnPauseListener(new OnPauseListener() {
                                    @Override
                                    public void onPause() {
                                        holder.btnStart.setText("Teruskan");
                                        FancyToast.makeText(context,"Download dimulai!",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                                    }
                                })
                                .setOnCancelListener(new OnCancelListener() {
                                    @Override
                                    public void onCancel() {
                                        holder.btnStart.setEnabled(true);
                                        holder.btnCancel.setEnabled(false);
                                        holder.btnStart.setText("Download");
                                        holder.tvSize.setText("");
                                        obj = 0;
                                        holder.pb.setProgress(0);
                                        holder.pb.setIndeterminate(false);
                                        FancyToast.makeText(context,"File batal didownload !",FancyToast.LENGTH_LONG,FancyToast.WARNING,false).show();

                                    }
                                })
                                .setOnProgressListener(new OnProgressListener() {
                                    @Override
                                    public void onProgress(Progress progress) {
                                        holder.tvSize.setText(UtilityPR.getProgressDisplayLine(progress.currentBytes,
                                                progress.totalBytes));
                                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                        holder.pb.setProgress((int) progressPercent);
                                        holder.pb.setIndeterminate(false);
                                    }
                                })
                                .start(new OnDownloadListener() {
                                    @Override
                                    public void onDownloadComplete() {
                                        holder.btnStart.setEnabled(false);
                                        holder.btnCancel.setEnabled(false);
                                        holder.btnStart.setBackgroundColor(Color.GRAY);
                                        holder.btnCancel.setText("Berhasil");
                                        holder.btnStart.setText("Downloaded");
                                        FancyToast.makeText(context,"File berhasil didownload!",FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,false).show();
                                    }

                                    @Override
                                    public void onError(Error error) {
                                        holder.btnStart.setEnabled(true);
                                        holder.btnCancel.setEnabled(false);
                                        holder.btnStart.setText("Download");
                                        holder.tvSize.setText("");
                                        obj = 0;
                                        holder.pb.setIndeterminate(false);
                                        holder.pb.setProgress(0);
                                        FancyToast.makeText(context,"Kesalahan Jaringan!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                                    }
                                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return download.size();
    }

    public class adapterDownloadViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNama, tvSize, btnCancel, btnStart;
        private ProgressBar pb;
        //private String dirPath;

        public adapterDownloadViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvSize = itemView.findViewById(R.id.tvSize);
            btnCancel = (TextView) itemView.findViewById(R.id.btnCancelPertama);
            btnStart = (TextView) itemView.findViewById(R.id.btnStartPertama);
            pb = itemView.findViewById(R.id.pb1);
            // dirPath = UtilityPR.getRootDirPath(itemView.getContext());

        }
    }
}
