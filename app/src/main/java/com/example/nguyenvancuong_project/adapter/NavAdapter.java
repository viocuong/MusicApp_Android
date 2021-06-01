package com.example.nguyenvancuong_project.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.nguyenvancuong_project.fragment.FavouriteFragment;
import com.example.nguyenvancuong_project.fragment.HomeFragment;
import com.example.nguyenvancuong_project.fragment.PersonFragment;
import com.example.nguyenvancuong_project.fragment.RootHomFragment;
import com.example.nguyenvancuong_project.fragment.SearchFragment;
import com.example.nguyenvancuong_project.model.Person;

import org.jetbrains.annotations.NotNull;

public class NavAdapter extends FragmentStatePagerAdapter {
    private int numPage = 4;

    public NavAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new RootHomFragment();
            case 1: return new SearchFragment();
            case 2: return new FavouriteFragment();
            case 3: return new PersonFragment();
            default: return new RootHomFragment();
        }
    }

    @Override
    public int getCount() {
        return numPage;
    }
}
