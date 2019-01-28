package com.design.copluk.copluksample.util;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.design.copluk.copluksample.R;


/**
 * Created by copluk on 2018/3/8.
 */

public class AlertUtil {

    public static void showDialog(Context context, String title, String content,
                                  String btnLeftName, String btnRightName, final OnButtonClick onClick ,
                                  boolean touchClose) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_view, null);

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view).create();

        TextView txtDialogTitle = view.findViewById(R.id.txtDialogTitle);
        TextView txtDialogContent = view.findViewById(R.id.txtDialogContent);
        Button btnDialogLeft = view.findViewById(R.id.btnDialogLeft);
        Button btnDialogRight = view.findViewById(R.id.btnDialogRight);

        txtDialogTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        txtDialogTitle.setText(title);
        txtDialogContent.setText(content);
        btnDialogLeft.setText(btnLeftName);
        btnDialogLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null) {
                    onClick.onLeftButtonClick(dialog);
                }
            }
        });
        btnDialogRight.setText(btnRightName);
        btnDialogRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClick != null) {
                    onClick.onRightButtonClick(dialog);
                }
            }
        });

        dialog.setCancelable(touchClose);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }

    public interface OnButtonClick {
        void onLeftButtonClick(AlertDialog dialog);

        void onRightButtonClick(AlertDialog dialog);
    }
}
