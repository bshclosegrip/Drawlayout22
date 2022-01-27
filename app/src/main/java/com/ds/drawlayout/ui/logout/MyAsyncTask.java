package com.ds.drawlayout.ui.logout;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {

    TextView textView;
    ProgressBar progressBar;

    public MyAsyncTask(TextView textView, ProgressBar progressbar) {
        this.textView = textView;
        this.progressBar = progressbar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setMax(100);
        progressBar.setProgress(10);
    }

    @Override
    protected Boolean doInBackground(Void... strings) {
        for(int i=0; i<= 100; i++) {
            publishProgress(i);
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0].intValue());
        textView.setText(values[0].toString());
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Boolean s) {
        super.onCancelled(s);
    }
}