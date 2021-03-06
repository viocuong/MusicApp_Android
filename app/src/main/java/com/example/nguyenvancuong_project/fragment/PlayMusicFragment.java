package com.example.nguyenvancuong_project.fragment;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nguyenvancuong_project.R;
import com.example.nguyenvancuong_project.Static;
import com.example.nguyenvancuong_project.model.Music;
import com.example.nguyenvancuong_project.singleton.VolleySingleton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayMusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayMusicFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Toolbar toolbar;
    private ImageView musicImg;
    private TextView musicName, singerName;
    private ImageButton btnFavourite, btnShuffle, btnPrev, btnPlayPause, btnNext, btnRepeat;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private int cur = 0;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ArrayList<Music> listMusic;
    public PlayMusicFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayMusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayMusicFragment newInstance(String param1, String param2) {
        PlayMusicFragment fragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        cur = getArguments().getInt("music");
        listMusic = (ArrayList<Music>) getArguments().getSerializable("listMusic");
        view(listMusic.get(cur).getName());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private void view(String name){
        StringRequest rq = new StringRequest(Request.Method.POST, Static.HOST+"/api/view", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("phan hoi like", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> m = new HashMap<>();
                m.put("name",name);
                return m;
            }
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(rq);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_play_music, container, false);
        initView(v);
        btnPlayPause.setImageResource(R.drawable.ic_pause);
        handler = new Handler();
        seekBar.setMax(100);

        playMusic(listMusic.get(cur));
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cur<listMusic.size()-1){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    playMusic(listMusic.get(++cur));
                }
            }
        });
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                getParentFragmentManager().popBackStack();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cur>0){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    playMusic(listMusic.get(--cur));
                }
            }
        });
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(update);
                    mediaPlayer.pause();
                    btnPlayPause.setImageResource(R.drawable.ic_play);
                }
                else{
                    mediaPlayer.start();
                    btnPlayPause.setImageResource(R.drawable.ic_pause);
                    updateSeekbar();
                }
            }
        });
        return v;
    }
    private void playMusic(Music music){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource("http://"+music.getUrl());
            mediaPlayer.prepare();
//            Log.d("do dai",""+mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();

        updateSeekbar();
        Static.loadImage(getActivity(),musicImg,"http://"+music.getImageUrl());
        musicName.setText(music.getName());
        singerName.setText(music.getSinger().getName());
    }
    private Runnable update = new Runnable() {
        @Override
        public void run() {
            updateSeekbar();
        }
    };
    private void updateSeekbar(){
        if(mediaPlayer.isPlaying()){
            double c = mediaPlayer.getCurrentPosition()/(1.0*mediaPlayer.getDuration());
            int current = (int)(c*100);
            seekBar.setProgress(current);
            handler.postDelayed(update,1000);
        }
    }
    private void initView(View v) {
        toolbar = v.findViewById(R.id.topBar);
        musicImg = v.findViewById(R.id.musicImg);
        musicName = v.findViewById(R.id.musicName);
        singerName = v.findViewById(R.id.singerName);
        btnFavourite = v.findViewById(R.id.btnFavourite);
        btnPrev = v.findViewById(R.id.btnPrevious);
        btnPlayPause = v.findViewById(R.id.btnPlayPause);
        btnNext = v.findViewById(R.id.btnNext);
        btnRepeat = v.findViewById(R.id.btnRepeat);
        seekBar = v.findViewById(R.id.seekBar);
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                if(btnFavourite.getTransitionName().equals("disable")){
                    View view = v.findViewById(R.id.parent);
                    String s= "???? th??ch"  +listMusic.get(cur).getName();
                    favourite(user.getUid(),listMusic.get(cur).getName());
                    Snackbar.make(view, s, Snackbar.LENGTH_LONG).show();
                    btnFavourite.setTransitionName("enable");
                    btnFavourite.setImageResource(R.drawable.ic_favaroutie_enable);
                }
                else if(btnFavourite.getTransitionName().equals("enable")){
                    btnFavourite.setTransitionName("disable");
                    btnFavourite.setImageResource(R.drawable.ic_border);
                }
            }
        });
    }
    private void favourite(String uid, String musicName){
        StringRequest rq = new StringRequest(Request.Method.POST, Static.HOST+"/api/favourite", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("phan hoi thich", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> m = new HashMap<>();
                m.put("person_uid",uid);
                m.put("music_name",musicName);
                return m;
            }
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(rq);
    }
}