package com.udithamelan.foodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.udithamelan.foodapp.entity.Product;
import com.udithamelan.foodapp.recyclervdisplay.RecipesResultsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipesViewArchive extends AppCompatActivity {
    RecyclerView rv_recipes_archive;
    RecipesResultsAdapter rAdapter;
    ArrayList<Product> prodarr;
    ArrayList<String> namesArr;
    ArrayList<String> titleArr;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_view_archive);

        rv_recipes_archive = (RecyclerView) findViewById(R.id.rv_recipes_archive);
        rv_recipes_archive.setHasFixedSize(true);
        rv_recipes_archive.setLayoutManager(new LinearLayoutManager(this));


        prodarr = new ArrayList<>();

        rAdapter = new RecipesResultsAdapter(this, prodarr);
        rv_recipes_archive.setAdapter(rAdapter);

        namesArr = getIntent().getStringArrayListExtra("productName");
        titleArr = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<namesArr.size();i++){
            String str = namesArr.get(i);
            String nameStr = str.replaceAll("\\s", "%");
            String nameStrtoLower = nameStr.toLowerCase();
            sb.append(nameStrtoLower);
            if (i == namesArr.size()-1){
                break;
            }else{
                sb.append(",");
            }
        }

        String url = "https://www.food2fork.com/api/search?key=b050b6e25e4f988089ac3c4de7475a68&q="+ sb +"&page=1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("recipes");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject recipes = jsonArray.getJSONObject(i);

                                String title = recipes.getString("title");

                                Product product = new Product();
                                product.setName(title);

                                prodarr.add(product);
                            }

                            rAdapter = new RecipesResultsAdapter(RecipesViewArchive.this,prodarr);

                            rv_recipes_archive.setAdapter(rAdapter);

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
        requestQueue.add(request);

    }


}
