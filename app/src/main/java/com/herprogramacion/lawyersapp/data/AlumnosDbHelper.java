package com.herprogramacion.lawyersapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.herprogramacion.lawyersapp.data.AlumnosContract.AlumnosEntry;

/**
 * Manejador de la base de datos
 */
public class AlumnosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Alumnos.db";

    public AlumnosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AlumnosEntry.TABLE_NAME + " ("
                +AlumnosEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +AlumnosEntry.ID + " TEXT NOT NULL,"
                +AlumnosEntry.NAME + " TEXT NOT NULL,"
                + AlumnosEntry.TELEFONO + " TEXT NOT NULL,"
                + AlumnosEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + AlumnosEntry.ID + "))");



        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockAlumnos(sqLiteDatabase, new Alumnos("Jean Carlos", "60 01 50 36", "foto.png"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Daniel ", "61 01 50 36", "foto.png"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Lucia ", "60 01 50 36", "foto.png"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Marina", "61 01 50 36", "foto.png"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Olga ", "300 200 5555", "foto.png"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Pamela ", "300 200 6666", "foto.png"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Rodrigo ","300 200 1111", "foto.png"));
        mockAlumnos(sqLiteDatabase, new Alumnos("Tom ", "300 200 1111", "foto.png"));
    }

    public long mockAlumnos(SQLiteDatabase db, Alumnos alumnos) {
        return db.insert(
                AlumnosEntry.TABLE_NAME,
                null,
                alumnos.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveAlumnos(Alumnos alumnos) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                AlumnosEntry.TABLE_NAME,
                null,
                alumnos.toContentValues());

    }

    public Cursor getAllAlumnos() {
        return getReadableDatabase()
                .query(
                        AlumnosEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getAlumnosById(String alumnosId) {
        Cursor c = getReadableDatabase().query(
                AlumnosEntry.TABLE_NAME,
                null,
                AlumnosEntry.ID + " LIKE ?",
                new String[]{alumnosId},
                null,
                null,
                null);
        return c;
    }

    public int deleteAlumnos(String alumnosId) {
        return getWritableDatabase().delete(
                AlumnosEntry.TABLE_NAME,
                AlumnosEntry.ID + " LIKE ?",
                new String[]{alumnosId});
    }

    public int updateAlumnos(Alumnos alumnos, String alumnosId) {
        return getWritableDatabase().update(
                AlumnosEntry.TABLE_NAME,
                alumnos.toContentValues(),
                AlumnosEntry.ID + " LIKE ?",
                new String[]{alumnosId}
        );
    }
}
