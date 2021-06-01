package com.example.nguyenvancuong_project;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.nguyenvancuong_project.singleton.VolleySingleton;

import de.hdodenhof.circleimageview.CircleImageView;

public class Static {
    public static String HOST="http://cackythuatgiautinn5.xyz:8001";
    public static void loadImage(Context context, ImageView img, String url){
        ImageLoader imageLoader = VolleySingleton.getInstance(context).getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if(response.getBitmap()!=null){
                    img.setImageBitmap(response.getBitmap());
                }
            }
            public void onErrorResponse(VolleyError error) {

            }
        });
    }public static void loadImageCircle(Context context, CircleImageView img, String url){
        ImageLoader imageLoader = VolleySingleton.getInstance(context).getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if(response.getBitmap()!=null){
                    img.setImageBitmap(response.getBitmap());
                }
            }
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
