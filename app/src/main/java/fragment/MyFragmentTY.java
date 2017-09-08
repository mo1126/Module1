package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bwie.module.Myapi;
import com.bwie.module.R;
import com.bwie.module.XQActivity;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import Bean.News;
import myadapter.Myadapter;
import view.xlistview.XListView;

/**
 * Created by Mo on 2017/8/30.
 */

public class MyFragmentTY extends android.support.v4.app.Fragment implements XListView.IXListViewListener{

    private View view;
    private List<News.ResultBean.DataBean> list;
    private Myadapter myadapter;
    private XListView xlv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =View.inflate(getActivity(),R.layout.myfragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xlv = view.findViewById(R.id.xlv);
        initData();
        getdata();
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(),XQActivity.class);
                intent.putExtra("url",list.get(i-1).url);
                startActivity(intent);
            }
        });
    }

    private void getdata() {
        RequestParams params=new RequestParams(Myapi.url);
        params.addQueryStringParameter("key",Myapi.key);
        params.addQueryStringParameter("type","tiyu");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
                Gson gson=new Gson();
                News news = gson.fromJson(result, News.class);
                 list = news.result.data;
                System.out.println(list);
                setdata();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {  }

            @Override
            public void onCancelled(CancelledException cex) { }
            @Override
            public void onFinished() {  }
        });
    }

    private void setdata() {
        if(myadapter==null){
            myadapter = new Myadapter(getContext(), list);
            xlv.setAdapter(myadapter);
        }else{
            myadapter.addlist(list);
            myadapter.notifyDataSetChanged();
        }
        xlv.stopRefresh();
        xlv.stopLoadMore();
    }


    private void initData() {
        list = new ArrayList<>();

        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);
    }


    @Override
    public void onRefresh() {
        list.clear();
        myadapter=null;
        getdata();
    }

    @Override
    public void onLoadMore() {
        getdata();
        myadapter.addlist(list);
    }
}
