package com.example.gimgiyun.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Basket>[] mBasket = new ArrayList[100];
    public static int i = 0;

    TextView basket;

    String currentUser;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        i++;
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        basket = (TextView)findViewById(R.id.basket);

        for(int j = 0; j<100; j++){

            mBasket[j] = new ArrayList<>();
        }

        mAdapter = new MyAdapter(mBasket[i]);
        mRecyclerView.setAdapter(mAdapter);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        currentUser = user.getEmail();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("like?");

        // 데이터베이스 읽기 #3. ChildEventListener
        myRef.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Basket basket = dataSnapshot.getValue(Basket.class);
                if( ((basket.getEmail()).equals(currentUser)) ){

                    mBasket[i].add(basket);
                    mAdapter.notifyItemInserted(mBasket[i].size() - 1);
                }
                else{
                    mBasket[i+1].add(basket);
                    mAdapter.notifyItemInserted(mBasket[i+1].size() - 1);
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
