package com.example.nguyenvancuong_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.example.nguyenvancuong_project.fragment.FavouriteFragment;
import com.example.nguyenvancuong_project.fragment.HomeFragment;
import com.example.nguyenvancuong_project.fragment.PersonFragment;
import com.example.nguyenvancuong_project.fragment.SearchFragment;
import com.example.nguyenvancuong_project.model.Person;
import com.example.nguyenvancuong_project.singleton.VolleySingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Person person;
    private BottomNavigationView nav;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private RequestQueue queue;
    private final HomeFragment homeFragment = new HomeFragment();
    private final SearchFragment searchFragment = new SearchFragment();
    private final FavouriteFragment favouriteFragment= new FavouriteFragment();
    private final PersonFragment personFragment = new PersonFragment();
    private FragmentManager fm = getSupportFragmentManager();
    private Fragment active = homeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
        initView();
    }
    private void initView() {

        nav = findViewById(R.id.nav);
        Intent intent = getIntent();
        this.person= (Person) intent.getSerializableExtra("person");
        this.mAuth = intent.getParcelableExtra("mAuth");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        initFragmentsNav();
        Bundle bundle = new Bundle();
        bundle.putSerializable("person", person);
        PersonFragment frag = new PersonFragment();
        frag.setArguments(bundle);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mHome:
                        fm.beginTransaction().hide(active).show(homeFragment).commit();
                        active = homeFragment;
//                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.mSearch:
                        fm.beginTransaction().hide(active).show(searchFragment).commit();
                        active = searchFragment;
//                        viewPager.setCurrentItem(1);
//                        break;
                    case R.id.mFavourite:
                        fm.beginTransaction().hide(active).show(favouriteFragment).commit();
                        active = favouriteFragment;
//                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.mPerson:
                        fm.beginTransaction().hide(active).show(personFragment).commit();
                        active= personFragment;
//                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });


    }
    private void initFragmentsNav(){
        fm.beginTransaction().add(R.id.main_frame,homeFragment,"1").commit();
        fm.beginTransaction().add(R.id.main_frame,searchFragment,"2").hide(searchFragment).commit();
        fm.beginTransaction().add(R.id.main_frame,favouriteFragment,"3").hide(favouriteFragment).commit();
        fm.beginTransaction().add(R.id.main_frame,personFragment,"4").hide(personFragment).commit();

    }
    public Person getPerson(){
        return this.person;
    }

}
