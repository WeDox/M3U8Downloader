package com.onedream.m3u8downloader;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.onedream.jdm3u8downloader.JDM3U8Downloader;
import com.onedream.jdm3u8downloader.bean.JDDownloadQueue;
import com.onedream.jdm3u8downloader.common.JDDownloadQueueState;
import com.onedream.jdm3u8downloader.listener.JDM3U8DownloaderContract;
import com.onedream.jdm3u8downloader.utils.JDM3U8FileCacheUtils;
import com.onedream.jdm3u8downloader.utils.JDM3U8LogHelper;
import com.onedream.m3u8downloader.okhttp_file_downloader.OkHttpFileDownloaderFactory;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        tv_show = findViewById(R.id.tv_show);
    }

    private void initData() {

    }

    private void initEvent() {
        tv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 自行请求存储权限
                startDownloadM3U8();
            }
        });
    }

    private void startDownloadM3U8() {
        JDDownloadQueue downloadQueue = new JDDownloadQueue();
        downloadQueue.setMovie_id(10);
        downloadQueue.setSingleRate(false);
        downloadQueue.setMovie_download_url("http://yi.jingdianzuida.com/20190905/yM4FKbnk/index.m3u8");
        downloadQueue.setMovie_title("蛇形叼手");
        downloadQueue.setMovie_num_index(0);
        downloadQueue.setMovie_num_title("第0集");
        downloadQueue.setState(JDDownloadQueueState.STATE_DOWNLOAD_QUEUE);//这个比较重要

        String PATH_MOVIE = JDM3U8FileCacheUtils.createRootDownloadPath(MainActivity.this) + File.separator + "download" + File.separator + "movie" + File.separator;
        //
        JDM3U8Downloader jdm3U8Downloader = new JDM3U8Downloader.Builder()
                .setSaveDir(PATH_MOVIE)
                .setDownloadQueue(downloadQueue)
                .setFileDownloaderFactory(OkHttpFileDownloaderFactory.create())
                .setDownloaderListener(new JDM3U8DownloaderContract.JDM3U8DownloadListener() {

                    @Override
                    public void downloadState(JDDownloadQueue downloadQueue, int downloadState, String msg) {
                        String logMsg = downloadQueue + "\n下载状态码" + downloadState + "\n【解释】==" + JDDownloadQueueState.getSateStr(downloadState) + "\n携带的消息：" + msg;
                        if (downloadState != JDDownloadQueueState.STATE_DOWNLOAD_FINISH) {
                            showText(logMsg);
                        }
                        JDM3U8LogHelper.printLog(logMsg);
                    }

                    @Override
                    public void downloadProgress(JDDownloadQueue downloadQueue, long sofar, long total) {
                        String logMsg = downloadQueue + "\n进度" + sofar + "\n总共" + total;
                        showText(logMsg);
                        JDM3U8LogHelper.printLog(logMsg);
                    }

                    @Override
                    public void downloadSuccess(JDDownloadQueue downloadQueue) {
                        JDM3U8LogHelper.printLog("下载成功事件：" + downloadQueue.getMovie_title());
                    }

                    @Override
                    public void downloadError(JDDownloadQueue downloadQueue, String errMsg) {
                        JDM3U8LogHelper.printLog(errMsg);
                    }

                    @Override
                    public void downloadPause(JDDownloadQueue downloadQueue) {
                        JDM3U8LogHelper.printLog("暂停下载" + downloadQueue.getMovie_title());
                    }
                })
                .build();
        jdm3U8Downloader.startDownload();
    }

    private void showText(final String content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_show.setText(content);
            }
        });
    }
}
