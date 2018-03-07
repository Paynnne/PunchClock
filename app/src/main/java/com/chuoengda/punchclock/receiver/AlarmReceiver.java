package com.chuoengda.punchclock.receiver;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.provider.Settings;

import com.chuoengda.punchclock.act.MainActivity;
import com.chuoengda.punchclock.service.OpenDingdingService;
import com.chuoengda.punchclock.utils.AppUtils;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;

/**
 * @Author：chupengda
 * @Date: 2017/11/13 10:48
 * @Annotation: 广播
 */

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        //屏幕唤醒
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_DIM_WAKE_LOCK, "StartupReceiver");//最后的参数是LogCat里用的Tag
        wl.acquire();

        //屏幕解锁
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("StartupReceiver");//参数是LogCat里用的Tag
        kl.disableKeyguard();


        //执行任务
        AppUtils.OpenApplication(context);
        //启动AccessibilityService
        Intent openDingIntent = new Intent(context, OpenDingdingService.class);
        context.startService(openDingIntent);

    }
}
