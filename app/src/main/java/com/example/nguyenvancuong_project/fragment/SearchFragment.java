package com.example.nguyenvancuong_project.fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nguyenvancuong_project.MainActivity;
import com.example.nguyenvancuong_project.R;
import com.example.nguyenvancuong_project.Static;
import com.example.nguyenvancuong_project.adapter.MusicSingerAdapter;
import com.example.nguyenvancuong_project.model.Category;
import com.example.nguyenvancuong_project.model.Music;
import com.example.nguyenvancuong_project.model.Singer;
import com.example.nguyenvancuong_project.singleton.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnSearch;
    private EditText txtSearch;
    private SearchView.OnQueryTextListener queryTextListener;
    private RecyclerView rcv;
    private ArrayList<Music> musicList = new ArrayList<>();
    private MusicSingerAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        btnSearch = v.findViewById(R.id.btnSearch);
        txtSearch = v.findViewById(R.id.txtSearch);
        rcv = v.findViewById(R.id.rcv);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        adapter = new MusicSingerAdapter(getActivity(),musicList,SearchFragment.this);
        rcv.setAdapter(adapter);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicList = new ArrayList<>();
                rcv.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
                loadMusic(txtSearch.getText().toString());

                adapter = new MusicSingerAdapter(getActivity(),musicList,SearchFragment.this);
                rcv.setAdapter(adapter);
                Log.d("okkkk",""+musicList.size());

            }
        });
        return v;
    }
    private void loadMusic(String name){

        StringRequest rq = new StringRequest(Request.Method.POST, Static.HOST+"/api/search", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String n= null;
                try {
                    n = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                JSONArray res=null;

                try {
                    res= new JSONArray(n);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.d("ket qua la",""+response+" "+res.length());

                for (int i = 0; i < res.length(); i++) {
                    try {
                        JSONObject music = res.getJSONObject(i);

                        Singer singer = new Singer(music.getString("singer_name"), "http://" + music.getString("singer_url"), music.getString("singer_dob"));
                        Category category = new Category(music.getString("category_name"));
                        Music m = new Music(singer, category, music.getString("name"), music.getString("file_url"), music.getString("img_url"));
                        musicList.add(m);
                        //System.out.println(m.getName()+" "+m.getImageUrl()+" "+m.getSinger().getName());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new MusicSingerAdapter(getActivity(),musicList, SearchFragment.this);
                rcv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
                rcv.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> m = new HashMap<>();
                m.put("key",name);
                return m;
            }
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(rq);
    }

}