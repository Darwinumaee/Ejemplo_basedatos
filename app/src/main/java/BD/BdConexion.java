package BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BdConexion extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bd_umaee.db";
    private static final int DATABASE_VERSION = 1;

    public BdConexion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BdAdministracion.CREATE_TABLE);
        db.execSQL(BdAdministracion.CREATE_CATEGORIAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
