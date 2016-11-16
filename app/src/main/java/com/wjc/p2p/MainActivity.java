package com.wjc.p2p;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.wjc.p2p.common.BaseActivity;
import com.wjc.p2p.fragment.HomeFragment;
import com.wjc.p2p.fragment.InvestFragment;
import com.wjc.p2p.fragment.MeFragment;
import com.wjc.p2p.fragment.MoreFragment;
import com.wjc.p2p.uitls.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Bind(R.id.fl_activity)
    FrameLayout flActivity;
    @Bind(R.id.btn_home)
    RadioButton btnHome;
    @Bind(R.id.btn_invest)
    RadioButton btnInvest;
    @Bind(R.id.btn_me)
    RadioButton btnMe;
    @Bind(R.id.btn_more)
    RadioButton btnMore;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;


    @Override
    protected void initTitle() {

    }

    @Override
    public void initData() {
        //默认初始化：显示首页数据
        selectFragment(0);
        btnHome.setChecked(true);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @OnClick({R.id.btn_home, R.id.btn_invest, R.id.btn_me, R.id.btn_more})
    public void ChangeTab(View view) {
        switch (view.getId()) {
            case R.id.btn_home://首页
                selectFragment(0);
                break;
            case R.id.btn_invest://投资
                selectFragment(1);
                break;
            case R.id.btn_me:
                selectFragment(2);//我的资产
                break;
            case R.id.btn_more:
                selectFragment(3);//更多
                break;
        }
    }

    /**
     * 切换Fragment
     *
     * @param i
     */
    private void selectFragment(int i) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        //隐藏已经显示的Fragment
        hideFragment();

        switch (i) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();//此时不会调用onCreateView()
                    //添加到事务中
                    transaction.add(R.id.fl_activity, homeFragment);

                }
                transaction.show(homeFragment);

                break;
            case 1:
                if (investFragment == null) {
                    investFragment = new InvestFragment();//此时不会调用onCreateView()
                    //添加到事务中
                    transaction.add(R.id.fl_activity, investFragment);

                }
                transaction.show(investFragment);

                break;
            case 2:
                if (meFragment == null) {
                    meFragment = new MeFragment();//此时不会调用onCreateView()
                    //添加到事务中
                    transaction.add(R.id.fl_activity, meFragment);

                }
                transaction.show(meFragment);

                break;
            case 3:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();//此时不会调用onCreateView()
                    //添加到事务中
                    transaction.add(R.id.fl_activity, moreFragment);

                }
                transaction.show(moreFragment);

                break;
        }

        transaction.commit();
    }

    /**
     * 将显示的framgent隐藏
     */
    private void hideFragment() {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (investFragment != null) {
            transaction.hide(investFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
    }


    boolean isExit = false;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !isExit) {//如果操作的是“返回键”
            isExit = true;

            UIUtils.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
            Toast.makeText(MainActivity.this, "再点击一次退出应用", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

}
