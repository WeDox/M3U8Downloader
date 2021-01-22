package com.onedream.jdm3u8downloader.downloader;

import androidx.annotation.NonNull;

import com.onedream.jdm3u8downloader.bean.JDM3U8TsBean;
import com.onedream.jdm3u8downloader.listener.JDM3U8DownloaderContract;

import java.io.File;

/**
 * 抽象m3u8相关文件下载器
 *
 * @author jdallen
 * @since 2021/1/22
 */
public abstract class JDM3U8AbstractDownloader {
    public abstract void downloadM3U8MultiRateFileContent(String urlPath, @NonNull final JDM3U8DownloaderContract.GetM3U8SingleRateContentListener baseDownloadListener);

    public abstract void downloadM3U8SingleRateFileContent(String urlPath, @NonNull final JDM3U8DownloaderContract.BaseDownloadListener baseDownloadListener);

    public abstract int downLoadTsFile(JDM3U8TsBean m3U8TsBean, File tempSaveFile);
}
