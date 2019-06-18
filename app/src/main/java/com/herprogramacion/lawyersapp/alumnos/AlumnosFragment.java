package com.herprogramacion.lawyersapp.alumnos;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.addeditalumnos.AddEditAlumnosActivity;
import com.herprogramacion.lawyersapp.data.AlumnosContract;
import com.herprogramacion.lawyersapp.data.AlumnosDbHelper;
import com.herprogramacion.lawyersapp.alumnosdetail.AlumnosDetailActivity;

import static com.herprogramacion.lawyersapp.data.AlumnosContract.AlumnosEntry;


/**
 * Vista para la lista de alumnos
 */
public class AlumnosFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_ALUMNOS = 2;

    private AlumnosDbHelper mAlumnosDbHelper;

    private ListView mAlumnosList;
    private AlumnosCursorAdapter mAlumnosAdapter;
    private FloatingActionButton mAddButton;


    public AlumnosFragment() {
        // Required empty public constructor
    }

    public static AlumnosFragment newInstance() {
        return new AlumnosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alumnos, container, false);

        // Referencias UI
        mAlumnosList = (ListView) root.findViewById(R.id.alumnos_list);
        mAlumnosAdapter = new AlumnosCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mAlumnosList.setAdapter(mAlumnosAdapter);

        // Eventos
        mAlumnosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mAlumnosAdapter.getItem(i);
                String currentAlumnosId = currentItem.getString(
                        currentItem.getColumnIndex(AlumnosEntry.ID));

                showDetailScreen(currentAlumnosId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });


        getActivity().deleteDatabase(AlumnosDbHelper.DATABASE_NAME);

        // Instancia de helper
        mAlumnosDbHelper = new AlumnosDbHelper(getActivity());

        // Carga de datos
        loadAlumnos();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditAlumnosActivity.REQUEST_ADD_ALUMNOS:
                    showSuccessfullSavedMessage();
                    loadAlumnos();
                    break;
                case REQUEST_UPDATE_DELETE_ALUMNOS:
                    loadAlumnos();
                    break;
            }
        }
    }

    private void loadAlumnos() {
        new AlumnosLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Alumno guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditAlumnosActivity.class);
        startActivityForResult(intent, AddEditAlumnosActivity.REQUEST_ADD_ALUMNOS);
    }

    private void showDetailScreen(String alumnosId) {
        Intent intent = new Intent(getActivity(), AlumnosDetailActivity.class);
        intent.putExtra(AlumnosActivity.EXTRA_ALUMNOS_ID, alumnosId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_ALUMNOS);
    }

    private class AlumnosLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAlumnosDbHelper.getAllAlumnos();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mAlumnosAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
