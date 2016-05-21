package com.example.chenhaohui.richtext.listener;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by chenhaohui on 16/5/21.
 */
public interface OnTextViewClickListener {
    /**
     * 图片被点击后的回调方法
     *
     * @param imageUrls 本篇富文本内容里的全部图片
     * @param position  点击处图片在imageUrls中的位置
     */
    void imageClicked(ArrayList<String> imageUrls, int position);

    void textLinkClicked(String url);
}