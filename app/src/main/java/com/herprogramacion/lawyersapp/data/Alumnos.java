package com.herprogramacion.lawyersapp.data;

import android.content.ContentValues;
import android.database.Cursor;

import com.herprogramacion.lawyersapp.data.AlumnosContract.AlumnosEntry;

import java.util.UUID;

/**
 * Entidad "alumnos"
 */
public class Alumnos {
    private String id;
    private String name;
    private String telefono;
    private String avatarUri;

    public Alumnos(String name, String telefono, String avatarUri) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.telefono = telefono;
        this.avatarUri = avatarUri;
    }

    public Alumnos(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(AlumnosEntry.ID));
        name = cursor.getString(cursor.getColumnIndex(AlumnosEntry.NAME));
        telefono = cursor.getString(cursor.getColumnIndex(AlumnosEntry.TELEFONO));
        avatarUri = cursor.getString(cursor.getColumnIndex(AlumnosEntry.AVATAR_URI));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AlumnosEntry.ID, id);
        values.put(AlumnosEntry.NAME, name);
        values.put(AlumnosEntry.TELEFONO, telefono);
        values.put(AlumnosEntry.AVATAR_URI, avatarUri);
        return values;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getTelefono() {
        return telefono;
    }

    public String getAvatarUri() {
        return avatarUri;
    }
}
