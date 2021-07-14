package com.example.chapter3.homework;


import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

public class PlaceholderFragment extends Fragment {

    private LottieAnimationView mLottieAnimationView;
    private RecyclerView mRecyclerView;
    //系统默认的短时间
    private int shortAnimationDuration;

    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View view= inflater.inflate(R.layout.fragment_placeholder, container, false);
        mContext=getContext();
        mLottieAnimationView=(LottieAnimationView)view.findViewById(R.id.animation_lottie_demo);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.item_recycler_view);

        shortAnimationDuration=getResources().getInteger(android.R.integer.config_longAnimTime);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                crossFade();
                initData();

            }
        }, 5000);
    }

    //填充RecycleView的数据
    private void initData(){
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        ItemLab itemLab=ItemLab.get(mContext);
        List<Item> items=itemLab.getItems();
        ItemAdapter mItemAdapter=new ItemAdapter(items);
        mRecyclerView.setAdapter(mItemAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

    }

    //淡入淡出动画效果，将RecycleView从GONE设为可见，同时将LottieAnimationView从可见设为不可见
    private void crossFade(){
    mRecyclerView.setAlpha(0f);
    mRecyclerView.setVisibility(View.VISIBLE);
//    Animate the content view to 100% opacity
    mRecyclerView.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);

    mLottieAnimationView.animate().alpha(0f).setDuration(shortAnimationDuration).setListener(new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            mLottieAnimationView.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    });
    }


    private class ItemHolder extends RecyclerView.ViewHolder{
        private ImageView mTvAvatar;
        private TextView mTvTitle;
        private TextView mTvDescription;
        private View mContentView;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mContentView=itemView;
            mTvAvatar=(ImageView)itemView.findViewById(R.id.tv_avatar);
            mTvTitle=(TextView) itemView.findViewById(R.id.tv_title);
            mTvDescription=(TextView)itemView.findViewById(R.id.tv_hot);
        }
        public void bindItem(Item item){
            mTvTitle.setText(item.getName());
            mTvDescription.setText(item.getDescription());
        }
    }




    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder>{
        private List<Item> mItems;

        public ItemAdapter(List<Item>items){
            this.mItems=items;
        }


        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, final int position) {
            final Item item=mItems.get(position);
            holder.bindItem(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
//        public void addData(int position, Item data) {
//            mItems.add(position, data);
//            notifyItemInserted(position);
//            if (position != mItems.size()) {
//                //刷新改变位置item下方的所有Item的位置,避免索引错乱
//                notifyItemRangeChanged(position, mItems.size() - position);
//            }
//        }
//        public void removeData(int position) {
//            if (null != mItems && mItems.size() > position) {
//                mItems.remove(position);
//                notifyItemRemoved(position);
//                if (position != mItems.size()) {
//                    //刷新改变位置item下方的所有Item的位置,避免索引错乱
//                    notifyItemRangeChanged(position, mItems.size() - position);
//                }
//            }
//        }
    }

}
