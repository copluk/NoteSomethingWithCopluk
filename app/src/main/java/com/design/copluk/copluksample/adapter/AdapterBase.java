package com.design.copluk.copluksample.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by copluk on 2017/5/5.
 */

abstract class AdapterBase<VH extends RecyclerView.ViewHolder,TYPE> extends RecyclerView.Adapter<VH> {



    private Context mContext;
    private List<TYPE> mData;
    private List<TYPE> mShowData;
    public ItemClickListener mItemClick;

    public AdapterBase(Context context){
        this.mContext = context;
        this.mData = new ArrayList<>();
        this.mShowData = new ArrayList<>(mData);
        handerView = new View(mContext);
        footerView = new View(mContext);
    }

    public Context getContext(){
        return mContext;
    }

    public List<TYPE> getData(){
        return mShowData;
    }

    public void setData(List<TYPE> datas){
        this.mData.clear();
        if(datas != null){
            this.mData.addAll(datas);
        }
        clearFilter();
    }

    public void setFilter(List<TYPE> datas){
        this.mShowData.clear();
        if(datas != null){
            this.mShowData.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void clearFilter(){
        this.mShowData.clear();
        this.mShowData = new ArrayList<>(mData);
        notifyDataSetChanged();
    }

    public void setItemClick(ItemClickListener itemClick){
        this.mItemClick = itemClick;
    }

    private boolean hasHander = false;
    private boolean hasFooter = false;
    protected final int IS_HANDER = 789;
    protected final int IS_FOOTER = 987;
    protected View handerView ;
    protected View footerView ;

    public int getItemCount() {
        int count;

        count = mShowData.size();

        if(hasFooter)
            count = count +1;
        if(hasHander)
            count = count +1;

        return count;
    }


    @Override
    public int getItemViewType(int position) {
        if(hasHander && position == 0)
            return IS_HANDER;

        if(hasFooter && getItemCount() == position){
            return IS_FOOTER;
        }

        return 0;
    }

    public void setHander(View view){
        hasHander = true;
        handerView = view;
    }

    public void setHasFooter(View view){
        hasFooter = true;
        footerView = view;
    }

    public boolean isHasHander() {
        return hasHander;
    }

    public boolean isHasFooter() {
        return hasFooter;
    }

    public int getRealPosition(int position) {
        if (hasHander) {
            position--;
        }
        return position;
    }

    public View checkShowView(int viewType , View view){

        switch (viewType){
            case IS_HANDER:
                return handerView;
            case IS_FOOTER:
                return footerView;
        }

        return view;
    }


}
