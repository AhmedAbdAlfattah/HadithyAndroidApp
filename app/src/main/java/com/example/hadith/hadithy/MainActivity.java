package com.example.hadith.hadithy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<String>responseList;
    ResponseAdapter responseAdapter;
    private Button searchButton;
    private EditText searchField;
    String longReponse;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseList = new ArrayList<>();

        // instantiate recycler view
        searchButton = findViewById(R.id.search_button);
        searchField = findViewById(R.id.search_word_text);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (responseAdapter != null) {
                    responseAdapter.clearData();
                }
                String searchWord = searchField.getText().toString();
                parseJson(searchWord);
            }
        });


    }

    private void parseJson(String searchWord) {
        String url = "https://dorar.net/dorar_api.json?skey=";
        String param;
        try {
            param = URLEncoder.encode(searchWord, "UTF-8");
            url += param;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("ahadith");
                    String another = jsonObject.getString("result");
                    String[] arrayofStrings = another.split("--------------");
                    returnArrayList(another);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void returnArrayList(String another) {
        String[] arrayofStrings = another.split("--------------");
        for (String e: arrayofStrings) {
            responseList.add(e);
        }
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        responseAdapter = new ResponseAdapter(this, responseList);
        recyclerView.setAdapter(responseAdapter);
    }
}
