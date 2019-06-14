package com.udithamelan.foodapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udithamelan.foodapp.database.ProductDBHelper;
import com.udithamelan.foodapp.recyclervdisplay.SearchAdapter;


public class SearchFragment extends Fragment {

    public View onCreate(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);


        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ProductDBHelper productDB = new ProductDBHelper(getActivity());

        RecyclerView rv_search = (RecyclerView) view.findViewById(R.id.rv_searchDisplay);

        Bundle bundle = this.getArguments();
        String searchQuery = bundle.getString("searchQuery");

        SearchAdapter adapter = new SearchAdapter(getActivity(), productDB.searchResult(searchQuery));

        rv_search.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_search.setLayoutManager(layoutManager);

        return view;

    }


}
