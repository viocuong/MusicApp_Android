package com.example.nguyenvancuong_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.example.nguyenvancuong_project.adapter.NavAdapter;
import com.example.nguyenvancuong_project.fragment.PersonFragment;
import com.example.nguyenvancuong_project.model.Person;
import com.example.nguyenvancuong_project.singleton.VolleySingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private Person person;
    private BottomNavigationView nav;
    private ViewPager viewPager;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private NavAdapter navAdater; //adapter fragment
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
        initView();
    }
    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        nav = findViewById(R.id.nav);
        Intent intent = getIntent();
        this.person= (Person) intent.getSerializableExtra("person");
        this.mAuth = intent.getParcelableExtra("mAuth");
        navAdater = new NavAdapter(getSupportFragmentManager(),4);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        viewPager.setAdapter(navAdater);
        Bundle bundle = new Bundle();
        bundle.putSerializable("person", person);
        PersonFragment frag = new PersonFragment();
        frag.setArguments(bundle);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mHome:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.mSearch:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.mFavourite:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.mPerson:

                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });


    }
    public Person getPerson(){
        return this.person;
    }

}
