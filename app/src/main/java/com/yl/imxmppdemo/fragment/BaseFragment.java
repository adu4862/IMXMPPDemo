package com.yl.imxmppdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/10/27 0027.
 */

public abstract class BaseFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = createView(inflater, container, savedInstanceState);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent!=null) {
                parent.removeView(view);
            }
      }
        return view;


    }
    protected abstract View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
}
