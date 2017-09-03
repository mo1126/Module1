package com.bwie.module;

import android.app.Application;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.smssdk.SMSSDK;

/**
 * Created by Mo on 2017/8/30.
 */

public class Myapp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        {
            PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
            PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
            PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        }

        initXutils();
        initImageLoader();
        // 通过代码注册你的AppKey和AppSecret
        MobSDK.init(this,"209dc6b8f6c6b", "64054277bd0b951b17911a6bba2c038c");

    }

    private void initImageLoader() {
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }

    private void initXutils() {
        x.Ext.init(this);
    }
}
