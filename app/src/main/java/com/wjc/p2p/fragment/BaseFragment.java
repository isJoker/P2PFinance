package com.wjc.p2p.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;
import com.wjc.p2p.ui.LoadingPage;

import butterknife.ButterKnife;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：Fragment基类
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        loadingPage = new LoadingPage(container.getContext()) {
            @Override
            public int LayoutId() {
                return getLayoutId();
            }

            //加载成功后的回调
            @Override
            protected void onSuccess(ResultState resultState, View successView) {
                //要做绑定操作。注意参数1！！
                ButterKnife.bind(BaseFragment.this,successView);
                initTitleBar();
                initData(resultState.getContent());
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };
        return loadingPage;
    }

    //在onCreateView()方法调用之后，方法可使用showLoadingPage()，所以声明在如下的方法中


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoadingPage();
    }

    private void showLoadingPage() {
        loadingPage.show();
    }

    protected abstract RequestParams getParams();//提供请求的参数

    protected abstract String getUrl();//提供请求的url

    protected abstract void initData(String content);//初始化数据

    protected abstract int getLayoutId() ;//获取子类布局的id

    protected abstract void initTitleBar();//初始化标题栏


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
