package com.example.nguyenvancuong_project.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nguyenvancuong_project.R;
import com.example.nguyenvancuong_project.Static;
import com.example.nguyenvancuong_project.fragment.PlayMusicFragment;
import com.example.nguyenvancuong_project.model.Music;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {
    private Activity activity;
    private Fragment fragment;
    private ArrayList<Music> mList;
    public MusicAdapter(Activity activity, ArrayList<Music> list, Fragment fragment){
        this.fragment = fragment;
        this.activity = activity;
        this.mList = list;
    }
    @NonNull
    @Override
    public MusicHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new MusicHolder(activity.getLayoutInflater().inflate(R.layout.item_music_rcv,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        Music music = mList.get(position);
        holder.music.setText(music.getName());
        holder.singer.setText(music.getSinger().getName());
        Static.loadImage(activity,holder.img,"http://"+music.getImageUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMusicFragment fragm = new PlayMusicFragment();
                Bundle args = new Bundle();
                args.putInt("music", position);
                args.putSerializable("listMusic",mList);
                fragm.setArguments(args);
                FragmentManager fragmentManager = fragment.getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in,R.anim.slide_out);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.replace(R.id.main_frame,fragm);
                fragmentTransaction.addToBackStack("playmusic");
                fragmentTransaction.commit();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    class MusicHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView music,singer;
        public MusicHolder(@NonNull @NotNull View v) {
            super(v);
            img = v.findViewById(R.id.img);
            music = v.findViewById(R.id.music);
            singer = v.findViewById(R.id.singer);
        }
    }

}