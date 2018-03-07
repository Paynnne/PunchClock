# PunchClock
钉钉自动打卡

#心路历程
* 写着玩玩
* 设想1：定时任务 打开钉钉 通过AccessibilityNodeInfo.findAccessibilityNodeInfosByText("")检索按钮的Text找到按钮
        通过AccessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)实现点击
  
* 由于钉钉考勤打卡按钮可能是image,不能通过所以通过设想1，改变思路使用钉钉自带的极速打卡
* 设想二：首先解锁屏幕唤醒屏幕，然后通过设想1的方法进入考勤打卡界面，自动打卡
  ```
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
    ```

成功！！ 
* 定时代码有点水 有待完善

# emphasis：自律比什么都重要！！
