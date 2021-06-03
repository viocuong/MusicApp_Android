package com.example.nguyenvancuong_project.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

public class MusicRankAdapter extends RecyclerView.Adapter<MusicRankAdapter.MusicHolder> {

    private Activity activity;
    private ArrayList<Music> musicList;
    private Fragment fragment;
    public MusicRankAdapter(Activity activity, ArrayList<Music> list, Fragment fragment){
        this.fragment = fragment;
        this.activity = activity;
        this.musicList = list;

    }
    @NotNull
    @Override
    public MusicRankAdapter.MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MusicRankAdapter.MusicHolder(activity.getLayoutInflater().inflate(R.layout.item_view_rank,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull MusicRankAdapter.MusicHolder holder, int position) {
        Music music = musicList.get(position);
        Log.d("music fragment la","music.getName()");
        Static.loadImage(activity,holder.img,"http://"+music.getImageUrl());
        holder.musicname.setText(music.getName());
        holder.singername.setText(music.getSinger().getName());
        holder.view.setText(""+music.getView());
        final float scale = activity.getResources().getDisplayMetrics().density;
        int pixels = (int) (35 * scale + 0.5f);
        holder.no.setText("");
        if(position==0){
            holder.noImg.setImageResource(R.drawable.ic_top1);
            ViewGroup.LayoutParams p= holder.noImg.getLayoutParams();
            p.width = pixels;
            p.height = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.view.requestLayout();
        }
        else if(position==1){
            holder.noImg.setImageResource(R.drawable.ic_top2);
            ViewGroup.LayoutParams p= holder.noImg.getLayoutParams();
            p.width = pixels;
            p.height = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.view.requestLayout();
        }
        else if(position==2){
            holder.noImg.setImageResource(R.drawable.ic_top3);
            ViewGroup.LayoutParams p= holder.noImg.getLayoutParams();
            p.width = pixels;
            p.height = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.view.requestLayout();
        }
        else {
            holder.no.setText(""+(position+1));
            ViewGroup.LayoutParams p= holder.no.getLayoutParams();
            p.width = pixels;
            p.height = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.no.requestLayout();
        }
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
        ImageView img, noImg;
        TextView singername, musicname, no, view;
        public MusicHolder(@NonNull View v) {
            super(v);
            view = v.findViewById(R.id.view);
            img = v.findViewById(R.id.img);
            singername = v.findViewById(R.id.singername);
            musicname = v.findViewById(R.id.musicname);
            no = v.findViewById(R.id.no);
            noImg= v.findViewById(R.id.noImg);
        }
    }
}
