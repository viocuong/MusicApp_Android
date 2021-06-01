package com.example.nguyenvancuong_project.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nguyenvancuong_project.R;
import com.example.nguyenvancuong_project.Static;
import com.example.nguyenvancuong_project.model.Singer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerHolder> {
    private Activity activity;
    private ArrayList<Singer> singers;
    public SingerAdapter(Activity activity, ArrayList<Singer> list){
        this.activity = activity;
        this.singers = list;
    }
    @NotNull
    @Override
    public SingerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new SingerHolder(activity.getLayoutInflater().inflate(R.layout.item_singer,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SingerHolder holder, int position) {
        Singer singer = singers.get(position);
        Static.loadImageCircle(activity,holder.img,"http://"+singer.getImg());
        holder.name.setText(singer.getName());
        Bundle data = new Bundle();
        data.putSerializable("singer",singer);
        //FragmentManager fragmentManager = activity.getFragmentManager()


    }

    @Override
    public int getItemCount() {
        return singers.size();
    }

    class SingerHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name;
        public SingerHolder(@NonNull View v) {
            super(v);
            img=v.findViewById(R.id.img);
            name=v.findViewById(R.id.singerName);
        }
    }
}
