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
 * Created by Mo on 2017/8/30.
 */

public class Zdyview extends LinearLayout implements ViewPager.OnPageChangeListener{

    public Context context;
    private ViewPager vp;
    private HorizontalScrollView scrollview;
    private LinearLayout ll;
    private List<String>list;
    private  List<Fragment> fragments;
    private int count;
    private int mColor;
    private List<TextView> topview;

    public Zdyview(Context context) {
        this(context,null);
    }

    public Zdyview(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Zdyview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init(context,attrs);
    }

    /**
     * 初始化自定义属性
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Zdyview);
        typedArray.recycle();

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.scrollview, this, true);
        scrollview = view.findViewById(R.id.horizontalscrollview);
        vp = view.findViewById(R.id.vp);
        vp.addOnPageChangeListener(this);

        ll = view.findViewById(R.id.ll);
    }

    /**
     * 调用者调用此方法 传具体数据
     * @param list
     * @param fragments
     */
    public  void diaplay(List<String> list, List<Fragment> fragments){
            this.list=list;
        this.fragments=fragments;
        this.count=list.size();
        topview = new ArrayList<>(count);
        drawUi();
    }

    private void drawUi() {
            drawHorizontal();
            drawViewpager();
    }



    /**
     * 绘制viewpager
     */
    private void drawViewpager() {
        vp.setAdapter(new Myadapter(((FragmentActivity)context).getSupportFragmentManager()));
    }

    /**
     * 绘制横向滑动菜单
     */
    private void drawHorizontal() {
        ll.setBackgroundColor(Color.WHITE);
        for (int i = 0; i < count; i++) {
            String s = list.get(i);
            TextView tv= (TextView) View.inflate(context,R.layout.tv_item,null);
            tv.setText(s);
            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    vp.setCurrentItem(finalI);
                }
            });
            ll.addView(tv);
            topview.add(tv);
        }
        topview.get(0).setSelected(true);
    }

    public class Myadapter extends FragmentPagerAdapter{

        public Myadapter(FragmentManager fm) {
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
            if(ll!=null&&ll.getChildCount()>0){
                for (int i = 0; i < ll.getChildCount(); i++) {

                    if(i==position){
                        ll.getChildAt(i).setSelected(true);
                    }else{
                        ll.getChildAt(i).setSelected(false);
                    }
                }
            }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
