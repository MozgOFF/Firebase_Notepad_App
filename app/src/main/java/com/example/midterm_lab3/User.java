package com.example.midterm_lab3;

class User {
    private String mUid;
    private String mName;
    private int age;

    public User(String uid, String name, int age) {
        mUid = uid;
        mName = name;
        this.age = age;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUid='" + mUid + '\'' +
                ", mName='" + mName + '\'' +
                ", age=" + age +
                '}';
    }
}
