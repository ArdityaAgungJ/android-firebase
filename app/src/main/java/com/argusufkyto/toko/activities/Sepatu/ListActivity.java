package com.argusufkyto.toko.activities.Sepatu;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.argusufkyto.toko.R;
import com.argusufkyto.toko.firebase.FirebaseHelper;
import com.argusufkyto.toko.helpers.PenampunganGlobal;
import com.argusufkyto.toko.model.Sepatu;

public class ListActivity extends AppCompatActivity {
    GridView listView;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent getUID= getIntent();
        uid=getUID.getStringExtra("uid");

        listView = (GridView) findViewById(R.id.listView);

        final FirebaseHelper list = new FirebaseHelper();
        list.tampilListSepatu(this,listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Sepatu sepatu = list.getSepatuList().get(i);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ListActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.list_dialog, null);
                dialogBuilder.setView(dialogView);

                final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
                final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
                final Button buttonBuy = (Button) dialogView.findViewById(R.id.buttonBuy);

                FirebaseHelper dialogList = new FirebaseHelper();
                dialogList.setListDialog(uid,buttonUpdate,buttonDelete);


                final AlertDialog b = dialogBuilder.create();
                b.show();



                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PenampunganGlobal g = (PenampunganGlobal) getApplication();
                        g.setId(sepatu.getId());
                        g.setName(sepatu.getName());
                        g.setPrice(sepatu.getPrice());
                        g.setImageFile(sepatu.getImageFile());

                        Intent iUpdate = new Intent(ListActivity.this, UpdateActivity.class);
                        startActivity(iUpdate);
                    }
                });



                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseHelper delete = new FirebaseHelper();
                        delete.deleteSepatu(sepatu.getId());
                        b.dismiss();
                    }
                });

                buttonBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseHelper buy = new FirebaseHelper();
                        buy.addTransaction(uid,sepatu.getId(),sepatu.getName(),sepatu.getPrice());
                    }
                });


            }
        });
    }

}
