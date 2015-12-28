package com.example.trevin.flicker;

/**
 * Created by Trevin on 12/27/2015.  This is a test branch
 */
enum DownloadStatus{IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}

public class GetRawData {
    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownLoadStatus mdownloadStatus;

    public GetRawData[String mRawUrl] {
        this.mRawUrl = mRawUrl;
    }
}
