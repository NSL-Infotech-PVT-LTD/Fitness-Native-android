package com.netscape.utrain.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netscape.utrain.R;
import com.netscape.utrain.adapters.O_EventListAdapter;
import com.netscape.utrain.databinding.FragmentOEventListBinding;
import com.netscape.utrain.model.CoachDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link O_EventListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link O_EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class O_EventListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentOEventListBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager completedLayoutManager;
    private RecyclerView.LayoutManager rejectedLayoutManager;
    private O_EventListAdapter currentEventAdapter, completedEventAdapter, rejectedEventAdapter;
    private List<CoachDataModel> data = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public O_EventListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment O_EventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static O_EventListFragment newInstance(String param1, String param2) {
        O_EventListFragment fragment = new O_EventListFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_o__event_list, container, false);
        View view = binding.getRoot();
        layoutManager = new LinearLayoutManager(getContext());
        rejectedLayoutManager = new LinearLayoutManager(getContext());
        completedLayoutManager = new LinearLayoutManager(getContext());
        binding.eventListRecycler.setLayoutManager(layoutManager);
        binding.eventCompletedRecycler.setLayoutManager(completedLayoutManager);
        binding.eventRejectedRecycler.setLayoutManager(rejectedLayoutManager);
        currentEventAdapter = new O_EventListAdapter(getContext(), data);
        rejectedEventAdapter = new O_EventListAdapter(getContext(), data);
        completedEventAdapter = new O_EventListAdapter(getContext(), data);
        binding.eventListRecycler.setAdapter(currentEventAdapter);
        binding.eventCompletedRecycler.setAdapter(completedEventAdapter);
        binding.eventRejectedRecycler.setAdapter(rejectedEventAdapter);

        return view;
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
