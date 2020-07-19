package com.maxsauer.splitscreen2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String APP1 = "org.prowl.torque";
    private static final String APP2 = "com.racechrono.app";

    // Apps available on Pixel emulator
    //private static final String APP1 = "com.google.android.apps.messaging";
    //private static final String APP2 = "com.android.chrome";

    private Intent app1Intent;
    private Intent app2Intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("splitscreen2", "onCreate");
        //setContentView(R.layout.activity_main);
        getIntent().addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        app1Intent = getPackageManager().getLaunchIntentForPackage(APP1);
        app2Intent = getPackageManager().getLaunchIntentForPackage(APP2);

        if (app1Intent != null) {
            app1Intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            showAlert(APP1);
        }

        if (app2Intent != null) {
            app2Intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            showAlert(APP2);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.i("splitscreen2", "onPostCreate");
        if (!isInMultiWindowMode()) {
            Log.i("splitscreen2", "starting SplitScreenService");
            startService(new Intent(this, SplitScreenService.class));
        }
        if (app1Intent != null && app2Intent != null) {
            finishAndRemoveTask();
            startActivities(new Intent[]{app1Intent, app2Intent});

        }


    }

    @Override
    protected void onResume() {
        Log.i("splitscreen2", "onResume");
        super.onResume();
    }

    private void showAlert(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (!isFinishing()){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("App not found")
                            .setMessage(s + " not installed.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
    }
}