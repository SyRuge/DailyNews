package com.xcx.dailynews.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xcx.dailynews.R;
import com.xcx.dailynews.bean.BaseDataBean;
import com.xcx.dailynews.mvp.ui.view.ProgressBarCircularIndeterminate;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xcx on 2016/11/2.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<BaseDataBean> mList;


    private OnGetPastNewsListener mListener;
    private OnItemViewClickListener mOnItemViewClickListener;

    public void setOnItemViewClickListener(OnItemViewClickListener listener) {
        mOnItemViewClickListener = listener;
    }

    public void setOnGetPastNewsListener(OnGetPastNewsListener listener) {
        mListener = listener;
    }

    public interface OnItemViewClickListener {
        void onItemClick(View view, int position, String url, String title);
    }

    public interface OnGetPastNewsListener {
        void getPastData();

        boolean isHasData();
    }

    public NewsAdapter(List<BaseDataBean> list) {
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {

        //加载更多的类型
        if (position == mList.size()) {
            return 3;
        }

        //类型2为图集
        if (mList.get(position).skipType != null & "photoset".equals(mList.get(position)
                .skipType)) {
            return 2;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == 0) {
            //普通条目
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,
                    null);
            return new MyViewHolder(v);
        } else if (viewType == 2) {
            //全图片类型的
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_other_item,
                    null);
            return new MyPhotoViewHolder(v);
        } else {
            //加载更多的类型
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_footer_item,
                    null);
            return new MyFooterHolder(v);
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder vh = (MyViewHolder) holder;
            vh.mTvNewsTitle.setText(mList.get(position).title);
            vh.mTvNewsDetail.setText(mList.get(position).digest);
            vh.mTvNewsDate.setText(mList.get(position).lmodify);
            Uri uri = Uri.parse(mList.get(position).imgsrc);
            vh.mIvNewsPic.setImageURI(uri);

        } else if (holder instanceof MyPhotoViewHolder) {
            MyPhotoViewHolder vh = (MyPhotoViewHolder) holder;
            vh.tvTitle.setText(mList.get(position).title);
            vh.tvDate.setText(mList.get(position).lmodify);
            List<BaseDataBean.ImgextraBean> imgextra = mList.get(position).imgextra;
            if (imgextra == null & mList.get(position).imgType == 1) {
                vh.ivOnlyPic.setVisibility(View.VISIBLE);
                vh.ivFirstPic.setVisibility(View.GONE);
                vh.ivSecondPic.setVisibility(View.GONE);
                vh.ivOnlyPic.setImageURI(mList.get(position).imgsrc);
            } else {
                vh.ivFirstPic.setVisibility(View.VISIBLE);
                vh.ivSecondPic.setVisibility(View.VISIBLE);
                vh.ivOnlyPic.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(imgextra.get(0).imgsrc)) {

                    vh.ivFirstPic.setImageURI(imgextra.get(0).imgsrc);
                }

                if (!TextUtils.isEmpty(imgextra.get(1).imgsrc)) {

                    vh.ivSecondPic.setImageURI(imgextra.get(1).imgsrc);
                }

            }
        } else if (holder instanceof MyFooterHolder) {
            MyFooterHolder vh = (MyFooterHolder) holder;
            if (mListener != null) {
                mListener.getPastData();
            }


            if (mListener != null && mListener.isHasData()) {
                //还有数据
                vh.mTvRefresh.setVisibility(View.VISIBLE);
                vh.mPbFooter.setVisibility(View.VISIBLE);
                vh.mTvNodata.setVisibility(View.GONE);
            } else {
                vh.mTvRefresh.setVisibility(View.GONE);
                vh.mPbFooter.setVisibility(View.GONE);
                vh.mTvNodata.setVisibility(View.VISIBLE);
            }

        }

        if (mOnItemViewClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemViewClickListener.onItemClick(holder.itemView, pos, mList.get(pos)
                            .url, mList.get(pos).title);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mList != null && mList.size() != 0) {
            return mList.size() + 1;
        }
        return 0;
    }

    public static class MyFooterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_footer_nodata)
        public TextView mTvNodata;
        @Bind(R.id.tv_footer_refresh)
        public TextView mTvRefresh;
        @Bind(R.id.pb_footer)
        public ProgressBarCircularIndeterminate mPbFooter;
        @Bind(R.id.rl_foot_root)
        public RelativeLayout mRlFootRoot;

        public MyFooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_news_title)
        public TextView mTvNewsTitle;
        @Bind(R.id.tv_news_detail)
        public TextView mTvNewsDetail;
        @Bind(R.id.tv_news_date)
        public TextView mTvNewsDate;
        @Bind(R.id.iv_news_pic)
        public SimpleDraweeView mIvNewsPic;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MyPhotoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_other_only)
        public SimpleDraweeView ivOnlyPic;
        @Bind(R.id.iv_other_first)
        public SimpleDraweeView ivFirstPic;
        @Bind(R.id.iv_other_second)
        public SimpleDraweeView ivSecondPic;
        @Bind(R.id.tv_news_other_title)
        public TextView tvTitle;
        @Bind(R.id.tv_news_other_date)
        public TextView tvDate;

        public MyPhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
