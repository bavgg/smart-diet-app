package com.jg.dietapp;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    public void updateProgress(int value) {
        if (getActivity() instanceof MainActivity) {
//            ((MainActivity) getActivity()).updateProgress(value);
        }
    }
}
