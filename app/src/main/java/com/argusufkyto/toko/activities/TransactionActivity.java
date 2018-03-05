package com.argusufkyto.toko.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.argusufkyto.toko.R;
import com.argusufkyto.toko.firebase.FirebaseHelper;

import android.widget.TextView;
import android.widget.Toast;

import com.argusufkyto.toko.model.Transaction;

import java.util.List;

public class TransactionActivity extends AppCompatActivity {
    ListView listView;
    private String uid;
    private List<Transaction> transactionList;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Intent getUID= getIntent();
        uid=getUID.getStringExtra("uid");


        listView = (ListView) findViewById(R.id.listViewTransaction);
        final TextView tVtotal = findViewById(R.id.textViewTotalPrice);

        final FirebaseHelper list = new FirebaseHelper();
        list.showTransaction(this,listView,uid,tVtotal);
//        ListAdapter a = listView.getAdapter();
//        a.getItem(1);
//        Transaction c = new Transaction();
//

//        DatabaseReference tRef = FirebaseDatabase.getInstance().getReference("transaction/"+uid);
//        transactionList = new ArrayList<>();
//
//
//        tRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                transactionList.clear();
//
//                //iterating through all the nodes
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    //getting artist
//                    Transaction transaction = postSnapshot.getValue(Transaction.class);
//                    //adding artist to the list
//                    transactionList.add(transaction);
//
//                    totalPrice = totalPrice+transaction.getPrice();
//                    tVtotal.setText(String.valueOf(totalPrice));
//                    Log.i("transactionPrice out ", String.valueOf(transaction.getPrice()));
//
//                }
//
//                //creating adapter
//                TransactionList transactionAdapter = new TransactionList(TransactionActivity.this, transactionList);
//                //attaching adapter to the listview
//                listView.setAdapter(transactionAdapter);
//
////                for (int i=0; i<transactionList.size();i++) {
////                    totalPrice = totalPrice+transactionList.get(i).getPrice();
////                    setTotalPrice(totalPrice+transactionList.get(i).getPrice());
////                    Log.i("transactionPrice "+i, String.valueOf(transactionList.get(i).getPrice()));
////                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//
//        });
//
//        Log.i("transactionPrice out ", String.valueOf(totalPrice));
//



//        List<Transaction> stockList = new ArrayList<Transaction>();
//        Transaction transaction;
//
//        int count = listView.getCount();
//
//        for(int i = 0; i < count; i++){
//            Transaction color = (Transaction) listView.getItemAtPosition(i);
//
//            // Here's the critical part I was missing
//            View childView = listView.getChildAt(i);
//
//            color.getProductName();
//            EditText editText = (EditText) childView.findViewById(R.id.countEditText);
//            String colorCount = editText.getText().toString();
//
//            stock = new Stock();
//            stock.setColorCode(color.getCode());
//            stock.setCount(Integer.valueOf(colorCount));
//
//            stockList.add(stock);
//        }


//        totalPrice=list.getTotalPrice();
//        Log.i("Total",String.valueOf(totalPrice));
        Button btnTransactionBuy = findViewById(R.id.btnTransactionBuy);

        btnTransactionBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TransactionActivity.this);
                dialogBuilder.setTitle("CONFIRMATION");
                dialogBuilder.setMessage("Are you sure want to buy from this cart?");
                dialogBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseHelper log = new FirebaseHelper();
                        log.addLogTransaction(uid);
                        list.deleteTransaksi(uid);
                        tVtotal.setText("");
                        Toast.makeText(getBaseContext(),"Payment Success",Toast.LENGTH_SHORT).show();
                    }
                });
                dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialogBuilder.create();
                dialogBuilder.show();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Transaction transaction = list.getTransactionList().get(i);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TransactionActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.cart_dialog, null);
                dialogBuilder.setView(dialogView);

                final Button buttonRemove = (Button) dialogView.findViewById(R.id.buttonRemoveCart);


                final AlertDialog b = dialogBuilder.create();
                b.show();

                buttonRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseHelper remove = new FirebaseHelper();
                        remove.removeCart(transaction.getTransactionID(),uid);
                        b.dismiss();
                    }
                });
            }
        });
    }

}
