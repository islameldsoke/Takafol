package com.example.moham.takafol.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moham.takafol.Adapters.UsersAdapter;
import com.example.moham.takafol.Models.Users;
import com.example.moham.takafol.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_home, container, false);

        List<Users> user =new ArrayList<>();

        //fake data

        int Images [] = {R.drawable.trika,R.drawable.trika,R.drawable.trika,R.drawable.trika};
        String UNames [] = {"trika","trika","trika","trika","trika"};
        String Posters [] = {"hello takfol hello takfol hello takfol hello takfol", "hello takfol hello takfol hello takfol hello takfol" ,
                "hello takfol hello takfol hello takfol hello takfol" ,"hello takfol hello takfol hello takfol hello takfol"};

        for (int i=0 ; i<Images.length; i++){
            Users users=
                    new Users(UNames[i] , Posters [i] ,Images[i]);
            user.add(users);
        }


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        UsersAdapter Adapter = new UsersAdapter(user);
        recyclerView.setAdapter(Adapter);
        return view;
    }

}
