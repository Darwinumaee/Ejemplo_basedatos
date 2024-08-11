package com.umaee.bdumaee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
    public static final String TABLE_NAME="contactos";
    public static final String CN_ID="id";
    public static final String CN_NAME="nombre";
    public static final String CN_PHONE="telefono";
    public static final String CN_CATEGORIES="categoria";

    public static final String CREATE_TABLE = "create table" + TABLE_NAME +  "("
            + CN_ID + " integer primary key autoincrement, "
            + CN_NAME + " text not null, "
            + CN_PHONE + " text not null, "
            + CN_CATEGORIES + " text not null);";

    public static final String TABLE2_NAME = "Categorias";
    public static final String CN_ID2="id2";
    public static final String CN_NAME2="nombre";

    public static final String CREATE_TABLE2 = "create table" + TABLE2_NAME +  "("
            + CN_ID2 + " text primary key not null, "
            + CN_NAME2 + " text not null);";


    public static final String ADD_FOREIGN_KEY = "ALTER TABLE " + TABLE_NAME
            + " ADD CONSTRAINT fk_categorias FOREIGN KEY (" + CN_CATEGORIES + ") "
            + "REFERENCES " + TABLE2_NAME + "(" + CN_ID2 + ");";

    private DBHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues(String nombre, String telefono, String categoria){
        ContentValues values = new ContentValues();
        values.put(CN_NAME, nombre);
        values.put(CN_PHONE, telefono);
        values.put(CN_CATEGORIES, categoria);
        return values;
    }

    public void insertar(String nombre, String telefono, String categoria){
        db.insert(TABLE_NAME, null, generarContentValues(nombre, telefono, categoria));
    }

    public void eliminar(String id){
        db.delete(TABLE_NAME, CN_ID + " = ?", new String[]{id});
    }

    /*public void eliminarMultiple(String nom1, String nom2){
        db.delete(TABLE_NAME, CN_NAME + "IN (?, ?)", new String[]{nom1, nom2});
    }*/

    public void modificar(String id, String nombre, String telefono, String categoria){
        db.update(TABLE_NAME, generarContentValues(nombre, telefono, categoria), CN_ID + " = ?", new String[]{id});
    }

    public Cursor cargarCursorContactos(){
        String[] columnas = new String[]{CN_ID, CN_NAME, CN_PHONE, CN_CATEGORIES};
        return db.query(TABLE_NAME, columnas,null, null, null, null, null);
    }

    //Buscar Contacto por nombre
    public Cursor buscarContacto(String nombre){
        String[] columnas = new String[]{CN_ID, CN_NAME, CN_PHONE};
        return db.query(TABLE_NAME, columnas, CN_NAME + " = ?", new String[]{nombre}, null, null, null);
    }

    //Buscar Contacto por ID
    public Cursor buscarID(String id){
        String[] columnas = new String[]{CN_ID, CN_NAME, CN_PHONE, CN_CATEGORIES};
        return db.query(TABLE_NAME, columnas, CN_ID + " = ?", new String[]{id}, null, null, null);
    }

    //Cargar categorias
    public Cursor cargarCursorCategorias(){
        String[] columnas = new String[]{CN_ID2, CN_NAME2};
        return db.query(TABLE2_NAME, columnas,null, null, null, null, null);
    }

    //Insertar Categoria
    public void insertarcat(String nombre){
        ContentValues values = new ContentValues();
        values.put(CN_NAME2,nombre);
        db.insert(TABLE2_NAME, null, values);
    }

   /* public void close(){
        helper.close();
    }*/

}
