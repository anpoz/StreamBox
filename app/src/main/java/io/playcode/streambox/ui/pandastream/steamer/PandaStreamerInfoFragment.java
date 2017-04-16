package io.playcode.streambox.ui.pandastream.steamer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.playcode.streambox.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PandaStreamerInfoFragment extends Fragment {


    public PandaStreamerInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_panda_streamer_info, container, false);
    }

}
