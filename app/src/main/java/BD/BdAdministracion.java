package BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class BdAdministracion {
    public static final String TABLA_ALUMNOS = "alumnos";
    public static final String ALUMNOS_ID = "_id";
    public static final String ALUMNOS_NOMBRE = "nombre";
    public static final String ALUMNOS_APELLIDO = "apellido";
    public static final String ALUMNOS_EMAIL = "email";

    //Tabla Categorías
    public static final String TABLE2_NAME = "categorias";
    public static final String TABLE2_ID = "_id";
    public static final String TABLE2_NOMBRE = "nombre";
    public static final String TABLE2_DESCRIPCION = "descripcion";

    public static final String CREATE_TABLE = "create table" + TABLA_ALUMNOS +  "("
            + ALUMNOS_ID + " integer primary key autoincrement, "
            + ALUMNOS_NOMBRE + " text not null, "
            + ALUMNOS_APELLIDO + " text not null, "
            + ALUMNOS_EMAIL + " text not null);"
            +"FOREIGN KEY (" + ALUMNOS_ID + ") REFERENCES " + TABLE2_NAME + "(" + TABLE2_ID + ");";

    public static final String CREATE_CATEGORIAS = "create table" + TABLE2_NAME +  "("
            + TABLE2_ID + " integer primary key autoincrement, "
            + TABLE2_NOMBRE + " text not null, "
            + TABLE2_DESCRIPCION + " text not null);";

    private BdConexion _conexion;
    private SQLiteDatabase _basededatos;

    public BdAdministracion(Context context) {
        _conexion = new BdConexion(context);
    }
    public  BdAdministracion open() throws SQLException {
        _basededatos = _conexion.getWritableDatabase();
        return this;
    }
    public void close() {
        _conexion.close();
    }
}

