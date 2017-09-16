package fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bwie.module.LixianActivity;
import com.bwie.module.Myapp;
import com.bwie.module.R;
import com.bwie.utils.CleanMessageUtil;
import com.bwie.utils.NetWorkInfo;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;


/**
 * Created by Mo on 2017/8/30.
 */

public class Fragment2 extends Fragment implements View.OnClickListener{

    private View view;
    private RelativeLayout lixian;
    private RelativeLayout network_sll;
    private RelativeLayout clear;
    private Switch tuisong;
    private TextView bigorno;
    private TextView daxiao;
    private String[] strings;
    private RelativeLayout textsize;
    private String[] strings1;
    private TextView size;

    @Override
    public void onResume() {
        super.onResume();
        try {
            String size1 = CleanMessageUtil.getTotalCacheSize(getContext());
            size.setText(size1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.right_content, null);
        return view;
    }

    /**
     * 推送
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();


        tuisong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    JPushInterface.resumePush(getContext());
                }else{
                    JPushInterface.stopPush(getContext());
                }
            }
        });
    }

    private void initView() {
        lixian = view.findViewById(R.id.lixian);
        network_sll = view.findViewById(R.id.network_sll);
        tuisong = view.findViewById(R.id.tuisong);
        bigorno = view.findViewById(R.id.bigorno);
        daxiao = view.findViewById(R.id.daxiao);
        textsize = view.findViewById(R.id.textsize);
        clear= view.findViewById(R.id.clear);
        size = clear.findViewById(R.id.size);
        lixian.setOnClickListener(this);
        network_sll.setOnClickListener(this);
        textsize.setOnClickListener(this);
        clear.setOnClickListener(this);
        try {
            String size1 = CleanMessageUtil.getTotalCacheSize(getContext());
             size.setText(size1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SharedPreferences big = getActivity().getSharedPreferences("bigorno", Context.MODE_PRIVATE);
         boolean b = big.getBoolean("big", true);
        if( b){
            bigorno.setText("最佳效果(加载大图)");
        }else{
            bigorno.setText("极省流量(不加载图)");
        }

        SharedPreferences textsize = getActivity().getSharedPreferences("textsize", Context.MODE_PRIVATE);
        boolean size = textsize.getBoolean("big", true);
        if( size){
            daxiao.setText("大字");
        }else{
            daxiao.setText("小字");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clear:
                CleanMessageUtil.clearAllCache(getActivity().getApplicationContext());
                try {
                    String size1 = CleanMessageUtil.getTotalCacheSize(getContext());
                    size.setText(size1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.lixian:
                Intent intent=new Intent(getActivity(),LixianActivity.class);
                startActivity(intent);
                break;
            case R.id.network_sll:
                SharedPreferences big = getActivity().getSharedPreferences("bigorno", Context.MODE_PRIVATE);
                final boolean b = big.getBoolean("big", true);
                if(b){
                    strings = new String[]{"大图", "无图"};
                }else{
                    strings = new String[]{ "无图","大图"};
                }
                AlertDialog.Builder alert=new AlertDialog.Builder(getActivity());
                alert.setTitle("省流量模式");
                alert.setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //存加载大图还是不加载图
                        SharedPreferences big = getActivity().getSharedPreferences("bigorno", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = big.edit();
                        if(b){
                            if(i==0){
                                edit.putBoolean("big",true);
                                bigorno.setText("最佳效果(加载大图)");
                            }else{
                                edit.putBoolean("big",false);
                                bigorno.setText("极省流量(不加载图)");
                            }
                        }else{
                            if( i==1){
                                edit.putBoolean("big",true);
                                bigorno.setText("最佳效果(加载大图)");
                            }else{
                                edit.putBoolean("big",false);
                                bigorno.setText("极省流量(不加载图)");
                            }
                        }
                        edit.commit();
                        dialogInterface.dismiss();
                    }
                });
                alert.create().show();
                break;
            case R.id.textsize:
                SharedPreferences textsize = getActivity().getSharedPreferences("textsize", Context.MODE_PRIVATE);

                final boolean size = textsize.getBoolean("textsize", true);
                if(size){
                    strings1 = new String[]{"大字", "小字"};
                }else{
                    strings1 = new String[]{ "小字","大字"};
                }
                AlertDialog.Builder alert1=new AlertDialog.Builder(getActivity());
                alert1.setTitle("字体大小");
                alert1.setSingleChoiceItems(strings1, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //存加载大图还是不加载图
                        SharedPreferences textsize = getActivity().getSharedPreferences("textsize", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = textsize.edit();
                        if(size){
                            if(i==0){
                                edit.putBoolean("textsize",true);
                                daxiao.setText("大字");
                            }else{
                                edit.putBoolean("textsize",false);
                                daxiao.setText("小字");
                            }
                        }else{
                            if( i==1){
                                edit.putBoolean("textsize",true);
                                daxiao.setText("大字");
                            }else{
                                edit.putBoolean("textsize",false);
                                daxiao.setText("小字");
                            }
                        }
                        edit.commit();
                        dialogInterface.dismiss();
                        getActivity().recreate();
                    }
                });
                alert1.create().show();

                break;
        }
    }
}
