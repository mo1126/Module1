package com.bwie.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import Bean.Lxstate;
import com.bwie.utils.Dao;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import myadapter.Mylxadapter;

public class LixianActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView lv;
    private List<Lxstate> list;
    private ImageView back;
    private TextView down;
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lixian);
        initView();
        initData();
    }

    private void initData() {
        dao = new Dao(this);
        list = new ArrayList<>();
        list.add(new Lxstate("top","头条",true));
        list.add(new Lxstate("shehui","社会",true));
        list.add(new Lxstate("guonei","国内",true));
        list.add(new Lxstate("guoji","国际",true));
        list.add(new Lxstate("yule","娱乐",true));
        list.add(new Lxstate("tiyu","体育",true));
        list.add(new Lxstate("junshi","军事",true));
        list.add(new Lxstate("keji","科技",true));
        list.add(new Lxstate("caijing","财经",true));
        list.add(new Lxstate("shishang","时尚",true));


        Mylxadapter myadapter=new Mylxadapter(this,list);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.setAdapter(myadapter);
        myadapter.setOnintemclick(new Mylxadapter.onintemclick() {
            @Override
            public void OnItemClickListener(int i, View view) {
                CheckBox cb=view.findViewById(R.id.lx_cb);
                Lxstate lxstate = list.get(i);
                if(cb.isChecked()){
                    cb.setChecked(false);
                    lxstate.state=false;
                }else{
                    cb.setChecked(true);
                    lxstate.state=true;
                }
                list.set(i,lxstate);
            }
        });
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.lx_back);
        lv = (RecyclerView) findViewById(R.id.lx_lv);
        down = (TextView) findViewById(R.id.lx_down);
        back.setOnClickListener(this);
        down.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lx_back:
                finish();
                break;
            case R.id.lx_down:
                downloade();
                break;
        }
    }

    private void downloade() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).state) {
                System.out.println(list.get(i).state);
            RequestParams params = new RequestParams(Myapi.url);
            params.addQueryStringParameter("key", Myapi.key);
            params.addQueryStringParameter("type", list.get(i).type);
                final int finalI = i;
                x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                        dao.add(list.get(finalI).type,result);
                    System.out.println(result);
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
        }
    }
}
