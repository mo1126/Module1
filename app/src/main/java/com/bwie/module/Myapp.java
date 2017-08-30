package com.bwie.module;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * Created by Mo on 2017/8/30.
 */

public class Myapp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initXutils();
        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }

    private void initXutils() {
        x.Ext.init(this);
    }
}
