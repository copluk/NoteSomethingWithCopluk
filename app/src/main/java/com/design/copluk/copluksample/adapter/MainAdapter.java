package com.design.copluk.copluksample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.design.copluk.copluksample.R;

import org.w3c.dom.Text;

/**
 * Created by copluk on 2017/4/26.
 */
public class MainAdapter extends AdapterBase<RecyclerView.ViewHolder, String> {

    public MainAdapter(Context context) {
        super(context);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_desktop, parent, false);

        view = checkShowView(viewType, view);

        ViewHolder holder = new ViewHolder(view);
        holder.txtName = (TextView) view.findViewById(R.id.txtName);
        return holder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == IS_HANDER || getItemViewType(position) == IS_FOOTER) {
            return;
        }

        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.txtName.setText(getData().get(getRealPosition(position)));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClick != null) {
                    mItemClick.onItemClicked(view, getRealPosition(viewHolder.getAdapterPosition()));
                }
            }
        });
    }

}
