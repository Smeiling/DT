package com.dt.sml.duitang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dt.sml.duitang.R;
import com.dt.sml.duitang.model.PostItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by songmeiling on 2017/7/14.
 */
public class MenuAdapter extends SwipeMenuAdapter<MenuAdapter.ItemViewHolder> {

    private List<String> titles;
    private List<PostItem> postList;

    private OnItemClickListener mOnItemClickListener;

    private Context mContext;

    public MenuAdapter(Context context, List<PostItem> list) {
        this.mContext = context;
        this.postList = list;
    }

    public MenuAdapter(List<String> titles) {
        this.titles = titles;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return postList == null ? 0 : postList.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view, parent, false);
        }
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public MenuAdapter.ItemViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        ItemViewHolder viewHolder = new ItemViewHolder(mContext, realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ItemViewHolder holder, int position) {
        holder.setData(postList.get(position));
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context context;

        ImageView ivHeader;
        TextView tvUserNick;
        TextView tvTitle;
        TextView tvText;
        ImageView ivImg;
        TextView tvReviewCount;
        TextView tvStarCount;
        OnItemClickListener mOnItemClickListener;

        public ItemViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(this);
            ivHeader = (ImageView) itemView.findViewById(R.id.iv_user_header);
            tvUserNick = (TextView) itemView.findViewById(R.id.tv_user_nick);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_content_title);
            tvText = (TextView) itemView.findViewById(R.id.tv_content_text);
            ivImg = (ImageView) itemView.findViewById(R.id.iv_content_img);
            tvReviewCount = (TextView) itemView.findViewById(R.id.tv_review_count);
            tvStarCount = (TextView) itemView.findViewById(R.id.tv_star_count);
        }

        public void setData(PostItem post) {
            Glide.with(context)
                    .load(post.getHeaderUrl())
                    .placeholder(R.drawable.header_boy)
                    .into(ivHeader);
            tvUserNick.setText(post.getUserNick());
            tvTitle.setText(post.getContentTitle());
            tvText.setText(post.getContentText());
            Glide.with(context)
                    .load(post.getContentImgUrl())
                    .placeholder(R.drawable.image_place_holder)
                    .into(ivImg);
            tvReviewCount.setText(String.valueOf(post.getReviewCount()));
            tvStarCount.setText(String.valueOf(post.getStarCount()));
        }

        public void setData(String headerUrl, String userNick, String title, String text, String imgUrl, int reviewCount, int startCount) {
            Glide.with(context)
                    .load(headerUrl)
                    .placeholder(R.drawable.header_boy)
                    .into(ivHeader);
            tvUserNick.setText(userNick);
            tvTitle.setText(title);
            tvText.setText(text);
            Glide.with(context)
                    .load(imgUrl)
                    .placeholder(R.drawable.image_place_holder)
                    .into(ivImg);
            tvReviewCount.setText(String.valueOf(reviewCount));
            tvStarCount.setText(String.valueOf(startCount));
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
