package com.netscape.utrain.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.netscape.utrain.R;
import com.netscape.utrain.adapters.ServicesBottomSheetAdapter;
import com.netscape.utrain.databinding.FragmentOHistoryBinding;
import com.netscape.utrain.utils.CommonMethods;
import com.netscape.utrain.utils.Constants;
import com.netscape.utrain.utils.PrefrenceConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link O_HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link O_HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class O_HistoryFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BottomSheetBehavior sheetBehavior;
    private ServicesBottomSheetAdapter bottomSheetAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FragmentOHistoryBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private LinearLayout liearLayout;
    private TextView sessionSel, eventSel, doneSel, spaceSel;
    private int sort_count = 1;
    private ViewPagerAdapter adapter;
    private String completed = "Completed";
    private String upcoming = "Upcoming";


    public O_HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment O_HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static O_HistoryFragment newInstance(String param1, String param2) {
        O_HistoryFragment fragment = new O_HistoryFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_o__history, container, false);
        View view = binding.getRoot();
        liearLayout = view.findViewById(R.id.bottomsheet_services);
        spaceSel = view.findViewById(R.id.spaceSel);
        sessionSel = view.findViewById(R.id.sessionSel);
        eventSel = view.findViewById(R.id.eventSel);
        doneSel = view.findViewById(R.id.doneSel);
        sheetBehavior = BottomSheetBehavior.from(liearLayout);
        bottomSheetBehavior_sort();
//        binding.historyTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorGreen));
//        binding.historyTabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        binding.historyTabLayou.setTabTextColors(Color.parseColor("#D6D6D6"), Color.parseColor("#ffffff"));
        setupViewPager(binding.historyViewPager);
        binding.historyTabLayou.setupWithViewPager(binding.historyViewPager);

        binding.filterSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetUpDown_address();
            }
        });

        wrapTabIndicatorToTitle(binding.historyTabLayou, 100, 50);

        liearLayout.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               bottomSheetUpDown_address();
                                           }
                                       }
        );
        return view;
    }

    private void checkClick() {

        eventSel.setBackground(getResources().getDrawable(R.drawable.gray_text_background));
        sessionSel.setBackground(getResources().getDrawable(R.drawable.gray_text_background));
        spaceSel.setBackground(getResources().getDrawable(R.drawable.gray_text_background));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        O_UpcEventFragment.count = 1;
        O_CmpEventFragment.count = 1;
    }

    private void bottomOnClickSort() {

        if (CommonMethods.getPrefData(PrefrenceConstant.ROLE_PLAY, getContext()).equalsIgnoreCase(Constants.Coach)) {
            spaceSel.setVisibility(View.GONE);
        } else {
            spaceSel.setVisibility(View.VISIBLE);

        }
        selectedTExt();

        eventSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_count = 1;
                selectedTExt();
                O_UpcEventFragment.count = sort_count;
                O_CmpEventFragment.count = sort_count;

                upcoming = "Upcoming";
                completed = "Completed";
                setupViewPager(binding.historyViewPager);
                bottomSheetUpDown_address();

            }
        });
        sessionSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_count = 2;
                selectedTExt();
                O_UpcEventFragment.count = sort_count;
                O_CmpEventFragment.count = sort_count;

                upcoming = "Upcoming";
                completed = "Completed";
                setupViewPager(binding.historyViewPager);
                bottomSheetUpDown_address();

            }
        });
        spaceSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_count = 3;
                selectedTExt();

                O_UpcEventFragment.count = sort_count;
                O_CmpEventFragment.count = sort_count;

                upcoming = "Booking";
                completed = "Post Bookings";
                setupSpacePager(binding.historyViewPager);
                bottomSheetUpDown_address();
            }
        });
        doneSel.setVisibility(View.GONE);
        doneSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTExt();
                if (sort_count == 1) {

                } else if (sort_count == 2) {
                    O_UpcEventFragment.count = 2;
                    O_CmpEventFragment.count = 2;
                    setupViewPager(binding.historyViewPager);


                } else if (sort_count == 3) {
                    O_UpcEventFragment.count = 3;
                    O_CmpEventFragment.count = 3;
                    setupViewPager(binding.historyViewPager);

                }
                bottomSheetUpDown_address();

            }
        });


    }


    private void selectedTExt() {
        wrapTabIndicatorToTitle(binding.historyTabLayou, 100, 50);
        if (sort_count == 1) {

            checkClick();
            binding.nameOfType.setText("Event");

            eventSel.setBackground(getResources().getDrawable(R.drawable.round_background_colord));


        } else if (sort_count == 2) {
            checkClick();
            binding.nameOfType.setText("Session");

            sessionSel.setBackground(getResources().getDrawable(R.drawable.round_background_colord));


        } else if (sort_count == 3) {
            checkClick();
            binding.nameOfType.setText("Space");

            spaceSel.setBackground(getResources().getDrawable(R.drawable.round_background_colord));

        }
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

    private void bottomSheetBehavior_sort() {
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void bottomSheetUpDown_address() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        bottomOnClickSort();
    }

    private void setupViewPager(final ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new O_UpcEventFragment(), upcoming);
        adapter.addFragment(new O_CmpEventFragment(), completed);
        viewPager.setAdapter(adapter);
    }
    private void setupSpacePager(final ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new O_UpcEventFragment(), "Space Bookings");
        viewPager.setAdapter(adapter);
    }

    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                //set minimum width to 0 for instead for small texts, indicator is not wrapped as expected
                tabView.setMinimumWidth(0);
                // set padding to 0 for wrapping indicator as title
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                // setting custom margin between tabs
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        settingMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        settingMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        settingMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }


            tabLayout.requestLayout();
        }
    }

    private void settingMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
