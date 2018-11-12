package com.example.user.webviewtest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class IPsettingActivity extends AppCompatActivity {

    private TextView tvIP1, tvIP2, tvIP3, tvIP4;
    private EditText etSettingIP;
    private Button btSettingIP1, btSettingIP2, btSettingIP3, btSettingIP4;

    private SharedPreferences appData;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipsetting);

        initButton();

        appData = getSharedPreferences("pref", MODE_PRIVATE);
        editor = appData.edit();

        tvIP1.setText(appData.getString("cam1",""));
        tvIP2.setText(appData.getString("cam2",""));
        tvIP3.setText(appData.getString("cam3",""));
        tvIP4.setText(appData.getString("cam4",""));
    }

    void initButton(){
        btSettingIP1 = (Button) findViewById(R.id.btSettingIP1);
        btSettingIP2 = (Button) findViewById(R.id.btSettingIP2);
        btSettingIP3 = (Button) findViewById(R.id.btSettingIP3);
        btSettingIP4 = (Button) findViewById(R.id.btSettingIP4);
        etSettingIP = (EditText) findViewById(R.id.etSettingIP);
        tvIP1 = (TextView) findViewById(R.id.tvIP1);
        tvIP2 = (TextView) findViewById(R.id.tvIP2);
        tvIP3 = (TextView) findViewById(R.id.tvIP3);
        tvIP4 = (TextView) findViewById(R.id.tvIP4);

        btSettingIP1.setOnClickListener(onClickListener);
        btSettingIP2.setOnClickListener(onClickListener);
        btSettingIP3.setOnClickListener(onClickListener);
        btSettingIP4.setOnClickListener(onClickListener);
    }

    Button.OnClickListener onClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {

            String setIP = etSettingIP.getText().toString();
            if (setIP == null){
                Toast.makeText(getApplicationContext(), "IP를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            switch (v.getId()){
                case R.id.btSettingIP1 :
                    save("cam1", setIP);
                    tvIP1.setText(setIP);
                    break;

                case R.id.btSettingIP2 :
                    save("cam2", setIP);
                    tvIP2.setText(setIP);
                    break;

                case R.id.btSettingIP3 :
                    save("cam3", setIP);
                    tvIP3.setText(setIP);
                    break;

                case R.id.btSettingIP4 :
                    save("cam4", setIP);
                    tvIP4.setText(setIP);
                    break;
            }
        }
    };

    private void save(String key, String IP){
        editor.putString(key, IP);
        editor.commit();
        Toast.makeText(getApplicationContext(),key + " CLICKED with " + IP, Toast.LENGTH_SHORT).show();
    }


}
