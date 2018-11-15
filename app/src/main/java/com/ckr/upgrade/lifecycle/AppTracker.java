package com.ckr.upgrade.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.ckr.upgrade.DownLoadService;
import com.ckr.upgrade.MainActivity;
import com.ckr.upgrade.dialog.UpgradeDialogFragment;

import static com.ckr.upgrade.util.UpgradeLog.Logd;

/**
 * Created by ckr on 2018/11/10.
 */

public class AppTracker implements Application.ActivityLifecycleCallbacks, UpgradeDialogFragment.OnDialogClickListener {
    private static final String TAG = "AppTracker";
    private boolean canShow;
    private Activity activity;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Logd(TAG, "onActivityCreated: " + activity);
    }

    public void showDialog(boolean isShow) {
        canShow = isShow;
        if (!canShow) {
            return;
        }
        if (activity == null) {
            return;
        }
        canShow = false;
        UpgradeDialogFragment dialogFragment = new UpgradeDialogFragment.Builder()
                .setPositiveText("立即更新")
                .setNegativeText("以后再说")
                .setOnDialogClickListener(null)
                .build();
        dialogFragment.show(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof MainActivity) {
            this.activity = activity;
            showDialog(canShow);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onPositiveClick() {
        if (activity != null) {
            Intent intent = new Intent(activity, DownLoadService.class);
            activity.startService(intent);
        }
    }
}
