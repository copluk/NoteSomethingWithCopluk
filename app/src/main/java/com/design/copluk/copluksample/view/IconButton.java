package com.design.copluk.copluksample.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.design.copluk.copluksample.R;


/**
 * Created by copluk on 2018/3/6.
 */

public class IconButton extends LinearLayout {
    private String mText;
    private Drawable mIcon;
    private boolean mHasRedDot = false;
    private int mRedDotCount;
    private TextView txtRedDot;

    public IconButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonMain);

        try {
            mText = typedArray.getString(R.styleable.ButtonMain_text);
            mIcon = typedArray.getDrawable(R.styleable.ButtonMain_src);
            mHasRedDot = typedArray.getBoolean(R.styleable.ButtonMain_redDot, false);
            mRedDotCount = typedArray.getInt(R.styleable.ButtonMain_redDotCount , -1);
        }finally {
            typedArray.recycle();
        }


        LayoutInflater.from(context).inflate(R.layout.btn_Icon, this);
        TextView txtFun = findViewById(R.id.txtFun);
        txtRedDot = findViewById(R.id.txtRedDot);
        ImageView imgFun = findViewById(R.id.imgFun);

        txtFun.setText(mText);
        txtRedDot.setVisibility(mHasRedDot & mRedDotCount > 0 ? VISIBLE : GONE);

        if (mIcon != null)
            imgFun.setImageDrawable(mIcon);

    }

    public void setRedDot(int number){
        mRedDotCount = number;
        txtRedDot.setVisibility(VISIBLE);
        txtRedDot.setText(String.valueOf(number));
    }

    public void setRedDot(String number){
        txtRedDot.setVisibility(VISIBLE);
        txtRedDot.setText(number);
    }




}
