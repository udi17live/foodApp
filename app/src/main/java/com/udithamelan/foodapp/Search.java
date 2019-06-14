package com.udithamelan.foodapp;

import android.app.FragmentTransaction;
import android.database.Cursor;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.udithamelan.foodapp.database.ProductDBHelper;
import com.udithamelan.foodapp.entity.Product;
import com.udithamelan.foodapp.recyclervdisplay.EditProductAdapter;
import com.udithamelan.foodapp.recyclervdisplay.SearchAdapter;

public class Search extends AppCompatActivity {
    ProductDBHelper productDB;
    Product product = new Product();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;
    ImageButton ib_search;
    EditText et_searchQuery;
    String searchQuery;
    SearchFragment sfrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        productDB = new ProductDBHelper(this);


        ib_search = (ImageButton) findViewById(R.id.ib_search);
        et_searchQuery = (EditText) findViewById(R.id.et_search);

        /*recyclerView = (RecyclerView) findViewById(R.id.rv_searchDisplay);
        searchQuery = et_searchQuery.getText().toString();
        layoutManager = new LinearLayoutManager(Search.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Cursor cursor = productDB.searchResult(searchQuery);
        adapter = new SearchAdapter(Search.this, cursor);
        recyclerView.setAdapter(adapter);*/


    }

    public void searchResults(View v){
        if (et_searchQuery.getText().toString().trim().length() == 0){
            Toast.makeText(Search.this, "Enter words to search", Toast.LENGTH_SHORT).show();
        }

        searchQuery = et_searchQuery.getText().toString();
        /*Bundle bundle = new Bundle();
        bundle.putString("searchQuery", searchQuery);
        sfrag = new SearchFragment();
        sfrag.setArguments(bundle);

        openSearch();*/

        RecyclerView rv_search = (RecyclerView) findViewById(R.id.rv_searchDisplay);

        SearchAdapter adapter = new SearchAdapter(this, productDB.searchResult(searchQuery));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_search.setLayoutManager(layoutManager);
        rv_search.setAdapter(adapter);




    }

    /*public void openSearch(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.placeholderSearchResutls, sfrag);
        fragmentTransaction.commit();
    }*/
}
