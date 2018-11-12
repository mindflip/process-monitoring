package com.example.user.webviewtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_TOPIC = "mon";

    private ImageView ivMon, ivComp, ivCal, ivSetIP;

    private SharedPreferences appData;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        FirebaseMessaging.getInstance().subscribeToTopic(MAIN_TOPIC);
        appData = getSharedPreferences("pref", MODE_PRIVATE);
        editor = appData.edit();
        for(int i = 1; i < 5; i++){
            editor.putString("cam"+i, "211.180.149.107:719" + i + "/video/mjpeg");
            editor.commit();
        }

        layoutInit();
    }

    private void layoutInit(){
        ivMon = (ImageView) findViewById(R.id.ivMon);
        ivComp = (ImageView) findViewById(R.id.ivComp);
        ivCal = (ImageView) findViewById(R.id.ivCal);
        ivSetIP = (ImageView) findViewById(R.id.ivSetIP);

        ivMon.setOnClickListener(imageOnClickListener);
        ivComp.setOnClickListener(imageOnClickListener);
        ivCal.setOnClickListener(imageOnClickListener);
        ivSetIP.setOnClickListener(imageOnClickListener);
    }

    ImageView.OnClickListener imageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch(v.getId()){
                case R.id.ivMon :
                    intent = new Intent(MainActivity.this, OverviewActivity.class);

                    for(int i = 1 ; i < 5 ; i ++){
                        intent.putExtra("ip"+i, appData.getString("cam"+i,""));
                    }
                    startActivity(intent);
                    break;

                case R.id.ivComp :
                    intent = new Intent(MainActivity.this, CamActivity.class);
                    intent.putExtra("title", getString(R.string.tvCompTitle));
                    intent.putExtra("ip", "211.180.149.107:7190/video/mjpeg");
                    startActivity(intent);
                    break;

                case R.id.ivCal :
                    intent = new Intent(MainActivity.this, CoatingActivity.class);
                    startActivity(intent);
                    break;

                case R.id.ivSetIP :
                    intent = new Intent(MainActivity.this, IPsettingActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

//
//    class mThread extends Thread{
//        @Override
//        public void run() {
//            try {
//                URL url = new URL("http://192.168.55.150/capture");
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
//                    Toast.makeText(MainActivity.this, "HTTPOK", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getApplicationContext(), "에러발생", Toast.LENGTH_SHORT).show();
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

}