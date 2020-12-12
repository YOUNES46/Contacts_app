package com.example.base_sql;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Addcontacs extends AppCompatActivity {
    EditText name,phone;
    Button ajouter;
    Dbcontact db;
    ImageButton imag;
    byte[] image = null;
    public static final int CODE_IMG = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontacs);
        name = (EditText) findViewById(R.id.nom_edit);
        phone = (EditText) findViewById(R.id.phone_edit);
        ajouter = (Button) findViewById(R.id.ajouter_edit);
        imag = (ImageButton) findViewById(R.id.imagebutt) ;

        db = new Dbcontact(this);
        imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
                intentImg.setType("image/*");
                startActivityForResult(intentImg, CODE_IMG);

            }
        });
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namee  = name.getText().toString();
                int  phonee  =Integer.parseInt(phone.getText().toString());
                BitmapDrawable drawable = (BitmapDrawable) imag.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                image = getBytes(bitmap);

                Contact cont =  new Contact(namee,phonee);
                db.addContact(cont);
                Toast.makeText(Addcontacs.this, "Vous Aver Bien Ajouter Le Contact...", Toast.LENGTH_SHORT).show();
                finish();


            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_OK &&  resultCode ==CODE_IMG){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                imag.setImageBitmap(decodeStream);
                image = getBytes(decodeStream);
            }
            catch(Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }


        }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }



}
