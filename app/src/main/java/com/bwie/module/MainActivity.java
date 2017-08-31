package com.bwie.module;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;


import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;
import Bean.News;
import fragment.Fragment1;
import fragment.Fragment2;
import fragment.MyFragment;
import view.ZdyView;
import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity implements XListView.IXListViewListener{


    private String url="http://v.juhe.cn/toutiao/index";
    private List<News.ResultBean.DataBean> data;
    @ViewInject(R.id.zdy) ZdyView zdy;
    private List<String> beans;
    private List<Fragment> fragmentList;
    private SlidingMenu menu;

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setslidingmenu();
        fragmentList = new ArrayList<>();
        beans = new ArrayList<>();

        beans.add("娱乐");
        beans.add("头条");
        beans.add("军事");
        beans.add("游戏");
        beans.add("国际");
        beans.add("娱乐");
        beans.add("娱乐");
        beans.add("娱乐");
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragment());
        zdy.diaplay(beans, fragmentList);
    }

    private void setslidingmenu() {
        setBehindContentView(R.layout.left);
        getSupportFragmentManager().beginTransaction().replace(R.id.left,new Fragment1()).commit();

        menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindOffsetRes(R.dimen.offset);

        menu.setSecondaryMenu(R.layout.right);
        getSupportFragmentManager().beginTransaction().replace(R.id.right,new Fragment2()).commit();

    }

    public void me(View view){
        menu.showMenu();
    }
    public void more(View view){
        menu.showSecondaryMenu();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
