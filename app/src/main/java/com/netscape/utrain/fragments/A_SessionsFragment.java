package com.netscape.utrain.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.netscape.utrain.R;
import com.netscape.utrain.activities.athlete.AllEventsMapAct;
import com.netscape.utrain.adapters.Ath_SessionRecyclerAdapter;
import com.netscape.utrain.model.AthleteSessionModel;
import com.netscape.utrain.response.AthleteSessionResponse;
import com.netscape.utrain.retrofit.RetrofitInstance;
import com.netscape.utrain.retrofit.Retrofitinterface;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A_SessionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A_SessionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A_SessionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Ath_SessionRecyclerAdapter adapter;
    private List<String> data = new ArrayList<>();
    private List<AthleteSessionModel> listModels = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Retrofitinterface api;

    private MaterialButton sessionViewAllBtn;

    public A_SessionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A_SessionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A_SessionsFragment newInstance(String param1, String param2) {
        A_SessionsFragment fragment = new A_SessionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.athlete_session_fragment, container, false);
        recyclerView = view.findViewById(R.id.organisationRecycler);
        sessionViewAllBtn = view.findViewById(R.id.sessionViewAllBtn);
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        data.add("chet");
//        adapter=new CoachesRecyclerAdapter(getContext(),data);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
        sessionViewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllEventsMapAct.class);
                intent.putExtra("from", "2");
                getContext().startActivity(intent);
            }
        });
        getAthleteEventApi();

        return view;
    }

    private void getAthleteEventApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        api = RetrofitInstance.getClient().create(Retrofitinterface.class);
        Call<AthleteSessionResponse> call = api.getAthleteSessionList("Bearer " + CommonMethods.getPrefData(Constants.AUTH_TOKEN, getContext()), Constants.CONTENT_TYPE, "", "10", "latest", "");
        call.enqueue(new Callback<AthleteSessionResponse>() {
            @Override
            public void onResponse(Call<AthleteSessionResponse> call, Response<AthleteSessionResponse> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().isStatus()) {
                        listModels.clear();
                        listModels.addAll(response.body().getData().getData());
                        adapter = new Ath_SessionRecyclerAdapter(getContext(), listModels);
                        recyclerView.setAdapter(adapter);

                    }
                }


            }

            @Override
            public void onFailure(Call<AthleteSessionResponse> call, Throwable t) {

                progressDialog.dismiss();
//               if (!t.toString().equalsIgnoreCase("null") )
//
//                        Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
