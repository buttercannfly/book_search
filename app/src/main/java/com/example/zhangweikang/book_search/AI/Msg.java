package com.example.zhangweikang.book_search.AI;

public class Msg {
    static final int TYPE_RECEIVE = 1;
    static final int TYPE_SEND = 2;
    String content;
    int type;

    Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }
}
