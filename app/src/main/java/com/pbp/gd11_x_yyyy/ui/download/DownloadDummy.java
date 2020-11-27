package com.pbp.gd11_x_yyyy.ui.download;

import java.util.ArrayList;

public class DownloadDummy {
    public ArrayList<Download> DOWNLOAD;
    //String judul, String namaFile, String link
    public DownloadDummy(){
        DOWNLOAD = new ArrayList();
        DOWNLOAD.add(PDF);
        DOWNLOAD.add(Gambar);
        DOWNLOAD.add(Musik);
    }

    public static final com.pbp.gd11_x_yyyy.ui.download.Download Gambar = new com.pbp.gd11_x_yyyy.ui.download.Download("Gambar", "punten_pecel.jpg", "https://pelangidb.com/pbp/download/punten_pecel.jpg", 1);
    public static final com.pbp.gd11_x_yyyy.ui.download.Download Musik = new com.pbp.gd11_x_yyyy.ui.download.Download( "Musik", "Passionate_Anthem_Roselia.mp3", "https://pelangidb.com/pbp/download/Passionate_Anthem_Roselia.mp3",2 );
    public static final com.pbp.gd11_x_yyyy.ui.download.Download PDF = new com.pbp.gd11_x_yyyy.ui.download.Download("PDF", "stakeholder.pdf", "https://scielo.conicyt.cl/pdf/jotmi/v10n2/art04.pdf", 3);

}
