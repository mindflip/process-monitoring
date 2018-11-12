package com.example.user.webviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CoatingActivity extends AppCompatActivity {

    private EditText etVoltage, etNaOH, etNa2SiO3, etNH4F, etTemp, etTime;
    private Button bt5000, bt6000, btADC12;
    private TextView tvResult, tvResultDp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coating);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        layoutInit();
    }

    private void layoutInit(){
        etVoltage = (EditText) findViewById(R.id.etVoltage);
        etNaOH = (EditText) findViewById(R.id.etNaOH);
        etNa2SiO3 = (EditText) findViewById(R.id.etNa2SiO3);
        etNH4F = (EditText) findViewById(R.id.etNH4F);
        etTemp = (EditText) findViewById(R.id.etTemp);
        etTime = (EditText) findViewById(R.id.etTime);

        bt5000 = (Button) findViewById(R.id.bt5000);
        bt6000 = (Button) findViewById(R.id.bt6000);
        btADC12 = (Button) findViewById(R.id.btADC12);

        bt5000.setOnClickListener(onClickListener);
        bt6000.setOnClickListener(onClickListener);
        btADC12.setOnClickListener(onClickListener);

        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResultDp = (TextView) findViewById(R.id.tvResultDp);
    }

    Button.OnClickListener onClickListener= new Button.OnClickListener(){

        @Override
        public void onClick(View v) {

            if (etVoltage.getText().toString().replace(" ","").equals("") || etNaOH.getText().toString().replace(" ","").equals("")
                    || etNa2SiO3.getText().toString().replace(" ","").equals("") || etNH4F.getText().toString().replace(" ","").equals("")
                    || etTemp.getText().toString().replace(" ","").equals("") || etTime.getText().toString().replace(" ","").equals("")){
                Toast.makeText(CoatingActivity.this, "빈칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show();

            } else {
                double volt = Double.parseDouble(etVoltage.getText().toString());
                double Na = Double.parseDouble(etNaOH.getText().toString());
                double Na2 = Double.parseDouble(etNa2SiO3.getText().toString());
                double NH = Double.parseDouble(etNH4F.getText().toString());
                double temp = Double.parseDouble(etTemp.getText().toString());
                double time = Double.parseDouble(etTime.getText().toString());

                switch (v.getId()) {
                    case R.id.bt5000:
                        tvResult.setText(Double.toString(coatingCal(1, volt, Na, Na2, NH, temp, time)));
                        tvResultDp.setText("PEO AL 5000 결과");
                        break;

                    case R.id.bt6000:
                        tvResult.setText(Double.toString(coatingCal(2, volt, Na, Na2, NH, temp, time)));
                        tvResultDp.setText("PEO AL 6000 결과");
                        break;

                    case R.id.btADC12:
                        tvResult.setText(Double.toString(coatingCal(3, volt, Na, Na2, NH, temp, time)));
                        tvResultDp.setText("PEO AL ADC 12 결과");
                        break;
                }
            }
        }
    };

    private double coatingCal(int i, double voltage, double NaOH, double Na2, double NH, double temp, double time){
        double result = 0;

        switch (i){
            case 1 :
                result = 6.87 + 0.55*voltage + 0.01*NaOH + 0.06*Na2 + 0.23*NH + (-0.88*temp) + 0.41*time;
                break;

            case 2 :
                result = 4.11 + 0.54*voltage + 0.02*NaOH + 0.09*Na2 + 0.21*NH + (-0.82*temp) + 0.38*time;
                break;

            case 3 :
                result = 4.02 + 0.54*voltage + 0.04*NaOH + (-0.02*Na2) + 0.18*NH + (-0.81*temp) + 0.38*time;
                break;
        }

        return result;
    }
}
