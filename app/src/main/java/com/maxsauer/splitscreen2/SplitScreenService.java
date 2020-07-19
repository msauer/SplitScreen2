package com.maxsauer.splitscreen2;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class SplitScreenService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean success = performGlobalAction(GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
        Log.i("splitscreen2", "Global" + success);
        return super.onStartCommand(intent, flags, startId);
    }
}
