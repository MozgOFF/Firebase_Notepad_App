package com.example.midterm_lab3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ITEMS = "items";

    private RecyclerView mRecyclerView;
    private ItemsAdapter mAdapter;

    private Button mLogoutButton;
    private Button mAddItems;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private DataSnapshot mDataSnapeshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        initUI();
    }

    private void initUI() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        mAdapter = new ItemsAdapter(getItems())

        mAddItems = findViewById(R.id.addItems);
        mLogoutButton = findViewById(R.id.logout);

        mLogoutButton.setOnClickListener(this);
        mAddItems.setOnClickListener(this);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mReference = mFirebaseDatabase.getReference(ITEMS);

        getItems();

    }

    private void getItems() {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Item> items = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Item item = child.getValue(Item.class);
                    items.add(new Item(item.getmItemId(), item.getmTitle(), item.getmContent(), item.getmPriority()));
                }
                mAdapter = new ItemsAdapter(items);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.logout):
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case (R.id.addItems):
                startActivity(new Intent(this, ToDoActivity.class));
                break;
        }
    }
}
