package com.example.a2theot10.asynctaskandnetcomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.AsyncTask;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import android.app.AlertDialog;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    class MyTask extends AsyncTask<String,Void,String>
    {
        public String doInBackground(String... params)
        {
            HttpURLConnection conn = null;
            try
            {
                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/addhit.php");
                conn = (HttpURLConnection) url.openConnection();

                String song = URLEncoder.encode(params[0], "UTF-8");
                String artist = URLEncoder.encode(params[1], "UTF-8");
                String year = URLEncoder.encode(params[2], "UTF-8");
                String postData = "song=" +song +
                        "&artist=" +artist+
                        "&year="+year;

                // For POST
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(artist.length());

                OutputStream out = null;
                out = conn.getOutputStream();
                out.write(artist.getBytes());
                if(conn.getResponseCode() == 200)
                {
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String all = "", line;
                    while((line = br.readLine()) !=null)
                        all += line;
                    return all;
                }
                else
                {
                    return "HTTP ERROR: " + conn.getResponseCode();
                }
            }
            catch(IOException e)
            {
                return e.toString();
            }
            finally
            {
                if(conn!=null)
                {
                    conn.disconnect();
                }
            }
        }

        public void onPostExecute(String result)
        {

            new AlertDialog.Builder(MainActivity.this).
                    setMessage("Server sent back: " + result).
                    setPositiveButton("OK", null).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.btn1);
        b.setOnClickListener(this);
    }
    public void onClick(View view)
    {
        MyTask task = new MyTask();
        EditText et = (EditText)findViewById(R.id.et1);
        String song = et.getText().toString();
        String artist = et.getText().toString();
        String year = et.getText().toString();
        task.execute(song,artist,year);

    }

}
