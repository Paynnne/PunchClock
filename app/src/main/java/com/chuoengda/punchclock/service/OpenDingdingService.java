package com.chuoengda.punchclock.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * @Author：chupengda
 * @Date: 2017/11/23 11:12
 * @Annotation: 钉钉打卡 服务
 */

public class OpenDingdingService extends AccessibilityService {

    private boolean isFinish = false;

    private int index = 1;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        Log.e("OpenDingdingService", "事件---->" + event.getEventType());
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        OpenHome(event.getEventType(), nodeInfo);
        OpenQianDao(event.getEventType(), nodeInfo);

    }

    @Override
    public void onInterrupt() {

    }

    /**
     * 进入工作(懿加乐)
     *
     * @param type
     * @param nodeInfo
     */
    private void OpenHome(int type, AccessibilityNodeInfo nodeInfo) {
        if (type == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            //判断当前是否是钉钉主页
            List<AccessibilityNodeInfo> homeList = nodeInfo.findAccessibilityNodeInfosByText("懿加乐");
            if (!homeList.isEmpty()) {
                //点击
                boolean isHome = click("懿加乐");
                index = 2;
                System.out.println("---->" + isHome);
                Log.e("OpenDingdingService", "事件----> 进入公司");
            }
        }

    }

    /**
     * 进入考勤打卡
     *
     * @param type
     * @param nodeInfo
     */
    private void OpenQianDao(int type, AccessibilityNodeInfo nodeInfo) {
        if (type == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            List<AccessibilityNodeInfo> qianList = nodeInfo.findAccessibilityNodeInfosByText("懿加乐");
            if (!qianList.isEmpty()) {
                boolean ret = click("考勤打卡");
                Log.e("OpenDingdingService", "事件----> 考勤打卡");
            }


        }

    }


    //通过文字点击
    private boolean click(String viewText) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            Log.w("OpenDingdingService", "点击失败，rootWindow为空");
            return false;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(viewText);
        if (list.isEmpty()) {
            //没有该文字的控件
            Log.w("OpenDingdingService", "点击失败，" + viewText + "控件列表为空");
            return false;
        } else {
            //有该控件
            //找到可点击的父控件
            AccessibilityNodeInfo view = list.get(0);
            return onclick(view);  //遍历点击
        }

    }

    private boolean onclick(AccessibilityNodeInfo view) {
        if (view.isClickable()) {
            view.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            Log.w("OpenDingdingService", "点击成功");
            return true;
        } else {

            AccessibilityNodeInfo parent = view.getParent();
            if (parent == null) {
                return false;
            }
            onclick(parent);
        }
        return false;
    }
}
