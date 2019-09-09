package com.netscape.utrain.activities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.netscape.utrain.utils.Constants;

public class AskPermission extends Activity {
    private Context mContext;
    private Activity activity;

    public AskPermission(Context mContext, Activity activity) {
        this.mContext = mContext;
        this.activity = activity;

    }

    public boolean askForReadWritePermission() {
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            isPermissionGranted(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Constants.WRITE_PERMISSION);
            return false;
        }
    }

    public boolean isPermissionGranted(Activity activity, String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(new String[]{permission},
                        requestCode);
            }
            return false;
        }
        return true;
    }

    public boolean askPermission(String permissionType, int request) {
        if (ContextCompat.checkSelfPermission(mContext, permissionType)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            isPermissionGranted(activity, permissionType, request);
            return false;
        }
    }

    public boolean isPermissionGiven(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
}
