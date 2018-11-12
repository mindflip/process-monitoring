package com.example.user.webviewtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class OverviewActivity extends AppCompatActivity {

    private WebView[] wvCam;
//    private WebView wvCam1, wvCam2, wvCam3, wvCam4;
    private SharedPreferences appData;
    private SharedPreferences.Editor editor;
    private String[] camIP = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        appData = getSharedPreferences("pref", MODE_PRIVATE);
        editor = appData.edit();

        Intent intent = getIntent();
        for(int i = 0; i < 4; i++){
            camIP[i] = intent.getExtras().getString("ip"+(i+1));
        }
        initLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wvDestroy();
    }

    private void wvDestroy(){
        for(int i = 0; i < 4; i++) {
            wvCam[i].stopLoading();
            wvCam[i].loadData("", "text/html", "utf-8");
            wvCam[i].reload();

            wvCam[i].setWebChromeClient(null);
            wvCam[i].setWebViewClient(null);

            wvCam[i].removeAllViews();
            wvCam[i].clearHistory();
            wvCam[i].destroy();
            wvCam[i] = null;
        }
    }

    private void initLayout(){
        wvCam = new WebView[4];

        for(int i = 0; i < wvCam.length; i++){
            wvCam[i] = (WebView) findViewById(R.id.wvCam + (i+1));
            wvCam[i].setWebViewClient(new WebViewClient());
            wvCam[i].getSettings().setJavaScriptEnabled(true);
            wvCam[i].getSettings().setLoadWithOverviewMode(true);
            wvCam[i].getSettings().setUseWideViewPort(true);
            wvCam[i].loadUrl("http://" + camIP[i]);
//            wvCam[i].loadUrl("http://192.168.55.22:8080/video/mjpeg");
//            wvCam[i].loadUrl("http://sillacam0.hopto.org:719" + i + "/video/mjpeg");
            wvCam[i].setOnTouchListener(onTouchListener);
        }

//        wvCam1 = (WebView) findViewById(R.id.wvCam1);
//        wvCam2 = (WebView) findViewById(R.id.wvCam2);
//        wvCam3 = (WebView) findViewById(R.id.wvCam3);
//        wvCam4 = (WebView) findViewById(R.id.wvCam4);
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN :
                    break;

                case MotionEvent.ACTION_MOVE :
                    break;

                case MotionEvent.ACTION_UP :
                    switch (v.getId()){
                        case R.id.wvCam1 :
//                            Toast.makeText(OverviewActivity.this, appData.getString("cam1",""), Toast.LENGTH_SHORT).show();
                            loadCam("cam1", "Camera 1");
//                            reconnection(0);

                            break;

                        case R.id.wvCam2 :
                            loadCam("cam2","Camera 2");
//                            reconnection(1);
                            break;

                        case R.id.wvCam3 :
                            loadCam("cam3","Camera 3");
//                            reconnection(2);
                            break;

                        case R.id.wvCam4 :
                            loadCam("cam4","Camera 4");
//                            reconnection(3);
                            break;
                    }
                    break;
            }
            return false;
        }
    };

    private void reconnection(int i){
        wvCam[i].stopLoading();
//        wvCam[i].loadData("", "text/html", "utf-8");
        wvCam[i].reload();

//        wvCam[i].loadUrl("http://" + camIP[i] + "/stream");

    }

    private void loadCam(String key, String titleInfo){
        Intent intent = new Intent(OverviewActivity.this, CamActivity.class);
        intent.putExtra("title", titleInfo);
        intent.putExtra("ip", appData.getString(key,""));
        startActivity(intent);
    }
}