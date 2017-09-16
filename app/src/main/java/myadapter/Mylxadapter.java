package myadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import Bean.Lxstate;

import com.bwie.module.R;

import java.util.List;

/**
 * Created by Mo on 2017/9/5.
 */

public class Mylxadapter extends RecyclerView.Adapter<Mylxadapter.MyViewHolder>{
    private Context context;
    private List<Lxstate> list;
   private  onintemclick onintemclick;
    public Mylxadapter(Context context, List<Lxstate> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.lxitem, null);
        final MyViewHolder myViewHolder=new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onintemclick.OnItemClickListener((Integer) view.getTag(),view);
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).name);
        holder.lv_cb.setChecked(list.get(position).state);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private  TextView name;
        private  CheckBox lv_cb;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.lx_name);
            lv_cb = itemView.findViewById(R.id.lx_cb);
        }
    }


    public void setOnintemclick(Mylxadapter.onintemclick onintemclick) {
        this.onintemclick = onintemclick;
    }

    public interface onintemclick{
            void OnItemClickListener(int i,View view);
    }
}
