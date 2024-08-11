package com.umaee.bdumaee;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

public class Main2Activity extends AppCompatActivity implements ViewModelStoreOwner, View.OnClickListener {
    private DataBaseManager manager;
    private EditText txtnombre;
    private EditText txttelefono;
    private Button btnguardar;
    private Spinner spncategorias;
    Cursor ccategorias;
    SimpleCursorAdapter acategorias;
    private Integer posicion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main); manager = new DataBaseManager(this);
        txttelefono= findViewById(R.id.txtNTelefono);
        txtnombre = findViewById(R.id.txtNContacto);
        spncategorias = findViewById(R.id.cmbcategorias);
        btnguardar = findViewById(R.id.btnguardar);
        btnguardar.setOnClickListener(this);
        final String[] from=new String[]{manager.CN_NAME2};
        int[] to = new int[]{android.R.id.text1};

        ccategorias = manager.cargarCursorCategorias();
        acategorias = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_dropdown_item,ccategorias,from,to,0);
        spncategorias.setAdapter(acategorias);
        ImageView avatar = findViewById(R.id.img);
        avatar.setImageResource(R.drawable.contacto);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.btnguardar) {
            posicion = spncategorias.getSelectedItemPosition();
            ccategorias.moveToPosition(posicion);

            manager.insertar(txtnombre.getText().toString(), txttelefono.getText().toString(), ccategorias.getString(0));
            txtnombre.setText(""); txttelefono.setText("");
            Intent refresh= new Intent(this,MainActivity.class);
            startActivity(refresh);
            finish();
        }    
    }
}
