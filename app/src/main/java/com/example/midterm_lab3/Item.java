package com.example.midterm_lab3;

public class Item {

    private String mTitle;
    private String mContent;
    private String mPriority;
    private String mItemId;

    public Item() {
    }

    public Item(String mItemId, String mTitle, String mContent, String mPriority) {
        this.mItemId = mItemId;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mPriority = mPriority;
    }

    public String getmItemId() {
        return mItemId;
    }

    public void setmItemId(String mItemId) {
        this.mItemId = mItemId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmPriority() {
        return mPriority;
    }

    public void setmPriority(String mPriority) {
        this.mPriority = mPriority;
    }
}
