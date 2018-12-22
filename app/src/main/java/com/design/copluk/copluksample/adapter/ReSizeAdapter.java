package com.design.copluk.copluksample.adapter;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.design.copluk.copluksample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by copluk on 2017/4/26.
 */
public class ReSizeAdapter extends RecyclerView.Adapter<ReSizeAdapter.ViewHolder> {

    private ItemClickListener mItemClickListener;
    private Context mContext;
    private int mSpanCount;
    private double mRowCount = 0;
    private double mHeight;
    protected List<String> mDataList = new ArrayList<>();

    public ReSizeAdapter(Context context , int spanCount){
        mContext = context;
        mSpanCount = spanCount;

        for (int i = 0 ; i < 6 ; i++){
            mDataList.add("" + i);
        }

        if(getItemCount() > 3){
            mRowCount = Math.ceil(((double) getItemCount() /(double) mSpanCount));
            Log.d("getItemCount()" , "getItemCount() : " + getItemCount());
            Log.d("mRowCount" , "mRowCount : " + mRowCount);
        }else {
            mRowCount = mDataList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView desktopIcon;
        public TextView desktopName;
        public LinearLayout itemBox;

        public ViewHolder(View itemView){
            super(itemView);
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        mItemClickListener = itemClickListener;
    }

    public ReSizeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_desktop, parent, false);

//        mHeight = (parent.getMeasuredHeight() - (9 * (mRowCount + 1))) / mRowCount ;
        mHeight = parent.getMeasuredHeight() / mRowCount ;

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        params.height = (int) Math.floor(mHeight);
        view.setLayoutParams(params);

        ViewHolder holder = new ViewHolder(view);

        holder.desktopIcon = (ImageView) view.findViewById(R.id.imgBtnIcon);
        holder.desktopName = (TextView) view.findViewById(R.id.txtName);
        holder.itemBox = (LinearLayout) view.findViewById(R.id.itemBox);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ReSizeAdapter.ViewHolder holder, int position) {
//        holder.desktopIcon.setImageResource(mDataList.get(position).getResourceID());
        holder.desktopName.setText(mDataList.get(position));

        holder.itemBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mItemClickListener != null){
                    mItemClickListener.onItemClicked(view , holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
