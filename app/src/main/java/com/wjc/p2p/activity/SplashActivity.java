package com.wjc.p2p.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wjc.p2p.MainActivity;
import com.wjc.p2p.R;
import com.wjc.p2p.bean.UpdateInfo;
import com.wjc.p2p.uitls.LogUtil;
import com.wjc.p2p.uitls.NetUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：欢迎页
 */

public class SplashActivity extends Activity {

    @Bind(R.id.iv_welcome_icon)
    ImageView ivWelcomeIcon;
    @Bind(R.id.tv_welcome_version)
    TextView tvWelcomeVersion;
    @Bind(R.id.rl_welcome)
    RelativeLayout rlWelcome;

    private String version;
    private UpdateInfo info; //最新版本信息对象

    protected static final int WHAT_REQUEST_UPDATE_SUCCESS = 1;//得到更新信息成功
    protected static final int WHAT_REQUEST_UPDATE_ERROR = 2;//得到更新信息失败
    protected static final int WHAT_DOWNLOAD_SUCCESS = 3;//下载apk成功
    protected static final int WHAT_DOWNLOAD_ERROR = 4;//下载apk失败
    private static final int WHAT_START_MAIN = 5;//进入主界面

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case WHAT_REQUEST_UPDATE_SUCCESS:
                    if(version.equals(info.getVersion())) {//当前就是最新版本,直接进入主界面
                        Toast.makeText(SplashActivity.this, "当前是最新版本", Toast.LENGTH_SHORT).show();
                        toMain();
                    } else {//有更新的版本
                        //显示下载的dialog
                        showDownloadDialog();
                    }
                    break;
                case WHAT_REQUEST_UPDATE_ERROR://得到更新信息失败, 提示并进入主界面
                    Toast.makeText(getApplicationContext(), "获取最新版本信息失败",Toast.LENGTH_SHORT).show();
                    toMain();
                    break;
                case WHAT_DOWNLOAD_SUCCESS:
                    //安装apk
                    installApk();
                    break;
                case WHAT_DOWNLOAD_ERROR://得到更新信息失败, 提示并进入主界面
                    Toast.makeText(getApplicationContext(), "下载apk失败", Toast.LENGTH_SHORT).show();
                    toMain();
                    break;
                case WHAT_START_MAIN:
                    finish();
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        //启动动画
        startAnimation();

        //获取当前版本的信息
        version = getVersion();
        tvWelcomeVersion.setText(version);


        //版本更新检查
        checkVersion();
    }

    private void startAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);//0:完全透明 1：完全不透明
        alphaAnimation.setDuration(3000);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());//设置动画的变化率
        //方法一：设置动画的监听   方法二：通过Handler发送延迟3秒后进入MainActivity
//        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                finish();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        rlWelcome.startAnimation(alphaAnimation);

    }

    /**
     * 当前版本号
     * @return
     */
    private String getVersion() {
        String version = "未知版本";
        PackageManager manager = getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //e.printStackTrace(); //如果找不到对应的应用包信息, 就返回"未知版本"
        }

        return version;
    }

    /**
     * 版本更新检查
     */
    private void checkVersion() {
        //得到当前时间
        startTime = System.currentTimeMillis();

        //检查手机是否联网
        boolean connected = isConnected();
        if (!connected) {
            Toast.makeText(this, "您已进入没有网络的异次元世界", Toast.LENGTH_SHORT).show();
            //进入主界面
            toMain();
        } else {//联上了
            //Toast.makeText(this, "连接上了", 0).show();
            //启动分线程请求服务器得到最新版本的信息并封装为UpdateInfo对象
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //请求服务器得到最新版本的信息并封装为UpdateInfo对象，
                        // 可以用AsyncHttpclient进行联网请求，我们这里用java原生的HttpURLConnection
                        info = NetUtils.getUpdateInfo();
                        handler.sendEmptyMessage(WHAT_REQUEST_UPDATE_SUCCESS);
                    } catch (Exception e) {//请求失败
                        //e.printStackTrace();
                        handler.sendEmptyMessage(WHAT_REQUEST_UPDATE_ERROR);
                    }
                }
            }).start();

        }
    }

    /**
     * 判断手机是否联网
     * ConnectivityManager
     *
     * @return
     */
    private boolean isConnected() {
        boolean connected = false;

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            connected = networkInfo.isConnected();
        }

        return connected;
    }

    /**
     * 显示下载的dialog
     */
    private void showDownloadDialog() {
        new AlertDialog.Builder(this)
                .setTitle("下载最新版本")
                .setMessage(info.getDesc())
                .setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //启动分线程下载apk
                        downApk();
                    }
                })
                .setNegativeButton("暂不下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toMain();
                    }
                })
                .show();
    }

    private ProgressDialog pd;
    private File apkFile;
    /**
     * 下载最新的APK
     * ProgressDailog
     * apkFile
     */
    private void downApk() {
        Toast.makeText(this, "下载最新的APK", Toast.LENGTH_SHORT).show();

        //准备pd
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(false);//设置点击别处不会取消进度条显示
        pd.show();
        //准备apkFile   // /sdcard/udpate.apk
        File sdFile = Environment.getExternalStorageDirectory();
        apkFile = new File(sdFile, "update.apk");

        //启动分线程下载Apk
        new Thread(){
            public void run() {
                //下载
                try {
                    NetUtils.downloadAPK(getApplicationContext(), pd, apkFile, info.getApkUrl());
                    //下载成功
                    handler.sendEmptyMessage(WHAT_DOWNLOAD_SUCCESS);
                } catch (Exception e) {//下载失败
                    e.printStackTrace();
                    LogUtil.e("下载失败=====" + e.getMessage());
                    handler.sendEmptyMessage(WHAT_DOWNLOAD_ERROR);
                } finally {
                    //移除dialog
                    pd.dismiss();
                }
            }
        }.start();

    }



    private long startTime;//开始的时间
    /**
     * 进入主页面
     */
    private void toMain() {
        long currentTime = System.currentTimeMillis();
        //得到需要延迟的时间
        int delayTime = (int) (3000 - (currentTime - startTime));

        if (delayTime < 0) {
            delayTime = 0;
        }
        handler.sendEmptyMessageDelayed(WHAT_START_MAIN, delayTime);//保证动画结束后才进入主界面
    }

    /**
     * 安装apk
     */
    private void installApk() {
        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
        intent.setData(Uri.parse("file:" + apkFile.getAbsolutePath()));
        startActivity(intent);
    }
}
