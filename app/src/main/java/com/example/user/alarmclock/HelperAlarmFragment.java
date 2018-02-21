package com.example.user.alarmclock;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;


public class HelperAlarmFragment extends Fragment {

    private ClosingIface closeFragment;


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void setCallBack(ClosingIface closeFragment) {
        this.closeFragment = closeFragment;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        closeFragment.closeFragment();

    }
}
