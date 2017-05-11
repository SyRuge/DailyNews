package com.xcx.dailynews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcx.dailynews.R;
import com.xcx.dailynews.bean.CollectBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xcx on 2017/4/9.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {




    public interface OnItemClickLitener {
        void onItemClick(View view, int position, CollectBean bean);

        void onUncollectClick(View view, int position,CollectBean bean);
    }

    private OnItemClickLitener mLitener;

    public void setOnItemClickLitener(OnItemClickLitener litener) {
        mLitener = litener;
    }


    public List<CollectBean> mList;

    public CollectAdapter(List<CollectBean> list) {
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_collect_news, parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        initListener(vh);
        CollectBean cb = mList.get(position);
        vh.mTvCollectNewsTitle.setText(cb.getSource());
        vh.mTvCollecNewsDetail.setText(cb.getDigest());
        vh.mTvCollectTime.setText(cb.getLmodify());
        vh.mTvCollectSource.setText(cb.getTitle());
    }

    private void initListener(final MyViewHolder holder) {

        final int p = holder.getLayoutPosition();
        //删除按钮点击事件
        if (mLitener != null) {
            holder.mTvCollectDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLitener.onUncollectClick(holder.mTvCollectDel, p,mList.get(p));
                }
            });
            //整个条目的点击事件
            holder.mLlLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLitener.onItemClick(holder.mLlLayout, p,mList.get(p));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null || mList.size() != 0) {
            return mList.size();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_collect_source)
        public TextView mTvCollectSource;
        @Bind(R.id.tv_collect_time)
        public TextView mTvCollectTime;
        @Bind(R.id.tv_collect_news_title)
        public TextView mTvCollectNewsTitle;
        @Bind(R.id.tv_collect_news_detail)
        public TextView mTvCollecNewsDetail;
        @Bind(R.id.tv_collect_del)
        public TextView mTvCollectDel;
        @Bind(R.id.ll_collect_root)
        public LinearLayout mLlLayout;//收藏条目的根布局


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
