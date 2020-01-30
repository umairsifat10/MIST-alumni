package com.example.imtiaz.mist;

public class PostInformation {
    String post,username,time;


    public PostInformation(String post, String username,String time) {
        this.post = post;
        this.username = username;
        this.time=time;
    }

    public PostInformation() {
    }

    public String getPost() {
        return post;
    }

    public String getUsername() { return username; }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}