package com.argusufkyto.toko.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.argusufkyto.toko.R;
import com.argusufkyto.toko.activities.Sepatu.AddActivity;
import com.argusufkyto.toko.activities.Sepatu.ListActivity;
import com.argusufkyto.toko.firebase.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    Button btnList, btnCart;
    TextView holder;
    FloatingActionButton fab;


    private String uid,email,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        Intent userCredential = getIntent();
        uid = userCredential.getStringExtra("uid");
        email = userCredential.getStringExtra("email");
        name = userCredential.getStringExtra("name");
        Log.i("uidz",uid);
        FirebaseHelper cUser = new FirebaseHelper();
        cUser.checkNewUser(uid, holder,email,name);
        cUser.setButtonVisibility(uid,fab);



        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("uid",uid);
                intent.putExtra("holder",holder.getText());
                startActivity(intent);
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TransactionActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(MainActivity.this,AddActivity.class);
                startActivity(add);
            }
        });

    }



    public void init(){

        btnList = (Button) findViewById(R.id.btnList);
        btnCart = findViewById(R.id.btnCart);
        holder = findViewById(R.id.holder);
        fab = findViewById(R.id.floatingActionButton);
    }


}
