package com.herprogramacion.lawyersapp.alumnosdetail;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.herprogramacion.lawyersapp.R;
import com.herprogramacion.lawyersapp.addeditalumnos.AddEditAlumnosActivity;
import com.herprogramacion.lawyersapp.alumnos.AlumnosActivity;
import com.herprogramacion.lawyersapp.alumnos.AlumnosFragment;
import com.herprogramacion.lawyersapp.data.Alumnos;
import com.herprogramacion.lawyersapp.data.AlumnosDbHelper;

/**
 * Vista para el detalle del alumno
 */
public class AlumnosDetailFragment extends Fragment {
    private static final String ARG_ALUMNOS_ID = "alumnosId";

    private String mAlumnosId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mTelefono;

    private AlumnosDbHelper mAlumnosDbHelper;


    public AlumnosDetailFragment() {
        // Required empty public constructor
    }

    public static AlumnosDetailFragment newInstance(String alumnosId) {
        AlumnosDetailFragment fragment = new AlumnosDetailFragment();
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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alumnos_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mTelefono = (TextView) root.findViewById(R.id.tv_telefono);

        mAlumnosDbHelper = new AlumnosDbHelper(getActivity());

        loadAlumnos();

        return root;
    }

    private void loadAlumnos() {
        new GetAlumnosByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteAlumnosTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AlumnosFragment.REQUEST_UPDATE_DELETE_ALUMNOS) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showAlumnos(Alumnos alumnos) {
        mCollapsingView.setTitle(alumnos.getName());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + alumnos.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mTelefono.setText(alumnos.getTelefono());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditAlumnosActivity.class);
        intent.putExtra(AlumnosActivity.EXTRA_ALUMNOS_ID, mAlumnosId);
        startActivityForResult(intent, AlumnosFragment.REQUEST_UPDATE_DELETE_ALUMNOS);
    }

    private void showAlumnosScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar alumno", Toast.LENGTH_SHORT).show();
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
            }
        }

    }

    private class DeleteAlumnosTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mAlumnosDbHelper.deleteAlumnos(mAlumnosId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showAlumnosScreen(integer > 0);
        }

    }

}
