package com.xcx.dailynews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xcx.dailynews.R;
import com.xcx.dailynews.bean.PhotoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xcx on 2016/11/8.
 */

public class StagggerAdapter extends RecyclerView.Adapter<StagggerAdapter.MyHolder> {

    public List<PhotoBean> mList;
    private OnGetMoreListener mOnGetMoreListener;

    public void setOnGetMoreListener(OnGetMoreListener listener) {
        mOnGetMoreListener = listener;
    }

    /**
     * 加载更多监听
     */
    public interface OnGetMoreListener {
        void onGetMore();
    }

    public List<Integer> mHeight;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public StagggerAdapter(List<PhotoBean> mList) {
        this.mList = mList;
        mHeight = new ArrayList<>();

        //随机获取一个mHeight值
        for (int i = 0; i < mList.size(); i++) {
            mHeight.add((int) (500 + Math.random() * 800));
        }
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, null);
        return new MyHolder(v);

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        initListener(holder);

        int pos = holder.getLayoutPosition();
        if (mList.size()-pos<5&&mOnGetMoreListener!=null){
            //通知需要加载更多的数据
            mOnGetMoreListener.onGetMore();
        }
        //绑定数据的同时，修改每个ItemView的高度
        ViewGroup.LayoutParams lp = holder.mSvPhotoFirst.getLayoutParams();
        lp.height = mHeight.get(position);
        holder.itemView.setLayoutParams(lp);
        holder.mSvPhotoFirst.setImageURI(mList.get(position).url);

    }

    private void initListener(final MyHolder holder) {
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
    public int getItemCount() {
        if (mList != null || mList.size() != 0) {
            return mList.size();
        }
        return 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.sv_photo_first)
        public SimpleDraweeView mSvPhotoFirst;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
