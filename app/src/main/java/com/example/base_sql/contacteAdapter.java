package com.example.base_sql;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class contacteAdapter extends ArrayAdapter<Contact>{
    Context context;
    int resource;
    public contacteAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,parent,false);
        TextView name = (TextView) convertView.findViewById(R.id.nom);
        TextView  phone = (TextView) convertView.findViewById(R.id.numero);
        ImageView imgUser = (ImageView) convertView.findViewById(R.id.imageuser);

        Contact currentContacte = getItem(position);
        name.setText(currentContacte.getName());
        phone.setText(String.valueOf(currentContacte.getPhone()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(currentContacte.getImage(), 0, currentContacte.getImage().length);
        imgUser.setImageBitmap(bitmap);

        return convertView;
    }

}
