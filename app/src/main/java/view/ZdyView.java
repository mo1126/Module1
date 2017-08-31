package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.module.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mo on 2017/8/31.
 */

public class ZdyView extends LinearLayout implements ViewPager.OnPageChangeListener{

    public Context context;
    public List<String> list;
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
    }

    //得到外部传的数据
    public void diaplay(List<String> list, List<Fragment> fragments){
        this.list=list;
        this.fragments=fragments;

        darwUi();
    }
    //绘制页面
    private void darwUi() {
        drawtop();
        drawViewpager();
    }

    /**
     * 绘制viewpager
     */
    private void drawViewpager() {
        vp.setAdapter(new Mypageradapter(((FragmentActivity)context).getSupportFragmentManager()));
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
    public void onPageScrollStateChanged(int state) {

    }

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
            return fragments.size();
        }
    }

    /**
     * 绘制选择横滑
     */
    private void drawtop() {
        ll.setBackgroundColor(Color.WHITE);
        for (int i = 0; i < list.size(); i++) {
            final TextView tv = (TextView) View.inflate(context, R.layout.tv_item, null);
            tv.setText(list.get(i));

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
