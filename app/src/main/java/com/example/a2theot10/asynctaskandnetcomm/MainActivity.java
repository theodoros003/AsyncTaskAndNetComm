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
import android.content.Intent;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    class TestTask2 extends AsyncTask<String,Void,String>
    {
        public String doInBackground(String... params)
        {
            HttpURLConnection conn = null;
            try
            {
                String artist = URLEncoder.encode(params[0], "UTF-8");


                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/hits.php?artist="+artist);
                conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                if(conn.getResponseCode() == 200)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String result = "", line;
                    while((line = br.readLine()) !=null)
                    {
                        result += line+"\n";
                    }
                    return result;
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
            TextView tv = (TextView) findViewById(R.id.tv1);
            tv.setText(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button list = (Button)findViewById(R.id.btn1);
        list.setOnClickListener(this);
        Button  add= (Button)findViewById(R.id.btn2);
        add.setOnClickListener(this);
    }
    public void onClick(View view)
    {
        if (view.getId() == R.id.btn1){
            TestTask2 task = new TestTask2();
            EditText et = (EditText)findViewById(R.id.et1);
            String artist = et.getText().toString();
            task.execute(artist);
        }
        else if (view.getId() == R.id.btn2){
            Intent intent = new Intent(this,AddSongsActivity.class);
            startActivityForResult(intent,0);
        }
    }

}