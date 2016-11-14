package com.wjc.p2p.common;

/**
 * Created by ${万嘉诚} on 2016/11/11.
 * WeChat：wjc398556712
 * Function：配置网络请求相关的地址
 */

public class AppNetConfig {

    public static final String HOST = "192.168.191.1";//提供主机ip地址

    //提供web应用的地址
    public static final String BASE_URL = "http://" + HOST + ":8080/P2PInvest/";

    //联网请求更新的地址
    public static final String URL_UPDATE = BASE_URL + "update.json";

    //具体的资源地址
    public static final String PRODUCT = BASE_URL + "product";
    public static final String INDEX = BASE_URL + "index";
    public static final String LOGIN = BASE_URL + "login";
    public static final String TEST = BASE_URL + "test";

}
