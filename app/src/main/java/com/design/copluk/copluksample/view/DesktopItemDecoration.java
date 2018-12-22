package com.design.copluk.copluksample.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.design.copluk.copluksample.R;


/**
 * Created by copluk on 2017/3/23.
 */

public class DesktopItemDecoration extends RecyclerView.ItemDecoration {
    private int mLineSize;
    private final ColorDrawable mDivider;
    private Context mContext;

    public DesktopItemDecoration(int color , int lineSize , Context context){

        mDivider = new ColorDrawable(color);
        mLineSize = lineSize;
        mContext = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pixel = (int) mContext.getResources().getDimension(R.dimen.main_padding);

        int left = pixel  , right = pixel , top = pixel  , bottom = pixel ;

        final double itemPosition = parent.getChildAdapterPosition(view) +1;
        int count = parent.getAdapter().getItemCount();
        double spancount = getSpanCount(parent , count);
        double colCount = Math.ceil(count / spancount);
        double rowCount = Math.ceil(count / colCount);

        if(itemPosition % rowCount == 0)
        {
            left = pixel /2;
        }else {
            right = pixel /2;
        }

        double rowItem = Math.ceil( itemPosition /  spancount);

        if(rowItem == 1)
        {
            bottom = pixel/2;
        }else if(rowItem == colCount)
        {
            top = pixel/2;
        }else {
            top = pixel/2;
            bottom = pixel/2;
        }

//        view.setPadding( pixel , pixel , pixel , pixel);
        view.setPadding(left , top , right , bottom);


    }

//    @Override
//    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
//
//        drawHorizontal(c, parent);
//        drawVertical(c, parent);
//    }




    /**
     * 畫橫線
     * 一次畫一列，最後一列不畫。
     */
    public void drawHorizontal(Canvas c, RecyclerView parent)
    {
        int childCount = parent.getChildCount();
        double spancount = getSpanCount(parent , childCount);
        double colCount = Math.ceil(childCount / spancount);

        for (int i = 0; i < colCount -1; i ++)
        {
            double viewCount = i*spancount;
            final View child = parent.getChildAt((int) viewCount);
            final int left = parent.getLeft();
            final int right = parent.getRight();
            final int top = child.getBottom() ;
            final int bottom = top + mLineSize;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 畫直線
     * 一次畫一行，最後一行不畫。
     */
    public void drawVertical(Canvas c, RecyclerView parent)
    {
        final int childCount = parent.getChildCount();
        double spancount = getSpanCount(parent , childCount);
        double colCount = Math.ceil(childCount / spancount);
        double rowCount = Math.ceil(childCount / colCount);

        for (int i = 0; i < rowCount -1; i ++)
        {

            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            double bottom = child.getBottom() * colCount + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mLineSize;

            mDivider.setBounds(left, top, right, (int) bottom);
            mDivider.draw(c);
        }
    }

    private int getSpanCount(RecyclerView parent , int count)
    {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager)
        {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }

        if (count <= 3)
        {
            spanCount = 1;
        }

        return spanCount;
    }

}
