package com.github.moon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.net.URI;

/**
 * Created by moon on 15/11/27.
 */
public class HtmlRemoteImageGetter implements Html.ImageGetter {
    final TextView container;
    URI baseUri;
    final Adapter adapter;
    public static abstract class Adapter {
        /**
         * the replace Image
         * @return
         */
        public abstract Drawable getDefaultDrawable();

        /**
         * the error Image
         * @return
         */
        public abstract Drawable getErrorDrawable();

    }

    public HtmlRemoteImageGetter(TextView t, String baseUrl,Adapter adapter) {
        this.container = t;
        this.adapter = adapter;
        if (baseUrl != null) {
            this.baseUri = URI.create(baseUrl);
        }
    }

    public Drawable getDrawable(String source) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        final Drawable defaultImage;
        if (null != adapter && (defaultImage = adapter.getDefaultDrawable())!=null){
            urlDrawable.setDrawable(defaultImage, container.getMeasuredWidth() - container.getPaddingLeft() - container.getPaddingRight());
        }
        /**
         * use volley
         */
        final ImageRequest imageRequest = new ImageRequest(
                source,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        //response.getWidth()
                        //Log.d("DRAWABLE","download size:" + response.getWidth()+ "," + response.getHeight());
                        Drawable drawable = new BitmapDrawable(response);
                        urlDrawable.setDrawable(drawable, container.getMeasuredWidth() - container.getPaddingLeft() - container.getPaddingRight());
                        //container.invalidate();
                        container.setText(container.getText());
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final Drawable errorImage;
                if (null != adapter && (errorImage = adapter.getErrorDrawable())!=null){
                    urlDrawable.setDrawable(errorImage, container.getMeasuredWidth() - container.getPaddingLeft() - container.getPaddingRight());
                }
            }
        });
        Volley.newRequestQueue(container.getContext()).add(imageRequest);

        return urlDrawable;
    }


    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Drawable drawable;

        private void setDrawable(Drawable nDrawable, int maxWidth) {

            if (drawable != null) {
                drawable = null;
            }

            Log.d("DRAWABLE","get size:" + nDrawable.getIntrinsicWidth()+ "," + nDrawable.getIntrinsicHeight());
            //XLog.d("htmlremote: width:" + width);
            drawable = nDrawable;

            int width_dp = nDrawable.getIntrinsicWidth();
            int height_dp = nDrawable.getIntrinsicHeight();
            int width_px = DensityUtil.dip2px(container.getContext(),width_dp);
            int height_px = DensityUtil.dip2px(container.getContext(),height_dp);
            //too large,adapter screen,but the maxwidth can be zero.
            if (maxWidth > 0) {
                height_px = width_px > maxWidth ? height_px * (width_px / maxWidth) : height_px;
                width_px = width_px > maxWidth ? maxWidth : width_px;
            }
            drawable.setBounds(0,0,width_px,height_px);
            setBounds(0,0,width_px,height_px);


        }

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }


    }

}
