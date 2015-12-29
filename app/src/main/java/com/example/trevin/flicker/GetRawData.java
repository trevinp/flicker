package com.example.trevin.flicker;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Trevin on 12/27/2015.  This is a test branch
 */
enum DownloadStatus{IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}

public class GetRawData {
    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownloadStatus mdownloadStatus;

    public GetRawData(String mRawUrl) {
        this.mRawUrl = mRawUrl;
        this.mdownloadStatus = DownloadStatus.IDLE;
    }

    public void reset() {
        this.mdownloadStatus = DownloadStatus.IDLE;
        this.mRawUrl = null;
                this.mData = null;
    }

    public DownloadStatus getMdownloadStatus() {
        return mdownloadStatus;
    }

    public String getmData() {
        return mData;
    }

    public void execute() {
        this.mdownloadStatus = DownloadStatus.PROCESSING;
        DownloadRawData downloadrawData = new DownloadRawData();
        downloadrawData.execute(mRawUrl);

    }
    public class DownloadRawData extends AsyncTask<String, Void,String> {
        protected void onPostExecute(String webData) {
            mData = webData;
            //Log.e(LOG_TAG, "Data returned was: ", mData);
            Log.d(LOG_TAG, "Data returned was:" + mData);
            if (mData == null) {
                if (mRawUrl == null) {
                    mdownloadStatus = DownloadStatus.NOT_INITIALIZED;
                } else {
                    mdownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            } else {
                mdownloadStatus = DownloadStatus.OK;
            }
        }
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            if (params == null)
                return null;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch(final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
        }
    }

}
