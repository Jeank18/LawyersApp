package com.herprogramacion.lawyersapp.addeditalumnos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.alumnos.AlumnosActivity;

public class AddEditAlumnosActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_ALUMNOS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String alumnosId = getIntent().getStringExtra(AlumnosActivity.EXTRA_ALUMNOS_ID);

        setTitle(alumnosId == null ? "Añadir alumno" : "Editar alumno");

        AddEditAlumnosFragment addEditAlumnosFragment = (AddEditAlumnosFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_alumnos_container);
        if (addEditAlumnosFragment == null) {
            addEditAlumnosFragment = AddEditAlumnosFragment.newInstance(alumnosId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_alumnos_container, addEditAlumnosFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
