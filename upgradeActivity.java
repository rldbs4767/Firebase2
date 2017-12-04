package com.example.gimgiyun.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class upgradeActivity extends AppCompatActivity {

    information User = new information();

    String currentUser;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;

    TextView tvMessage;
    //관심상품에 담을 상품목록
    Button pizzaButton;
    Button mealButton;


    public static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        i++;
        firebaseAuth = FirebaseAuth.getInstance();
        tvMessage = (TextView)findViewById(R.id.tv_message);

        pizzaButton = (Button)findViewById(R.id.pizzaButton);
        mealButton = (Button)findViewById(R.id.mealButton);
        database = FirebaseDatabase.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        currentUser = user.getEmail();

        User.email[i] = user.getEmail();

            for(int j = 0; j<=i; j++) {

                if (currentUser.equals(User.email[j])) {
                    i = j;
                    break;
                }
            }

            if (User.pizzalike[i] == 1) {
                pizzaButton.setText("nonlike");

            }

            if (User.meallike[i] == 1) {
                mealButton.setText("nonlike");
            }

        //버튼 이벤트
        pizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                (User.pizzalike[i])++;

                if (User.pizzalike[i] == 1) {
                    writeUserlike("pizza", currentUser);
                    pizzaButton.setText("nonlike");
                    User.pizzalike[i] = 1;

                }
                else if (User.pizzalike[i] > 1) {
                    writeUserunlike("not pizza", currentUser);
                    pizzaButton.setText("pizza");
                    User.pizzalike[i] = 0;

                }

            }
        });

        mealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User.meallike[i]++;

                if(User.meallike[i] == 1)
                {
                    writeUserlike("meal",currentUser);
                    mealButton.setText("nonlike");
                    User.meallike[i] = 1;

                }
                else if(User.meallike[i] != 1)
                {
                    writeUserunlike("not meal",currentUser);
                    mealButton.setText("meal");
                    User.meallike[i] = 0;
                }

            }
        });


    }
    //좋아하는 품목 or 품목을 취소하는 함수(사용자가 누군지 알기위해)
    private void writeUserlike(String item, String email)
    {
        User user = new User(email,item);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        FirebaseDatabase.getInstance().getReference("like?").child(formattedDate).setValue(user);
    }
    private void writeUserunlike(String item, String email)
    {
        User user = new User(email,item);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        FirebaseDatabase.getInstance().getReference("like?").child(formattedDate).setValue(user);
    }

}



