package com.bwie.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mo on 2017/9/5.
 */

public class NetWorkInfo {

    public Context context;
    public Network network;
    public void Network(Context context,Network network){
        this.context=context;

        //网络连接管理器
        ConnectivityManager  manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络可用对象
        NetworkInfo info = manager.getActiveNetworkInfo();
        //若对象不为空 则有网络
        if(info!=null){
            //wifi网络状态
            if(info.getType()==ConnectivityManager.TYPE_WIFI){
                network.netwifi();
            }else if(info.getType()==ConnectivityManager.TYPE_MOBILE){
                //移动 网络状态
                network.netmoble();
            }else{
                //没有有效网络
                network.unnet();
            }
        }else{
            //没有网络
            network.unnet();
        }

    }


    public void setNetwork(Network network) {
        this.network = network;
    }

    //定义接口
    public interface Network{
        void netwifi();
        void netmoble();
        void unnet();
    }
}
