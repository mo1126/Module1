package myadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.module.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import Bean.News;

/**
 * Created by Mo on 2017/8/30.
 */

public class Myadapter extends BaseAdapter{

    public Context context;
    public List<News.ResultBean.DataBean> list;

    public final int count=2;
    public final int atype=0;
    public final int btype=1;

    public Myadapter(Context context, List<News.ResultBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void addlist(List<News.ResultBean.DataBean> list){
        this.list.addAll(list);
    }
    @Override
    public int getItemViewType(int position) {
        if(position%2==1){
            return atype;
        }else{
            return btype;
        }
    }

    @Override
    public int getViewTypeCount() {
        return count;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder1{
        ImageView iv;
        TextView title;
        TextView author_name;
        TextView date;
    }
    public class ViewHolder2{
        ImageView iv2;
        TextView title2;
        TextView author_name2;
        TextView date2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder2 holder2=null;
        ViewHolder1 holder1=null;
        int type = getItemViewType(i);
        if(view==null){
            switch (type){
                case atype:
                    holder1=new ViewHolder1();
                    view=View.inflate(context, R.layout.item1,null);
                    holder1.title=view.findViewById(R.id.title1);
                    holder1.author_name=view.findViewById(R.id.author_name1);
                    holder1.date=view.findViewById(R.id.date1);
                    holder1.iv=view.findViewById(R.id.iv1);
                    view.setTag(holder1);
                    break;
                case btype:
                    holder2=new ViewHolder2();
                    view=View.inflate(context, R.layout.item2,null);
                    holder2.title2=view.findViewById(R.id.title2);
                    holder2.author_name2=view.findViewById(R.id.author_name2);
                    holder2.date2=view.findViewById(R.id.date2);
                    holder2.iv2=view.findViewById(R.id.iv2);
                    view.setTag(holder2);
                    break;
            }
        }else{
            switch (type){
                case atype:
                    holder1= (ViewHolder1) view.getTag();
                    break;
                case btype:
                    holder2= (ViewHolder2) view.getTag();
                    break;
            }
        }

        switch (type){
            case atype:
                holder1.title.setText(list.get(i).title);
                holder1.author_name.setText(list.get(i).author_name);
                holder1.date.setText(list.get(i).date);
                ImageLoader.getInstance().displayImage(list.get(i).thumbnail_pic_s,holder1.iv);
                break;
            case btype:
               holder2.title2.setText(list.get(i).title);
                holder2.author_name2.setText(list.get(i).author_name);
                holder2.date2.setText(list.get(i).date);
               ImageLoader.getInstance().displayImage(list.get(i).thumbnail_pic_s,holder2.iv2);
                break;
        }
        return view;
    }
}
