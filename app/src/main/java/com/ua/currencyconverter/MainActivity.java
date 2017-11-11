package com.ua.currencyconverter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);


    }


    public void getRates(View view){
        DownloadData downloadData = new DownloadData();
        String url = "https://api.fixer.io/latest?base="+editText.getText();
        try {
            downloadData.execute(url);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class DownloadData extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String rates = jsonObject.getString("rates");
                JSONObject object = new JSONObject(rates);
                String tl = object.getString("TRY");
                String chf = object.getString("CHF");
                String czk = object.getString("CZK");

                textView1.setText(tl);
                textView2.setText(chf);
                textView3.setText(czk);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                 url = new URL(params[0]);
                 httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream stream = httpURLConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(stream);
                int data = streamReader.read();
                while (data > 0){
                    char character =  (char) data;
                    result += character;
                    data = streamReader.read();
                }
                return result;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
