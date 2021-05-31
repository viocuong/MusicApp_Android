package com.example.nguyenvancuong_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nguyenvancuong_project.model.Person;
import com.example.nguyenvancuong_project.model.ResultResponse;
import com.example.nguyenvancuong_project.singleton.VolleySingleton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GoogleActivity";
    private Button btnLogin;
    private ImageView btnLoginWithGoogle;
    private EditText txtUsername, txtPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initView();
        setOnListener();

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
            updateUI(currentUser);
    }
    private void setOnListener() {
        btnLogin.setOnClickListener(this);
        btnLoginWithGoogle.setOnClickListener(this);
    }

    private void initView() {
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginWithGoogle = findViewById(R.id.btnLoginWithGoogle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                break;
            case R.id.btnLoginWithGoogle:
                signIn();
                break;
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void updateUI(FirebaseUser user){
        if(user == null) return;
        this.user = user;
        //new setTask().execute();
        StringRequest sr = new StringRequest(Request.Method.POST, Static.HOST + "/api/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("success")) Log.d("ket qua", "thanh cong");
                else Log.d("ket qua", response);
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                //Log.d("urlphoto", ""+user.getPhotoUrl().toString());
                intent.putExtra("person",new Person(user.getUid(),user.getDisplayName(),user.getPhotoUrl().toString(),user.getEmail()));
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loi request","error");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> m = new HashMap<String, String>();
                m.put("uid",user.getUid());
                m.put("name",user.getDisplayName());
                m.put("password","1");
                m.put("email",user.getEmail());
                m.put("photo_url",user.getPhotoUrl().toString());
                return m;
            }
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(sr);
    }
    void toast(String m){
        Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();
    }
//    class setTask extends AsyncTask<Void, Integer, Void> {
//
//        private Exception exception;
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                URL url = new URL("http://192.168.1.10:8000/api/register");
//                HttpURLConnection con = (HttpURLConnection)url.openConnection();
//                con.setRequestMethod("POST");
//                con.setRequestProperty("Content-Type","application/json; utf-8");
//                con.setRequestProperty("Accept", "application/json");
//                con.setDoOutput(true);
//                String param = String.format("{\n" +
//                        "\t\"uid\":\"%s\",\n" +
//                        "\t\"name\":\"%s\",\n" +
//                        "\t\"password\":\"%s\",\n" +
//                        "\t\"email\":\"%s\",\n" +
//                        "\t\"photo_url\":\"%s\"\n" +
//                        "}",user.getUid(),user.getDisplayName(),"1",user.getEmail(),user.getPhotoUrl().toString());
//                Log.d("param la",param);
//                try(OutputStream os = con.getOutputStream()) {
//                    byte[] input = param.getBytes("utf-8");
//                    os.write(input, 0, input.length);
//                }
//                try(BufferedReader br = new BufferedReader(
//                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
//                    StringBuilder response = new StringBuilder();
//                    String responseLine = null;
//                    while ((responseLine = br.readLine()) != null) {
//                        response.append(responseLine.trim());
//                    }
////                    if(response.toString().contains("success")){
////                        Log.d("ket qua", "thanh cong");
////                    }
////                    else Log.d("ket qua","that bai");
//////                    Log.d("response1", response.toString());
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//        }
//    }
}