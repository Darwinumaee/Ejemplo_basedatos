package com.umaee.bdumaee;

import static android.app.ProgressDialog.show;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity implements View.OnClickListener{

    DataBaseManager manager;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    SharedPreferences registro;
    SharedPreferences.Editor admin;
    ListView lista;
    TextView tv;
    Button btn;
    Button btnbuscar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.listview);
        tv = findViewById(R.id.TxtNombre);

        manager = new DataBaseManager(this);
        cursor = manager.cargarCursorContactos();

        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);

        btnbuscar = findViewById(R.id.btnagregar);
        btnbuscar.setOnClickListener(this);

        final String[] from = new String[]{DataBaseManager.CN_NAME, DataBaseManager.CN_PHONE};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to,0);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener((parent, view, position, id) -> {
            cursor.moveToPosition(position);
            Intent editar = new Intent(MainActivity.this, EditarActivity.class);
            editar.putExtra("idContacto", cursor.getString(0)); startActivity(editar);
            finish();
        });
        registro = getSharedPreferences("Historial", 0);
        admin = registro.edit();

        //verificar si es la primera vez
        if(registro.getBoolean("Primera_vez",true)){
            //insercion a tabla categorias
            manager.insertarcat("Amigos");
            manager.insertarcat("Trabajo");
            manager.insertarcat("Familia");

            admin.putBoolean("Primera_vez",false).commit();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            final Cursor c= manager.buscarContacto(tv.getText().toString());
            adapter.changeCursor(c);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    c.moveToPosition(position);
                    Intent editar = new Intent(MainActivity.this, EditarActivity.class);
                    editar.putExtra("idContacto", c.getString(0));
                    startActivity(editar);
                    finish();
                }
            });
        }
        if(v.getId()==R.id.btnagregar){
            Intent otro = new Intent(this,Main2Activity.class); startActivity(otro);
            finish();
        }
    }
}