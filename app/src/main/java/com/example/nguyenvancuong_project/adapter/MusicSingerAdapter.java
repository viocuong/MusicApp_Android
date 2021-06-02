package com.example.nguyenvancuong_project.adapter;

import android.app.Activity;
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

import java.util.ArrayList;

public class MusicSingerAdapter extends RecyclerView.Adapter<MusicSingerAdapter.MusicHolder> {
    private Activity activity;
    private ArrayList<Music> musicList;
    private Fragment fragment;
    public MusicSingerAdapter(Activity activity, ArrayList<Music> list, Fragment fragment){
        this.fragment = fragment;
        this.activity = activity;
        this.musicList = list;
    }
    @NotNull
    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MusicHolder(activity.getLayoutInflater().inflate(R.layout.item_music_singer,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
        Music music = musicList.get(position);
        Static.loadImage(activity,holder.img,"http://"+music.getImageUrl());
        holder.musicname.setText(music.getName());
        holder.singername.setText(music.getSinger().getName());
        holder.no.setText(""+(position+1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMusicFragment fragm = new PlayMusicFragment();
                Bundle args = new Bundle();
                args.putInt("music", position);
                args.putSerializable("listMusic",musicList);
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
        return musicList.size();
    }

    class MusicHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView singername, musicname, no;

        public MusicHolder(@NonNull View v) {
            super(v);
            img = v.findViewById(R.id.img);
            singername = v.findViewById(R.id.singername);
            musicname = v.findViewById(R.id.musicname);
            no = v.findViewById(R.id.no);
        }
    }
}
