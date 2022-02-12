package com.example.lalpari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    RecyclerView lalpari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        lalpari = findViewById(R.id.lalpari);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(RecyclerActivity.this,RecyclerView.HORIZONTAL,false);
        lalpari.setLayoutManager(manager);

        List<Integer>list = new ArrayList<>();
        list.add(R.drawable.l1);
        list.add(R.drawable.l2);
        list.add(R.drawable.l3);
        list.add(R.drawable.l4);
        list.add(R.drawable.l5);
        LalPariAdapter pariAdapter = new LalPariAdapter(RecyclerActivity.this,list);
        lalpari.setAdapter(pariAdapter);

    }
}