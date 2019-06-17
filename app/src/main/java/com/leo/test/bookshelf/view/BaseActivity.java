package com.leo.test.bookshelf.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.databinding.BindingAdapter;

import com.leo.test.bookshelf.vm.DialogProvider;
import com.leo.test.bookshelf.widget.WaitingDialog;

import timber.log.Timber;

/**
 * Created by leo on 2016. 7. 12..
 *
 * @author leo
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements DialogProvider {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d(">> onCreate()");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d(">> onDestroy()");
    }

    public void onHomeAsUpClicked(View view) {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (upIntent == null) {
            finish();
            return;
        }

        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
        } else {
            NavUtils.navigateUpTo(this, upIntent);
        }
    }

    protected void showToast(String message) {
        Toast toast = Toast.makeText (this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showWaitingDialog() {
        WaitingDialog.show(this);
    }
}
