package com.leo.test.bookshelf.view.fragments;

import androidx.fragment.app.Fragment;

import com.leo.test.bookshelf.vm.DialogProvider;
import com.leo.test.bookshelf.widget.WaitingDialog;

public class BaseFragment extends Fragment implements DialogProvider {
    public void onRefresh() {
    }

    @Override
    public void showWaitingDialog() {
        WaitingDialog.show(getContext());
    }
}
