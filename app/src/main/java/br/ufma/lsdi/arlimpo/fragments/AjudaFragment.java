package br.ufma.lsdi.arlimpo.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufma.lsdi.arlimpo.R;
import br.ufma.lsdi.arlimpo.activity.MainActivity;

/**
 * Created by Leeo on 04/04/2017.
 */

public class AjudaFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Context context;

    private final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private SharedPreferences sharedPreferences;


    public static AjudaFragment newInstance(String param1, String param2) {
        AjudaFragment fragment = new AjudaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AjudaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.toolbar.setTitle("Ajuda");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajuda, container, false);
        sharedPreferences = getActivity().getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);

        return view;
    }

}
