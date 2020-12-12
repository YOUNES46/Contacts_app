package com.example.base_sql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Updatecontact extends AppCompatActivity {
    Dbcontact dbb;
    EditText name , phone ;
    Button conf;
    ImageButton imagg;
    byte[] imagee = null;
    int idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatecontact);
        idd = getIntent().getIntExtra("id",0);
       dbb = new Dbcontact(this);
       Contact con = dbb.getcont(idd);
       name = (EditText) findViewById(R.id.nom_changer);
       phone = (EditText) findViewById(R.id.phone_changer);
       conf = (Button) findViewById(R.id.changer_btn);
        imagg = (ImageButton) findViewById(R.id.imagebutt2) ;
       String lenom = con.getName();
       String lephone = String.valueOf(con.getPhone());
       name.setText(lenom);
       phone.setText(lephone);
        Bitmap bitmap = BitmapFactory.decodeByteArray(con.getImage(), 0, con.getImage().length);
        imagg.setImageBitmap(bitmap);
        imagee = getBytes(bitmap);
        imagg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
                intentImg.setType("image/*");
                startActivityForResult(intentImg, 100);

            }
        });
       conf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String nam = name.getText().toString();
               int oho =Integer.parseInt(phone.getText().toString());
               BitmapDrawable drawable = (BitmapDrawable) imagg.getDrawable();
               Bitmap bitmap = drawable.getBitmap();
               imagee = getBytes(bitmap);
               Contact co = new Contact(nam,oho,imagee);
               co.setId(idd);
              dbb.updatecotacye(co);
               Toast.makeText(Updatecontact.this, "Vous Aver Bien Changer Le Contact...", Toast.LENGTH_SHORT).show();
              finish();
               // Intent intent = new Intent(Updatecontact.this,MainActivity.class);
              // startActivity(intent);

           }
       });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delet,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_delet:
                showalerdialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showalerdialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attention...")
        .setMessage("Voulez supprimer ce contact....!!!!")
        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbb.deletcontac(idd);
                finish();

            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {

            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                imagg.setImageBitmap(decodeStream);

                imagee = getBytes(decodeStream);

            } catch (Exception ex) {
                Log.e("ex", ex.getMessage());
            }

        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
