package com.xcx.dailynews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcx.dailynews.R;
import com.xcx.dailynews.bean.NewsBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xcx on 2016/11/1.
 */

public class NewsAdapterTest extends RecyclerView.Adapter<NewsAdapterTest.ViewHolder> {

    List<NewsBean.Bean> mList;

    public NewsAdapterTest(List<NewsBean.Bean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=View.inflate(parent.getContext(), R.layout.news_item,null);
        ViewHolder holder=new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置数据
        holder.mTvTitle.setText(mList.get(position).title);
    }

    @Override
    public int getItemCount() {
        if (mList!=null){
            return mList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_news_title)
       public  TextView mTvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
