package com.example.nguyenvancuong_project.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nguyenvancuong_project.R;
import com.example.nguyenvancuong_project.Static;
import com.example.nguyenvancuong_project.adapter.MusicRankAdapter;
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
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewRankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewRankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MusicRankAdapter adapter;
    private RecyclerView rcv;
    private ArrayList<Music> musicList = new ArrayList<>();

    public ViewRankFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewRankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewRankFragment newInstance(String param1, String param2) {
        ViewRankFragment fragment = new ViewRankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMusic();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_view_rank, container, false);
        rcv = v.findViewById(R.id.rcv);
        Log.d("music fragment la",""+musicList.size());
        rcv.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        adapter = new MusicRankAdapter(getActivity(),musicList,getParentFragment());
        rcv.setAdapter(adapter);
        return v;
    }
    private void loadMusic(){
        StringRequest rq = new StringRequest(Request.Method.GET, Static.HOST+"/api/rankbyview", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("hhhhhhhhhhhh",response);
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
                        Music m = new Music(singer, category, music.getString("name"), music.getString("file_url"), music.getString("img_url"),music.getInt("view"));
                        musicList.add(m);
                        //System.out.println(m.getName()+" "+m.getImageUrl()+" "+m.getSinger().getName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("music fragment la",""+musicList.size());
                adapter = new MusicRankAdapter(getActivity(),musicList, getParentFragment());
                rcv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
                rcv.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(rq);
    }
}