package com.example.nguyenvancuong_project.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.nguyenvancuong_project.fragment.FavouriteRankFragment;
import com.example.nguyenvancuong_project.fragment.ViewRankFragment;

import org.jetbrains.annotations.NotNull;

public class RankTabAdapter extends FragmentStatePagerAdapter {
    public RankTabAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ViewRankFragment();
            case 1: return new FavouriteRankFragment();
            default: return new ViewRankFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:return "Lượt nghe";
            case 1: return "Lượt yêu thích";
            default: return "Lượt nghe";
        }
    }
}
