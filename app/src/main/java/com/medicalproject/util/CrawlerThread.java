package com.medicalproject.util;

import android.os.AsyncTask;

import com.example.lenovo.medicalProject.CrawlerActivity;

/**
 * @ClassName：Fragment1
 * @Description： 爬虫线程
 * @author：许鹤铭
 */
public class CrawlerThread extends AsyncTask<String, Integer, String>
{

    public  String content;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... strings) {
        content = Crawler.crawlerWork("阿莫西林");
        return content;
    }

    @Override
    protected void onPostExecute(String s) {

        CrawlerActivity.textView.setText(content);
    }

    @Override
    protected void onProgressUpdate(Integer... progresses) {
        super.onProgressUpdate();
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}
