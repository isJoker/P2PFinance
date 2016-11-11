package com.wjc.p2p.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjc.p2p.R;

import butterknife.Bind;
import butterknife.ButterKnife;

;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：
 */

public class HomeFragment extends Fragment {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(),R.layout.fragment_home,null);
        ButterKnife.bind(this, view);

        initTitleBar();

        return view;
    }

    protected void initTitleBar() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.GONE);
        tvTopTitle.setText("首页");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
