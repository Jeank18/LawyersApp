package com.herprogramacion.lawyersapp.alumnosdetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.alumnos.AlumnosActivity;

public class AlumnosDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra(AlumnosActivity.EXTRA_ALUMNOS_ID);

        AlumnosDetailFragment fragment = (AlumnosDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.alumnos_detail_container);
        if (fragment == null) {
            fragment = AlumnosDetailFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.alumnos_detail_container, fragment)
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alumnos_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
