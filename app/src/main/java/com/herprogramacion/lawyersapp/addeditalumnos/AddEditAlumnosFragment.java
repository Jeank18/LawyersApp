package com.herprogramacion.lawyersapp.addeditalumnos;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.data.Alumnos;
import com.herprogramacion.lawyersapp.data.AlumnosDbHelper;

/**
 * Vista para creación/edición de un alumno
 */
public class AddEditAlumnosFragment extends Fragment {
    private static final String ARG_ALUMNOS_ID = "arg_alumnos_id";

    private String mAlumnosId;

    private AlumnosDbHelper mAlumnosDbHelper;

    private FloatingActionButton mSaveButton;
    private TextInputEditText mNameField;
    private TextInputEditText mEdad;
    private TextInputEditText mCorreo;
    private TextInputEditText mTelefonoField;
    private TextInputLayout mNameLabel;
    private TextInputLayout mEdad;
    private TextInputLayout mCorreo;
    private TextInputLayout mTelefonoLabel;


    public AddEditAlumnosFragment() {
        // Required empty public constructor
    }

    public static AddEditAlumnosFragment newInstance(String alumnosId) {
        AddEditAlumnosFragment fragment = new AddEditAlumnosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ALUMNOS_ID, alumnosId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlumnosId = getArguments().getString(ARG_ALUMNOS_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_alumnos, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNameField = (TextInputEditText) root.findViewById(R.id.et_name);
        mEdadField = (TextInputEditText) root.findViewById(R.id.et_edad);
        mCorreoField = (TextInputEditText) root.findViewById(R.id.et_correo);
        mTelefonoField = (TextInputEditText) root.findViewById(R.id.et_telefono);
        mNameLabel = (TextInputLayout) root.findViewById(R.id.til_name);
        mEdadLabel = (TextInputLayout) root.findViewById(R.id.til_edad);
        mCorreoLabel = (TextInputLayout) root.findViewById(R.id.til_correo);
        mTelefonoLabel = (TextInputLayout) root.findViewById(R.id.til_telefono);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditAlumnos();
            }
        });

        mAlumnosDbHelper = new AlumnosDbHelper(getActivity());

        // Carga de datos
        if (mAlumnosId != null) {
            loadAlumnos();
        }

        return root;
    }

    private void loadAlumnos() {
        new GetAlumnosByIdTask().execute();
    }

    private void addEditAlumnos() {
        boolean error = false;

        String name = mNameField.getText().toString();
        String edad = mEdadField.getText().toString();
        String correo = mCorreoField.getText().toString();
        String telefono = mTelefonoField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            mNameLabel.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(name)) {
            mEdadLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(name)) {
            mCorreoLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(telefono)) {
            mTelefonoLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Alumnos alumnos = new Alumnos(name, edad, correo, telefono, "");

        new AddEditAlumnosTask().execute(alumnos);

    }

    private void showAlumnosScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva información", Toast.LENGTH_SHORT).show();
    }

    private void showAlumnos(Alumnos alumnos) {
        mNameField.setText(alumnos.getName());
        mTelefonoField.setText(alumnos.getTelefono());

    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar alumno", Toast.LENGTH_SHORT).show();
    }

    private class GetAlumnosByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAlumnosDbHelper.getAlumnosById(mAlumnosId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showAlumnos(new Alumnos(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditAlumnosTask extends AsyncTask<Alumnos, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Alumnos... alumnos) {
            if (mAlumnosId != null) {
                return mAlumnosDbHelper.updateAlumnos(alumnos[0], mAlumnosId) > 0;

            } else {
                return mAlumnosDbHelper.saveAlumnos(alumnos[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showAlumnosScreen(result);
        }

    }

}
