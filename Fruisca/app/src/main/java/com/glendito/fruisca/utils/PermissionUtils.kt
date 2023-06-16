package com.glendito.fruisca.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {

    fun showReadExternalStorage(activity: Activity, onGranted: () -> Unit) {
        val writeExternalStoragePermission = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 1)
        } else {
            onGranted.invoke()
        }
    }

    fun showWriteExternalStorage(activity: Activity, onGranted: () -> Unit) {
        val writeExternalStoragePermission = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 1)
        } else {
            onGranted.invoke()
        }
    }

}