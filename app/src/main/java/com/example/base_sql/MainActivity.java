package com.example.base_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView contactslist;
    Button add;
    Dbcontact DB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactslist = (ListView) findViewById(R.id.contectelist);
        add = (Button) findViewById(R.id.ajouter_cont);
        DB = new Dbcontact(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Addcontacs.class);

                startActivity(i);
            }
        });
        contactslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact selected_cont = (Contact) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this,Updatecontact.class);
                intent.putExtra("id",selected_cont.getId());
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Contact> contacs = DB.getAllcontacts();

        contacteAdapter adapter = new contacteAdapter(this,R.layout.item_adapter,contacs);
        contactslist.setAdapter(adapter);
    }
}
