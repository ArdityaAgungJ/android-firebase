package com.argusufkyto.toko.firebase;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.argusufkyto.toko.adapters.SepatuList;
import com.argusufkyto.toko.adapters.TransactionList;
import com.argusufkyto.toko.model.LogTransaction;
import com.argusufkyto.toko.model.Sepatu;
import com.argusufkyto.toko.model.Transaction;
import com.argusufkyto.toko.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACER on 12/2/2017.
 */

public class FirebaseHelper {


    private List<Sepatu> sepatuList;

    public List<Sepatu> getSepatuList() {
        return sepatuList;
    }

    private List<Transaction> transactionList;

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    private boolean newUser;

    private Sepatu sendSepatu;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private double totalPrice=0;



    public FirebaseHelper() {
    }

    public void addSepatu(String name, double price, byte[] image){

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference("sepatu");
        String id = mRef.push().getKey();


        String imageFile = Base64.encodeToString(image, Base64.DEFAULT);
        Sepatu sepatu = new Sepatu(name,price,imageFile,id);

        mRef.child(id).setValue(sepatu);

        /*
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("sepatu");
        String id = mRef.push().getKey();
        mRef.setValue(image);
        */

    }

    public void addUser(String uid, String email, String name,String role){

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference("user");

        User user = new User(uid,email,name,role);

        mRef.child(uid).setValue(user);


    }

    public void checkNewUser(final String uid, final TextView holder, final String email, final String name){
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("user");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if (uid.equals(user.getId())){
                        holder.setText("old user");
                        Log.i("uid check",user.getId());
                        Log.i("uid check1",uid);

                    }
                }
                if(holder.getText().equals("old user")){

                } else {
                    holder.setText("new user");
                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference mRef = mDatabase.getReference("user");

                    User user = new User(uid,email,name,"user");

                    mRef.child(uid).setValue(user);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setListDialog(final String uid, final Button update, final Button delete){
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("user");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if (uid.equals(user.getId())){
                        if(user.getRole().equals("admin")) {
                            update.setVisibility(View.VISIBLE);
                            delete.setVisibility(View.VISIBLE);
                        }
                        else {
                            update.setVisibility(View.GONE);
                            delete.setVisibility(View.GONE);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setButtonVisibility(final String uid, final FloatingActionButton add){
        final DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("user");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    if (uid.equals(user.getId())){
                        if(user.getRole().equals("admin"))
                            add.setVisibility(View.VISIBLE);
                        else add.setVisibility(View.INVISIBLE);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addLogTransaction(final String uid){
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mRef = mDatabase.getReference("log_transaction/"+uid);

        DatabaseReference tRef = FirebaseDatabase.getInstance().getReference("transaction/"+uid);

        tRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Transaction transaction = postSnapshot.getValue(Transaction.class);
                    //adding artist to the list

                    String id = mRef.push().getKey();
                    LogTransaction log = new LogTransaction(uid,transaction.getProductID(),id);
                    mRef.child(id).setValue(log);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }


    public void addTransaction(final String uid,String productID, String productName, double productPrice){
        DatabaseReference tRef = FirebaseDatabase.getInstance().getReference("transaction/"+uid);
        String tid = tRef.push().getKey();


        Transaction transaction = new Transaction(tid,uid,productID,productName,productPrice);

        tRef.child(tid).setValue(transaction);
    }


    public void tampilListSepatu(final Activity context, final GridView listView){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("sepatu");
        sepatuList = new ArrayList<>();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sepatuList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Sepatu sepatu = postSnapshot.getValue(Sepatu.class);
                    //adding artist to the list
                    sepatuList.add(sepatu);
                }

                //creating adapter
                SepatuList sepatuAdapter = new SepatuList(context, sepatuList);
                //attaching adapter to the listview
                listView.setAdapter(sepatuAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

//    public Sepatu getProduct(final String pid){
//        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("sepatu");
//
//        sendSepatu = new Sepatu();
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //iterating through all the nodes
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Sepatu sepatu = postSnapshot.getValue(Sepatu.class);
//                    if (pid.equals(sepatu.getId())){
//                        sendSepatu.setImageFile(sepatu.getImageFile());
//                        sendSepatu.setImage(sepatu.getImage());
//                        sendSepatu.setId(sepatu.getId());
//                        sendSepatu.setName(sepatu.getName());
//                        sendSepatu.setPrice(sepatu.getPrice());
//                    }
//                    Log.i("sepatuID",sendSepatu.getId()+"");
//                    Log.i("sepatuName",sendSepatu.getName()+"");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        Log.i("sepatuID2",sendSepatu.getId()+"");
//        Log.i("sepatuName2",sendSepatu.getName()+"");
//        return sendSepatu;
//    }

    public void showTransactionbck(final Activity context, final ListView listView, String uid){
        DatabaseReference tRef = FirebaseDatabase.getInstance().getReference("transaction/"+uid);
        transactionList = new ArrayList<>();

        tRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Transaction transaction = postSnapshot.getValue(Transaction.class);
                    //adding artist to the list
                    transactionList.add(transaction);
                }

                //creating adapter
                TransactionList transactionAdapter = new TransactionList(context, transactionList);
                //attaching adapter to the listview
                listView.setAdapter(transactionAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void showTransaction(final Activity context, final ListView listView, String uid, final TextView textView){
        DatabaseReference tRef = FirebaseDatabase.getInstance().getReference("transaction/"+uid);
        transactionList = new ArrayList<>();


        tRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                transactionList.clear();

                totalPrice=0;
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Transaction transaction = postSnapshot.getValue(Transaction.class);
                    //adding artist to the list
                    transactionList.add(transaction);
                    totalPrice = totalPrice+transaction.getPrice();
                    textView.setText("Rp."+String.valueOf(totalPrice));
                }

                //creating adapter
                TransactionList transactionAdapter = new TransactionList(context, transactionList);
                //attaching adapter to the listview
                listView.setAdapter(transactionAdapter);

//                for (int i=0; i<transactionList.size();i++) {
//                    totalPrice = totalPrice+transactionList.get(i).getPrice();
//                    setTotalPrice(totalPrice+transactionList.get(i).getPrice());
//                    Log.i("transactionPrice "+i, String.valueOf(transactionList.get(i).getPrice()));
//                }
//                Log.i("transactionPrice in ", String.valueOf(totalPrice));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    public  void deleteTransaksi(String uid){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("transaction").child(uid);
        mRef.removeValue();
    }

    public  void deleteSepatu(String id){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("sepatu").child(id);
        mRef.removeValue();
    }

    public  void removeCart(String tid,String uid){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("transaction/"+uid).child(tid);
        mRef.removeValue();
    }

    public void updateSepatu(String id, String name, double price, byte[] image){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("sepatu");

        String imageFile = Base64.encodeToString(image, Base64.DEFAULT);
        Sepatu sepatu = new Sepatu(name,price,imageFile,id);

        mRef.child(id).setValue(sepatu);

    }

    public void getImage(final String sid, final ImageView imageView){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("sepatu");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist


                    Sepatu sepatu = postSnapshot.getValue(Sepatu.class);
                    //adding artist to the list
                    if (sid.equals(sepatu.getId())) {
                        byte[] decodeImage = Base64.decode(sepatu.getImageFile(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);
                        imageView.setImageBitmap(bitmap);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getUserRole(final String uid, final TextView holderRole){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("user");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist

                    User user = postSnapshot.getValue(User.class);
                    //adding artist to the list
                    if (uid.equals(user.getId())) {
                        holderRole.setText(user.getRole());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
