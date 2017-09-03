package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.module.MainActivity;
import com.bwie.module.MoreLoginActivity;
import com.bwie.module.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;


/**
 * Created by Mo on 2017/8/30.
 */

public class Fragment1 extends Fragment implements View.OnClickListener{

    private View view;
    private ImageView yz_phone;
    private ImageView yz_qq;
    private ImageView yz_weibo;
    private ImageView yz_weixin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.left_content, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();

    }

    private void initview() {
        yz_phone = view.findViewById(R.id.yz_phone);
        yz_qq = view.findViewById(R.id.yz_qq);
        yz_weibo = view.findViewById(R.id.yz_weibo);
        yz_weixin = view.findViewById(R.id.yz_weixin);
        yz_phone.setOnClickListener(this);
        yz_qq.setOnClickListener(this);
        yz_weibo.setOnClickListener(this);
        yz_weixin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yz_qq:

                UMShareAPI.get(getContext()).getPlatformInfo(getActivity(), SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.yz_phone:
                break;
            case R.id.yz_weibo:
                UMShareAPI.get(getContext()).getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, umAuthListener);
                break;
            case R.id.yz_weixin:
                break;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            Toast.makeText(getContext(), "开始", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            String iconurl = data.get("iconurl");
            ImageLoader.getInstance().displayImage(iconurl, MainActivity.head);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

}
