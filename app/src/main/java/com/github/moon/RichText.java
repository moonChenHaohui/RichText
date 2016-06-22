package com.github.moon;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.moon.listener.OnTextViewClickListener;

import java.util.ArrayList;

/**
 * Created by moon on 15/11/29.
 */
public class RichText extends ScrollView {


    private OnTextViewClickListener onTextViewClickListener;
    private MoonHtmlRemoteImageGetter.Adapter imageLoadAdapter;
    private MoonHtmlRemoteImageGetter imageGetter;
    private MoonHtmlTagHandler tagHandler;
    private TextView mTextView;
    /**
     * all images url
     */
    private ArrayList<String> imageUrls;

    public RichText(Context context) {
        this(context, null);
        init(context);
    }

    public RichText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public RichText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mTextView = new TextView(context);
        mTextView.setLayoutParams(new ViewGroup.LayoutParams(-1,-1));
        if (null == imageGetter) {
            imageGetter = new MoonHtmlRemoteImageGetter(mTextView, null,imageLoadAdapter);
        }
        if (null == tagHandler) {
            tagHandler = new MoonHtmlTagHandler();
        }
        addView(mTextView);
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setTextView(TextView mTextView) {
        removeView(mTextView);
        this.mTextView = mTextView;
        addView(mTextView);
    }

    /**
     * set rich text
     *
     * @param text rich text
     */
    public RichText text(String text) {

        Spanned spanned = Html.fromHtml(text, imageGetter, tagHandler);
        SpannableStringBuilder spannableStringBuilder;
        if (spanned instanceof SpannableStringBuilder) {
            spannableStringBuilder = (SpannableStringBuilder) spanned;
        } else {
            spannableStringBuilder = new SpannableStringBuilder(spanned);
        }
        //hold image url link
        ImageSpan[] imageSpans = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ImageSpan.class);
        if (null == imageUrls) {
            imageUrls = new ArrayList<String>();
        } else {
            imageUrls.clear();
        }

        for (int i = 0, size = imageSpans.length; i < size; i++) {
            ImageSpan imageSpan = imageSpans[i];
            String imageUrl = imageSpan.getSource();
            int start = spannableStringBuilder.getSpanStart(imageSpan);
            int end = spannableStringBuilder.getSpanEnd(imageSpan);
            imageUrls.add(imageUrl);

            final int finalI = i;
            ImageClickSpan clickableSpan = new ImageClickSpan(imageUrl);
            clickableSpan.setIndex(finalI);
            clickableSpan.setListener(onTextViewClickListener);
            ClickableSpan[] clickableSpans = spannableStringBuilder.getSpans(start, end, ClickableSpan.class);
            if (clickableSpans != null && clickableSpans.length != 0) {
                for (ClickableSpan cs : clickableSpans) {
                    spannableStringBuilder.removeSpan(cs);
                }
            }
            spannableStringBuilder.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        //hold text url link
        URLSpan[] urls = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), URLSpan.class);
        if (null != urls && urls.length > 0) {
            for (URLSpan url : urls) {
                LinkClickSpan myURLSpan = new LinkClickSpan(url.getURL());
                myURLSpan.setListener(onTextViewClickListener);
                spannableStringBuilder.setSpan(myURLSpan, spannableStringBuilder.getSpanStart(url), spannableStringBuilder.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        mTextView.setText(spanned);
        Linkify.addLinks(mTextView, Linkify.WEB_URLS);
        return this;

    }

    public RichText textViewClickListener(OnTextViewClickListener onTextViewClickListener) {
        this.onTextViewClickListener = onTextViewClickListener;
        return this;
    }

    public RichText imageLoadAdapter(MoonHtmlRemoteImageGetter.Adapter imageLoadAdapter) {
        this.imageLoadAdapter = imageLoadAdapter;
        return this;
    }

    class ClickSpan extends ClickableSpan {

        protected String url;
        protected OnTextViewClickListener listener;

        public ClickSpan(String url) {
            this.url = url;
        }

        public OnTextViewClickListener getListener() {
            return listener;
        }

        public void setListener(OnTextViewClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View widget) {
            //Selection.setSelection((Spannable) ((TextView) widget).getText(), 0);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }
    }

    class LinkClickSpan extends ClickSpan {

        public LinkClickSpan(String url) {
            super(url);
        }

        @Override
        public void onClick(View widget) {
            super.onClick(widget);
            if (null != listener) {
                listener.textLinkClicked(url);
            }
        }
    }

    class ImageClickSpan extends ClickSpan {

        private int index = -1;

        public ImageClickSpan(String url) {
            super(url);
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View widget) {
            super.onClick(widget);
            if (null != listener) {
                listener.imageClicked(RichText.this.imageUrls, index);
            }
        }
    }
}
