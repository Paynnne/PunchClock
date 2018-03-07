package com.chuoengda.punchclock.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.chuoengda.punchclock.receiver.AlarmReceiver;
import com.chuoengda.punchclock.utils.TimeUtils;

import java.util.Date;

/**
 * @Author：chupengda
 * @Date: 2017/11/13 09:36
 * @Annotation: Service
 */

public class PunchClockService extends Service {

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 每次通过startService()方法启动Service时都会被回调。
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long myTime = Long.valueOf(TimeUtils.getEquationOfTime(intent.getStringExtra("TIME")));
//        long myTime = 20 * 1000;
        new Thread(new Runnable() {

            @Override
            public void run() {
                Log.e("RunningService", "executed at " + new Date().toString());
            }
        }).start();
        //后台执行定时任务一般有两种实现方式，一种是Timer类，一种是如下我们使用的Alarm机制，
        //但Alarm有独有的唤醒CPU的功能，使得在CPU休眠的时候该服务也能正常运行
        //AlarmManager通过调用上下文的getSystemService（）方法来获取对象实例，并传入参数ALARM_SERVICE
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intent = new Intent(this, AlarmReceiver.class);
        //PendingIntent类似Intent，一般称延时意图，在一定条件触发后（比如点击或者固定时间后）即可唤起执行
        //调用getBroadcast来执行广播的PendingIntent，也可以调用getActivity或者getService来获取相应的组件
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        //通过SystemClock.elapsedRealtime()来获取系统启动到此刻的时间（systemclock.currentTimeMills方法可获取1970.1.1至今的时间
        long triggerAtMillis = SystemClock.elapsedRealtime() + myTime;
        //调用manager的set方法，第一个参数ELAPSED_REALTIME_WAKEUP表示让定时任务触发从系统启动开始，切会唤醒CPU，
        // 第二个参数表示定时任务触发的时间，第三个参数表示触发时执行的意图
        //自Api19开始，set方法设置的闹钟可能会发生deferred(延迟)，因为OS基于尽可能减少设备唤醒和电池损耗考虑，所以OS不推荐设置过多的闹钟。如果实在需要闹钟准时响应，可以采用setExact方法。
        if (Build.VERSION.SDK_INT >= 19) {
            manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pIntent);
        } else {
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtMillis, pIntent);
        }


        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 绑定服务时才会调用
     * 必须要实现的方法
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 服务销毁时的回调
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
