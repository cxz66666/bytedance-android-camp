package net.zjueva.bytedance_hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecycleActivity extends AppCompatActivity implements IOnItemClickListener {

    private static final String TAG = "HW2";
    private RecyclerView recyclerView;
    private ItemAdapter mItemAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView mDateView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        Log.d(TAG, "RecyclerViewActivity onCreate");
        initView();
    }
    private void initView(){
        recyclerView=findViewById(R.id.item_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ItemLab itemLab=ItemLab.get(this);
        List<Item> items=itemLab.getItems();
        mItemAdapter=new ItemAdapter(items);
//        mItemAdapter.setOnItemClickListener(this);
        mItemAdapter.setOnItemClickListener(this);

        recyclerView.setAdapter(mItemAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(3000);
        recyclerView.setItemAnimator(animator);

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mDateView=(TextView)findViewById(R.id.update_time);
        mDateView.setText("更新于 "+dateFormat.format(date));

    }

    @Override
    public void onItemClick(int position, Item data) {
        Toast.makeText(RecycleActivity.this, "点击了第" + position + "条", Toast.LENGTH_SHORT).show();
        mItemAdapter.addData(position + 1, new Item(-1,"新增头条", "0w"));
    }

    @Override
    public void onItemLongClick(int position, Item data) {
        Toast.makeText(RecycleActivity.this, "长按了第" + position + "条", Toast.LENGTH_SHORT).show();
        mItemAdapter.removeData(position);
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mTvIndex;
        private TextView mTvTitle;
        private TextView mTvHot;
        private View mContentView;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            mContentView=itemView;
            mTvIndex=(TextView)itemView.findViewById(R.id.tv_index);
            mTvTitle=(TextView) itemView.findViewById(R.id.tv_title);
            mTvHot=(TextView)itemView.findViewById(R.id.tv_hot);
        }
        public void bindItem(Item item){
            mTvIndex.setText(item.getId()+".");
            mTvTitle.setText(item.getTitle());
            mTvHot.setText(item.getHotDegree());
            if(item.getId()<4){
                mTvIndex.setTextColor(Color.parseColor("#FFD700"));
            } else {
                mTvIndex.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }

        public void setOnClickListener(View.OnClickListener listener) {
            if (listener != null) {
                mContentView.setOnClickListener(listener);
            }
        }

        public void setOnLongClickListener(View.OnLongClickListener listener) {
            if (listener != null) {
                mContentView.setOnLongClickListener(listener);
            }
        }
    }



    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder>{
        private List<Item>mItems;
        private IOnItemClickListener mItemClickListener;

        public ItemAdapter(List<Item>items){
            this.mItems=items;
        }

        public void setOnItemClickListener(IOnItemClickListener listener) {
            mItemClickListener = listener;
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
              holder.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(mItemClickListener!=null){
                          mItemClickListener.onItemClick(position,item);
                      }
                  }
              });
              holder.setOnLongClickListener(new View.OnLongClickListener(){
                  @Override
                  public boolean onLongClick(View v) {
                      if (mItemClickListener != null) {
                          mItemClickListener.onItemLongClick(position, item);
                      }
                      return false;
                  }
              });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
        public void addData(int position, Item data) {
            mItems.add(position, data);
            notifyItemInserted(position);
            if (position != mItems.size()) {
                //刷新改变位置item下方的所有Item的位置,避免索引错乱
                notifyItemRangeChanged(position, mItems.size() - position);
            }
        }
        public void removeData(int position) {
            if (null != mItems && mItems.size() > position) {
                mItems.remove(position);
                notifyItemRemoved(position);
                if (position != mItems.size()) {
                    //刷新改变位置item下方的所有Item的位置,避免索引错乱
                    notifyItemRangeChanged(position, mItems.size() - position);
                }
            }
        }
    }
}
