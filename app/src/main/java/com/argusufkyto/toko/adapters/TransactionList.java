package com.argusufkyto.toko.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.argusufkyto.toko.R;
import com.argusufkyto.toko.helpers.PenampunganGlobal;
import com.argusufkyto.toko.firebase.FirebaseHelper;
import com.argusufkyto.toko.model.Sepatu;
import com.argusufkyto.toko.model.Transaction;

import java.util.List;

    public class TransactionList extends ArrayAdapter<Transaction> {
        private Activity context;
        List<Transaction> transactions;
        private Sepatu product;

        String pid2,productName;





        public TransactionList(Activity context, List<Transaction> transactions) {
            super(context,R.layout.layout_transaction_list,transactions);
            this.context = context;
            this.transactions = transactions;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_transaction_list, null, true);

            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewNameTR);
            TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPriceTR);
            ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imageListTR);

            Transaction transaction = this.transactions.get(position);


            textViewName.setText(transaction.getProductName());
            textViewPrice.setText("Rp."+String.valueOf(transaction.getPrice()));
            PenampunganGlobal g = new PenampunganGlobal();
            g.setTotalPrice(transaction.getPrice());
            Log.i("asd",String.valueOf(g.getTotalPrice())+"");
            FirebaseHelper setImage = new FirebaseHelper();
            setImage.getImage(transaction.getProductID(),imageView);


            return listViewItem;
        }



    }
