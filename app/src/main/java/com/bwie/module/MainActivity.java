package com.bwie.module;


import android.content.Intent;
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
import fragment.MyFragmentCJ;
import fragment.MyFragmentGJ;
import fragment.MyFragmentGN;
import fragment.MyFragmentJS;
import fragment.MyFragmentKJ;
import fragment.MyFragmentSH;
import fragment.MyFragmentSS;
import fragment.MyFragmentTY;
import fragment.MyFragmentYL;
import view.ZdyView;
import view.xlistview.XListView;

@ContentView(R.layout.activity_main)
public class MainActivity extends SlidingFragmentActivity {


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

        beans.add("头条");
        beans.add("社会");
        beans.add("国内");
        beans.add("国际");
        beans.add("娱乐");
        beans.add("体育");
        beans.add("军事");
        beans.add("科技");
        beans.add("财经");
        beans.add("时尚");
        fragmentList.add(new MyFragment());
        fragmentList.add(new MyFragmentSH());
        fragmentList.add(new MyFragmentGN());
        fragmentList.add(new MyFragmentGJ());
        fragmentList.add(new MyFragmentYL());
        fragmentList.add(new MyFragmentTY());
        fragmentList.add(new MyFragmentJS());
        fragmentList.add(new MyFragmentKJ());
        fragmentList.add(new MyFragmentCJ());
        fragmentList.add(new MyFragmentSS());
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


    //左侧滑菜单的更多登陆方式点击事件
    public void morelogin(View view){
        Intent intent=new Intent(this, MoreLoginActivity.class);
        startActivity(intent);
    }
}
