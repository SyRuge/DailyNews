package com.xcx.dailynews.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xcx.dailynews.R;
import com.xcx.dailynews.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xcx on 2016/11/9.
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {




    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    private List<PhotoBean> mList;
    private List<Integer> mHeight;
    private StaggeredGridLayoutManager mManager;

    public PhotoAdapter(List<PhotoBean> list, StaggeredGridLayoutManager manager) {
        mManager = manager;
        mList = list;
        mHeight = new ArrayList<>();

        //随机获取一个mHeight值
        for (int i = 0; i < list.size(); i++) {
            mHeight.add((int) (500 + Math.random() * 800));
        }
    }

    @Override
    public int getItemViewType(int position) {
        //最后一个条目是加载更多
        if (position == mList.size()) {
            return 1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            //普通条目
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, null);
            return new MyViewHolder(v);
        } else {
            //加载更多
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_footer_item,
                    null);
            return new MyFooterHolder(v);
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder vh = (MyViewHolder) holder;
            initListener(vh);
            ViewGroup.LayoutParams lp = vh.mSvPhoto.getLayoutParams();
            lp.height = mHeight.get(position);
            vh.itemView.setLayoutParams(lp);
            vh.mSvPhoto.setImageURI(mList.get(position).url);

        } else {

        }

    }

    private void initListener(final MyViewHolder holder) {
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int largePosition = getLargePosition(mManager.findLastVisibleItemPositions(new int[mManager
                .getSpanCount()]));

        int position = holder.getLayoutPosition();


        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams
                && position == mList.size()) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager
                    .LayoutParams) holder.itemView.getLayoutParams();

            p.setFullSpan(true);
        }
    }

    private int getLargePosition(int[] p) {
        int temp = p[0];

        for (int i = 1; i < p.length; i++) {
            if (temp <= p[i]) {
                temp = p[i];
            }
        }
        return temp;
    }

    @Override
    public int getItemCount() {
        if (mList != null || mList.size() != 0) {
            return mList.size() + 1;
        }
        return 0;
    }

    public static class MyFooterHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_footer_more)
        public TextView mTvFooterMore;
        @Bind(R.id.pb_photo_more)
        public ProgressBar mPbPhotoMore;
        @Bind(R.id.rl_footer_content)
        public RelativeLayout mRlFooterContent;

        public MyFooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.sv_photo_first)
        public SimpleDraweeView mSvPhoto;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
