package com.xcx.dailynews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xcx.dailynews.R;

import java.util.List;

/**
 * Created by xcx on 2017/4/18.
 */

public class PhotoPagerAdapter extends PagerAdapter {
    private List<String> mList;

    private String mTitle;



    public PhotoPagerAdapter(List<String> list,String title) {
        mList = list;
        mTitle=title;
    }


    @Override
    public int getCount() {
        if (mList!=null&&mList.size()!=0){
            return mList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = View.inflate(container.getContext(), R.layout.photo_detail_item, null);
        SimpleDraweeView sd= (SimpleDraweeView) v.findViewById(R.id.sd_photo_detail);
        TextView tv= (TextView) v.findViewById(R.id.tv_photo_detail);
        tv.setText("("+(position+1)+"/2) "+mTitle);
        sd.setImageURI(mList.get(position));
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(container.getChildAt(position));
    }
}
