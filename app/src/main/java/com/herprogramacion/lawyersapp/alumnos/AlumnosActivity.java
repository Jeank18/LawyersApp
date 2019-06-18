package com.herprogramacion.lawyersapp.alumnos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.herprogramacion.lawyersapp.R;

public class AlumnosActivity extends AppCompatActivity {

    public static final String EXTRA_ALUMNOS_ID = "extra_alumnos_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AlumnosFragment fragment = (AlumnosFragment)
                getSupportFragmentManager().findFragmentById(R.id.alumnos_container);

        if (fragment == null) {
            fragment = AlumnosFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.alumnos_container, fragment)
                    .commit();
        }
    }
}
