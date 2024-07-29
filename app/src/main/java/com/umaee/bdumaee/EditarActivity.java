package com.umaee.bdumaee;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditarActivity extends AppCompatActivity implements View.OnClickListener {
    private DataBaseManager manager;
    Cursor cursor;
    private String parametro;
    private EditText txtnombre;
    private EditText txttelefono;
    private Spinner spncategorias;
    SimpleCursorAdapter acategorias;
    Cursor ccategorias;
    private Integer posicion;

    private Button btnactualizar;
    private Button btneliminar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar); manager = new DataBaseManager(this);
        Intent param = getIntent(); parametro=param.getStringExtra("idContacto");

        cursor = manager.buscarID(parametro); cursor.moveToPosition(0);
        txttelefono= (EditText) findViewById(R.id.txtNTelefono);
        txtnombre = (EditText) findViewById(R.id.txtNContacto);
        spncategorias = (Spinner) findViewById(R.id.cmbcategorias);
        btnactualizar = (Button) findViewById(R.id.btnactualizar);
        btnactualizar.setOnClickListener(this);
        txtnombre.setText(cursor.getString(1));
        txttelefono.setText(cursor.getString(2));
        posicion = Integer.parseInt(cursor.getString(3));
        final String[] from=new String[]{manager.CN_NAME2};
        int[] to = new int[]{android.R.id.text1};
        ImageView avatar = (ImageView) findViewById(R.id.img);

        switch (posicion){ case 1:
            avatar.setImageResource(R.drawable.amigos);
            break; case 2:
            avatar.setImageResource(R.drawable.trabajo);
            break; case 3:
            avatar.setImageResource(R.drawable.familia);
            break; default:
            avatar.setImageResource(R.drawable.contacto);
        }
        ccategorias = manager.cargarCursorCategorias();
        acategorias = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_dropdown_item,ccategorias,from,to,0);
        spncategorias.setAdapter(acategorias);

        spncategorias.setSelection(posicion-1);
        btneliminar=(Button) findViewById(R.id.btnEliminar);
        btneliminar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.btnactualizar){
            posicion = spncategorias.getSelectedItemPosition();
            ccategorias.moveToPosition(posicion);
            manager.modificar(parametro, txtnombre.getText().toString(), txttelefono.getText().toString(), ccategorias.getString(0));
            txtnombre.setText(""); txttelefono.setText("");
            Intent refresh= new Intent(this,MainActivity.class); startActivity(refresh);
            finish();
        }
        if(v.getId()== R.id.btnEliminar){
            manager.eliminar(parametro); txtnombre.setText("");
            txttelefono.setText("");
            Intent refresh= new Intent(this,MainActivity.class); startActivity(refresh);
            finish();
        }

    }
}
