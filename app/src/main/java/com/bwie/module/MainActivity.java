package com.bwie.module;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonToken;
import com.kson.slidingmenu.SlidingMenu;
import com.kson.slidingmenu.app.SlidingFragmentActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import Bean.News;
import Bean.Pindao;
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
import myadapter.Myadapter;
import view.ZdyView;
import view.xlistview.XListView;



@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String url="http://v.juhe.cn/toutiao/index";
    private List<Pindao> list;
    @ViewInject(R.id.zdy) ZdyView zdy;
    private List<String> beans;
    private List<Fragment> fragmentList;

    public static ImageView head;
    private SlidingMenu menu;
    private ImageView pindao;
    private String resultJson;
    private SharedPreferences sp;
    private String pindaos;

    @Override
    public  void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        setslidingmenu();
        initData();
    }

    private void initView() {
        head = (ImageView) findViewById(R.id.head);
        this.pindao = (ImageView) findViewById(R.id.pindao);
        this.pindao.setOnClickListener(this);
    }



    private void initData() {

        sp = getSharedPreferences("pindao", MODE_PRIVATE);
        pindaos = sp.getString("pindao", null);
        if(pindaos!=null) {
            Gson gson = new Gson();
            list = gson.fromJson(pindaos, new TypeToken<List<Pindao>>() {
            }.getType());
        }else{
            list=new ArrayList<>();
            list.add(new Pindao("头条",true));
            list.add(new Pindao("社会",true));
            list.add(new Pindao("国内",true));
            list.add(new Pindao("国际",true));
            list.add(new Pindao("娱乐",true));
            list.add(new Pindao("体育",true));
            list.add(new Pindao("军事",true));
            list.add(new Pindao("科技",false));
            list.add(new Pindao("财经",false));
            list.add(new Pindao("时尚",false));
        }
        fragmentList=null;
        fragmentList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).isSelect){
                switch (list.get(i).name){
                    case "头条":
                        fragmentList.add(new MyFragment());
                        System.out.println("头条");
                        break;
                    case "社会":
                        fragmentList.add(new MyFragmentSH());
                        System.out.println("社会");
                        break;
                    case "国内":
                        fragmentList.add(new MyFragmentGN());
                        System.out.println("国内");
                        break;
                    case "国际":
                        fragmentList.add(new MyFragmentGJ());
                        System.out.println("国际");
                        break;
                    case "娱乐":
                        fragmentList.add(new MyFragmentYL());
                        System.out.println("娱乐");
                        break;
                    case "体育":
                        fragmentList.add(new MyFragmentTY());
                        System.out.println("体育");
                        break;
                    case "军事":
                        fragmentList.add(new MyFragmentJS());
                        System.out.println("军事");
                        break;
                    case "科技":
                        fragmentList.add(new MyFragmentKJ());
                        System.out.println("科技");
                        break;
                    case "财经":
                        fragmentList.add(new MyFragmentCJ());
                        System.out.println("财经");
                        break;
                    case "时尚":
                        fragmentList.add(new MyFragmentSS());
                        System.out.println("时尚");
                        break;
                }
            }
        }
//        beans = new ArrayList<>();
//        beans.add("头条");
//        beans.add("社会");
//        beans.add("国内");
//        beans.add("国际");
//        beans.add("娱乐");
//        beans.add("体育");
//        beans.add("军事");
//        beans.add("科技");
//        beans.add("财经");
//        beans.add("时尚");

//        fragmentList.add(new MyFragment());
//        fragmentList.add(new MyFragmentSH());
//        fragmentList.add(new MyFragmentGN());
//        fragmentList.add(new MyFragmentGJ());
//        fragmentList.add(new MyFragmentYL());
//        fragmentList.add(new MyFragmentTY());
//        fragmentList.add(new MyFragmentJS());
//        fragmentList.add(new MyFragmentKJ());
//        fragmentList.add(new MyFragmentCJ());
//        fragmentList.add(new MyFragmentSS());
        zdy.diaplay(list, fragmentList);
    }

    private void setslidingmenu() {
        menu = new SlidingMenu(this);
        menu.setMenu(R.layout.left);
        getSupportFragmentManager().beginTransaction().replace(R.id.left,new Fragment1()).commit();
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindOffsetRes(R.dimen.offset);

        menu.setSecondaryMenu(R.layout.right);
        getSupportFragmentManager().beginTransaction().replace(R.id.right,new Fragment2()).commit();
        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
    }

    public void me(View view){
        menu.showMenu();
    }
    public void more(View view){
        menu.showSecondaryMenu();
    }

    /**
     *  左侧滑菜单的登录回调方法
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

        if(resultCode==101){
            resultJson = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
            sp.edit().putString("pindao",resultJson).commit();
            list.clear();
            fragmentList.clear();
            initData();
            //recreate();

        }
    }



    //左侧滑菜单的更多登陆方式点击事件
    public void morelogin(View view){
        Intent intent=new Intent(this, MoreLoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.pindao:
                sp = getSharedPreferences("pindao", MODE_PRIVATE);
                pindaos = sp.getString("pindao", null);
                if(pindaos==null){
                    List<ChannelBean> list=new ArrayList<>();
                    list.add(new ChannelBean("头条",true));
                    list.add(new ChannelBean("社会",true));
                    list.add(new ChannelBean("国内",true));
                    list.add(new ChannelBean("国际",true));
                    list.add(new ChannelBean("娱乐",true));
                    list.add(new ChannelBean("体育",true));
                    list.add(new ChannelBean("军事",true));
                    list.add(new ChannelBean("科技",true));
                    list.add(new ChannelBean("财经",true));
                    list.add(new ChannelBean("时尚",true));
                    ChannelActivity.startChannelActivity(this,list);
                }else{
                    ChannelActivity.startChannelActivity(this,pindaos);
                }
                break;

        }
    }
}


