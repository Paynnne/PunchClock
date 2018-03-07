package com.chuoengda.punchclock.act;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chuoengda.punchclock.R;
import com.chuoengda.punchclock.service.OpenDingdingService;
import com.chuoengda.punchclock.service.PunchClockService;
import com.chuoengda.punchclock.utils.AppUtils;
import com.chuoengda.punchclock.utils.TimeUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_main_btnstartService)
    Button btnStartService;
    @BindView(R.id.activity_main_ethours)
    EditText etHours;
    @BindView(R.id.activity_main_etminutes)
    EditText etMinutes;

    private String hint;

    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainActivity = this;
        hint = "5" + AppUtils.randomTime();
        etMinutes.setHint(hint);

        if (AppUtils.isServiceRunning(this, "com.chuoengda.punchclock.service.PunchClockService")) {
            btnStartService.setText("服务已启动");
            btnStartService.setClickable(false);
        } else {
            btnStartService.setText("启动服务");
            btnStartService.setClickable(true);
        }

    }

    @OnClick({R.id.activity_main_btnstartService})
    public void onClick(View view) {
        //执行任务
        if (view.getId() == R.id.activity_main_btnstartService) {
            Intent intent = new Intent(this, PunchClockService.class);
            if (etMinutes.getText() != null && !"".equals(etMinutes.getText().toString())) {
                intent.putExtra("TIME", "08:" + etMinutes.getText().toString());

                //
                Log.e("aaaaaaaaaaaa", TimeUtils.getEquationOfTime("08:" + etMinutes.getText().toString()));
                Toast.makeText(MainActivity.this, "将于" + " 08:" + etMinutes.getText().toString() + " 开启服务", Toast.LENGTH_SHORT).show();
            } else {
                hint = "5" + AppUtils.randomTime();
                etMinutes.setHint(hint);
                intent.putExtra("TIME", "08:" + hint);

                //
                Toast.makeText(MainActivity.this, "将于" + " 08:" + hint + " 开启服务", Toast.LENGTH_SHORT).show();
                Log.e("aaaaaaaaaaaa", TimeUtils.getEquationOfTime("08:" + hint));
            }
            startService(intent);
            btnStartService.setText("服务已启动 ");
            btnStartService.setClickable(false);
        }
    }

}
