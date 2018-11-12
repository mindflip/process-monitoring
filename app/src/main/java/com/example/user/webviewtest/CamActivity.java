package com.example.user.webviewtest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CamActivity extends AppCompatActivity {

    private WebView mWebView;
    private WebSettings mWebSettings;
    private TextView tvCamTitle, tvIP;
    private Button btCapture;
    private int count = 0;
    private String camIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        tvCamTitle = (TextView) findViewById(R.id.tvCamTitle);
        tvIP = (TextView) findViewById(R.id.tvIP);

        buttonInit();

        Intent intent = getIntent();
        String camTitle = intent.getExtras().getString("title");
        camIP = intent.getExtras().getString("ip");
        tvCamTitle.setText(camTitle);
        tvIP.setText(camIP);

        if(!camTitle.equals(getString(R.string.tvCompTitle)))
            btCapture.setVisibility(View.INVISIBLE);

        mWebView = (WebView)findViewById(R.id.wvCam);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);
        mWebView.setOnTouchListener(onTouchListener);

        mWebView.loadUrl("http://" + camIP);
////        mWebView.loadUrl("http://192.168.55.22:8080/video/mjpeg");
//        mWebView.loadUrl("http://sillacam0.hopto.org:7194/video/mjpeg");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mWebView.stopLoading();
        mWebView.loadData("", "text/html", "utf-8");
        mWebView.reload();

        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);

        mWebView.removeAllViews();
        mWebView.clearHistory();
        mWebView.destroy();
        mWebView = null;

    }

    private void buttonInit(){
        btCapture = (Button) findViewById(R.id.btCapture);
        btCapture.setOnClickListener(onClickListener);
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN :
                    break;
                case MotionEvent.ACTION_MOVE :
                    break;
                case MotionEvent.ACTION_UP :
                    reconnection();
                    break;
            }
            return false;
        }
    };

    private void reconnection(){
        mWebView.stopLoading();
        mWebView.reload();
    }

    Button.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (count < 1){
                count++;
                Toast.makeText(CamActivity.this, "비교를 위해 캡처를 한 번 더 해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                mThread thread = new mThread();
                thread.start();
                Toast.makeText(CamActivity.this, "불일치도 PUSH 전송", Toast.LENGTH_SHORT).show();
            }
        }
    };

    class mThread extends Thread{
        @Override
        public void run() {
            try {
//                URL url = new URL("http://192.168.55.150/capture");
                URL url = new URL("http://115.93.81.76:7186/compare");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
//                    Toast.makeText(getApplicationContext(), "HTTPOK", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getApplicationContext(), "에러발생", Toast.LENGTH_SHORT).show();
//                }
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    Log.i("CamActivityHTTP", "HTTPOK");
                }else{
                    Log.i("CamActivityHTTP", "ERROR");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}