package com.design.copluk.copluksample.model;

public class ClickItem {

    public String title;
    public Class activity;

    public ClickItem(String _title , Class _activity){
        this.title = _title;
        this.activity = _activity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
