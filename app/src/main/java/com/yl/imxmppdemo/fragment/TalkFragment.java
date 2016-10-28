package com.yl.imxmppdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yl.imxmppdemo.R;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class TalkFragment extends BaseFragment {
    @Override
    protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(),R.layout.fragment_talk,null);
        return view;
    }
}
