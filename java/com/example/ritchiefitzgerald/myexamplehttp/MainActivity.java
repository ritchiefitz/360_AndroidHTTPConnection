package com.example.ritchiefitzgerald.myexamplehttp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = new TextView(this);
        String html = "";

        try {
            html = new GetSource().execute().get();
        } catch (Exception e) {
            Log.d("custom", "Async Failed!");
            e.printStackTrace();
        }

        textView.setTextSize(20);
        textView.setText(html);
        Log.d("custom", "Finished Process");
        setContentView(textView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetSource extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            BufferedReader reader = null;

            try {
                URL url = null;

//              Happy path
//              Valid https url works
                try {
                    url = new URL("https://google.com");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                } catch(Exception e) {
                    e.printStackTrace();
                }

//              Nasty Path
//              Passing an empty string causes the following error.
//              System.err﹕ java.net.MalformedURLException: Protocol not found:
                try {
                    url = new URL("");
                } catch (Exception e) {
                    e.printStackTrace();
                }

//              Nasty Path
//              Passing a null value causes the following error.
//              System.err﹕ java.net.MalformedURLException
                try {
                    url = new URL(null);
                } catch(Exception e) {
                    e.printStackTrace();
                }

//              Nasty Path
//              Passing a url that doesn't exist gives us.
//              System.err﹕ java.net.UnknownHostException: Unable to resolve host: No address associated with hostname
                try {
                    url = new URL("http://asfgagqergqfasgdadsqwr.com/");
                } catch(Exception e) {
                    e.printStackTrace();
                }

//              My Happiest Path
//              Valid http url works
                try {
                    url = new URL("http://google.com");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                } catch(Exception e) {
                    e.printStackTrace();
                }

//              My Happiest Path
//              Valid http url to json works
                try {
                    url = new URL("http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=8rync8vtt4fnnvkag4bhygxw&q=Toy+Story&page_limit=1");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                } catch(Exception e) {
                    e.printStackTrace();
                }


                HttpURLConnection con = (HttpURLConnection) url.openConnection();


                StringBuilder sb = new StringBuilder();

//              Happy Path
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (line.equals("")) {
                        continue;
                    }

                    sb.append(line + "\n");
                }

                reader.close();

                Log.d("html", "HTML: " + sb.toString());
                return sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
