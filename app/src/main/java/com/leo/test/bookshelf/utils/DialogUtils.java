package com.leo.test.bookshelf.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.leo.test.bookshelf.R;

public class DialogUtils {
    public static void showConfirmDialog(Context context, int message, DialogInterface.OnClickListener positive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        builder.setMessage(message)
                .setPositiveButton(R.string.text_ok, positive)
                .setNegativeButton(R.string.text_cancel, (dialog, which) -> {
                })
                .setCancelable(true)
                .show();
    }

    public static void showToast(Context context, int message) {
        Toast toast = Toast.makeText (context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
