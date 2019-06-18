package com.herprogramacion.lawyersapp.data;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para abogados
 */
public class AlumnosContract {

    public static abstract class AlumnosEntry implements BaseColumns{
        public static final String TABLE_NAME ="alumnos";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String TELEFONO = "telefono";
        public static final String AVATAR_URI = "avatarUri";
    }
}
