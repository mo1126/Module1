package com.bwie.module;

import android.app.Application;
import android.graphics.Bitmap;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.SMSSDK;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by Mo on 2017/8/30.
 */

public class Myapp extends Application{
    private static ImageLoaderConfiguration config;
    private DisplayImageOptions options;
    @Override
    public void onCreate() {
        super.onCreate();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
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

        options = new DisplayImageOptions.Builder()// 开始构建, 显示的图片的各种格式
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .cacheInMemory(true)// 开启内存缓存
                .cacheOnDisk(true) // 开启硬盘缓存
                .displayer(new SimpleBitmapDisplayer())// 正常显示一张图片　
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型;使用.bitmapConfig(Bitmap.config.RGB_565)代替ARGB_8888;
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//这两种配置缩放都推荐
                .build();// 构建完成（参数可以不用设置全，根据需要来配置）


        config = new ImageLoaderConfiguration.Builder(this)// 开始构建 ,图片加载配置
                .threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程优先级
                .threadPoolSize(3)// 线程池内加载的数量 ;减少配置之中线程池的大小，(.threadPoolSize).推荐1-5；
                .denyCacheImageMultipleSizesInMemory()// 设置加载的图片有多样的
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 图片加载任务顺序
                .memoryCache(new WeakMemoryCache())//使用.memoryCache(new WeakMemoryCache())，不要使用.cacheInMemory();
                .memoryCacheExtraOptions(480, 800) // 即保存的每个缓存文件的最大长宽
                .memoryCacheSizePercentage(60)// 图片内存占应用的60%；
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//使用HASHCODE对UIL进行加密命名
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5 加密
                .diskCacheSize(50 * 1024 * 1024) // 缓存设置大小为50 Mb
                .denyCacheImageMultipleSizesInMemory()// 自动缩放
                .defaultDisplayImageOptions(options)// 如果需要打开缓存机制，需要自己builde一个option,可以是DisplayImageOptions.createSimple()
                .build();
                    ImageLoader.getInstance().init(config);





    }

    private void initXutils() {
        x.Ext.init(this);
    }
}
