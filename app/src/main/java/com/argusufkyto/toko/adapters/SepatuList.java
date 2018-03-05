package com.argusufkyto.toko.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.argusufkyto.toko.R;
import com.argusufkyto.toko.model.Sepatu;

import java.util.List;

public class SepatuList extends ArrayAdapter<Sepatu> {
    private Activity context;
    List<Sepatu> sepatu;



    public SepatuList(Activity context, List<Sepatu> sepatu) {
        super(context, R.layout.layout_sepatu_list,sepatu );
        this.context = context;
        this.sepatu = sepatu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_sepatu_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);
        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imageList);

        Sepatu sepatu = this.sepatu.get(position);

        byte[] decodeImage= Base64.decode(sepatu.getImageFile(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeImage,0,decodeImage.length);
        textViewName.setText(sepatu.getName());
        textViewPrice.setText("Rp."+String.valueOf(sepatu.getPrice()));
        imageView.setImageBitmap(bitmap);


        return listViewItem;
    }
}
