package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.module.R;

import java.util.ArrayList;
import java.util.List;

import Bean.Pindao;

/**
 * Created by Mo on 2017/8/31.
 */

public class ZdyView extends LinearLayout implements ViewPager.OnPageChangeListener{

    public Context context;
    public List<Pindao> list;
    public List<Fragment> fragments;
    private ViewPager vp;
    private LinearLayout ll;
    private HorizontalScrollView scrollView;
    private List<TextView> tv_list;

    public ZdyView(Context context) {
        this(context,null);
    }

    public ZdyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZdyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init(context,attrs);
    }

    //初始化
    private void init(Context context,AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.scrollview, this, true);
        scrollView = view.findViewById(R.id.horizontalscrollview);
        ll = view.findViewById(R.id.ll);
        vp = view.findViewById(R.id.vp);
        vp.addOnPageChangeListener(this);
        tv_list = new ArrayList<>();
        fragments=new ArrayList<>();
    }

    //得到外部传的数据
    public void diaplay(List<Pindao> list, List<Fragment> fragments){
        this.list=list;
        this.fragments=fragments;
        darwUi();
    }
    //绘制页面
    private void darwUi() {
        System.out.println(list.toString());
        System.out.println(fragments.toString());
        drawtop();
        drawViewpager();
    }

    /**
     * 绘制viewpager
     */

    private void drawViewpager() {
        Mypageradapter mypageradapter = new Mypageradapter(((FragmentActivity) context).getSupportFragmentManager());
        vp.setAdapter(mypageradapter);
        mypageradapter.notifyDataSetChanged();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < tv_list.size(); i++) {
            if(position==i){
                tv_list.get(i).setSelected(true);
            }else{
                tv_list.get(i).setSelected(false);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    public class Mypageradapter extends FragmentPagerAdapter{
        public Mypageradapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            int sum=0;
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).isSelect){
                    sum++;
                }
            }
            return sum;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, 0, object);
    }

    }

    /**
     * 绘制选择横滑
     */
    private void drawtop() {
        int num=0;
        for (Pindao pindao : list) {
            if(pindao.isSelect){
                num++;
            }
        }

        tv_list.clear();
        ll.removeAllViews();
        for (int i = 0; i < num; i++) {
            final TextView tv = (TextView) View.inflate(context, R.layout.tv_item, null);
                    tv.setText(list.get(i).name);
            //设置点击事件
            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    tv.setSelected(true);
                    vp.setCurrentItem(finalI);
                }
            });
            //添加到ll
            ll.addView(tv);
            tv_list.add(tv);
        }
        tv_list.get(0).setSelected(true);
    }

}
