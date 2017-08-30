package com.bwie.module;


import android.os.Bundle;


import com.google.gson.Gson;
import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import Bean.News;
import fragment.Fragment1;
import fragment.Fragment2;
import myadapter.Myadapter;
import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity implements XListView.IXListViewListener{

    @ViewInject(R.id.lv) XListView lv;
    private String url="http://v.juhe.cn/toutiao/index";
    private List<News.ResultBean.DataBean> data;
    private Myadapter myadapter;

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setslidingmenu();
        initData();
        getData();
    }

    private void setslidingmenu() {
        setBehindContentView(R.layout.left);
        getSupportFragmentManager().beginTransaction().replace(R.id.left,new Fragment1()).commit();

        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindOffsetRes(R.dimen.offset);


        menu.setSecondaryMenu(R.layout.right);
        getSupportFragmentManager().beginTransaction().replace(R.id.right,new Fragment2()).commit();

    }

    private void initData() {
        data=new ArrayList<>();
        lv.setXListViewListener(this);
        lv.setPullLoadEnable(true);
    }

    private void getData() {
        RequestParams entity=new RequestParams(url);
        entity.addQueryStringParameter("key","22a108244dbb8d1f49967cd74a0c144d");
        entity.addQueryStringParameter("type","yule");

        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                gsonjx(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    private void gsonjx(String result) {
        Gson gson=new Gson();
        News news = gson.fromJson(result, News.class);
        data = news.result.data;
        setdata();
    }

    private void setdata() {
        if(myadapter==null){
            myadapter = new Myadapter(this,data);
            lv.setAdapter(myadapter);
        }else{
            myadapter.notifyDataSetChanged();
        }

        lv.stopLoadMore();
        lv.stopRefresh();
    }

    @Override
    public void onRefresh() {
        
        getData();
    }

    @Override
    public void onLoadMore() {
        getData();
        myadapter.addlist(data);
        myadapter.notifyDataSetChanged();
    }
}
