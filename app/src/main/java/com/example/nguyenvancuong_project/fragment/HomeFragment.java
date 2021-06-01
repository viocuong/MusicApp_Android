package com.example.nguyenvancuong_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.nguyenvancuong_project.R;
import com.example.nguyenvancuong_project.Static;
import com.example.nguyenvancuong_project.adapter.MusicAdapter;
import com.example.nguyenvancuong_project.adapter.SingerAdapter;
import com.example.nguyenvancuong_project.model.Category;
import com.example.nguyenvancuong_project.model.Music;
import com.example.nguyenvancuong_project.model.Singer;
import com.example.nguyenvancuong_project.singleton.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rcv;
    private MusicAdapter adapter;
    private ArrayList<Music> musicList = new ArrayList<>();

    private RecyclerView singerRcv;
    private SingerAdapter singerAdapter;
    private ArrayList<Singer> singers = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSinger();
        loadMusic();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private void loadMusic(){
        JsonArrayRequest rq = new JsonArrayRequest(Static.HOST + "/api/musics", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject music = response.getJSONObject(i);
                        Singer singer = new Singer(music.getString("singer_name"),"http://"+music.getString("singer_url"),music.getString("singer_dob"));
                        Category category = new Category(music.getString("category_name"));
                        Music m = new Music(singer,category,music.getString("name"),music.getString("file_url"),music.getString("img_url"));
                        musicList.add(m);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new MusicAdapter(getActivity(),musicList,HomeFragment.this);
                rcv.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                rcv.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(rq);
    }
    private void loadSinger(){
        JsonArrayRequest rq = new JsonArrayRequest(Static.HOST+"/api/singers", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject s = response.getJSONObject(i);
                        String name = s.getString("name");
                        String url = s.getString("img_url");
                        System.out.println(url);
                        String dob = s.getString("dob");
                        singers.add(new Singer(name,url,dob));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                singerAdapter = new SingerAdapter(getActivity(),singers);
                singerRcv.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
                singerRcv.setAdapter(singerAdapter);
                singerAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(rq);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        //load music
        rcv = v.findViewById(R.id.rcv_music1);
        adapter = new MusicAdapter(getActivity(),musicList,HomeFragment.this);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        rcv.setAdapter(adapter);
        Log.d("oncreate","view"+musicList.size());
        //load singer
        singerRcv = v.findViewById(R.id.rcv_singer);
        singerAdapter = new SingerAdapter(getActivity(),singers);
        singerRcv.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL, false));
        singerRcv.setAdapter(singerAdapter);
        return v;
    }
}