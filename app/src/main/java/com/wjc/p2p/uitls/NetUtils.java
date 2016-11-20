package com.wjc.p2p.uitls;

import android.app.ProgressDialog;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wjc.p2p.bean.UpdateInfo;
import com.wjc.p2p.common.AppNetConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：请求服务器的工具类
 */

public class NetUtils {

    /**
     * 得到最新版本的信息对象
     * @return
     * @throws Exception
     */
    public static UpdateInfo getUpdateInfo() throws Exception {

        String updateUrl =  AppNetConfig.URL_UPDATE;

        //1. 请求服务器得到对应的流数据   inputStream
        InputStream is = requestServer(updateUrl);

        //2. 解析json流,并封装成udpateInfo对象
        UpdateInfo info = parseJson(is);

        return info;
    }

    /**
     * 解析json数据流返回对应的数据信息对象(使用原生的API)
     * @param is
     * @return
     * @throws Exception
     */
    private static UpdateInfo parseJson(InputStream is) throws Exception {
        String jsonString = readToString(is);

        UpdateInfo updateInfo = JSON.parseObject(jsonString,UpdateInfo.class);

        return updateInfo;
    }

    private static String readToString(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while((len=is.read(buffer))>0) {
            baos.write(buffer, 0, len);
        }
        baos.close();
        is.close();
        return baos.toString();
    }


    /**
     * 请求服务器得到数据流
     * @param path
     * @return
     * @throws Exception
     */
    private static InputStream requestServer(String path) throws Exception {

        //模拟网速慢
        Thread.sleep(1000);

        InputStream is = null;
        // 1). 得到HttpUrlConnection对象
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 2). 设置
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestMethod("GET");
        // 3). 连接
        conn.connect();
        // 4). 请求并获取服务器端返回的数据流
        int responseCode = conn.getResponseCode();
        if(responseCode==200) {
            is = conn.getInputStream();
        }

        return is;
    }



    /**
     * 下载apk文件, 并同步显示进度
     * @param applicationContext
     * @param pd
     * @param apkFile
     * @param apkUrl
     * @throws Exception
     */
    public static void downloadAPK(Context applicationContext,
                                   ProgressDialog pd, File apkFile, String apkUrl) throws Exception {

        InputStream is = null;
        // 1). 得到HttpUrlConnection对象
        URL url = new URL(apkUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 2). 设置
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestMethod("GET");
        // 3). 连接
        conn.connect();
        // 4). 请求并获取服务器端返回的数据流
        int responseCode = conn.getResponseCode();
        if(responseCode==200) {
            //设置最大进度
            pd.setMax(conn.getContentLength());
            is = conn.getInputStream();
            //得到apk文件的输出流
            FileOutputStream fos = new FileOutputStream(apkFile);
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len=is.read(buffer))>0) {
                //写数据
                fos.write(buffer, 0, len);
                //更新进度
                pd.incrementProgressBy(len);
                //模拟网速慢
                Thread.sleep(2);
            }
            fos.close();
            is.close();
        }
        //5). 关闭流, 连接
        conn.disconnect();
    }
}
