package andfans.com.mylbs.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andfans.com.mylbs.R;

/**
 * Created by 兆鹏 on 2016/11/23.
 */
public class Fragment_me extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment03_layout,container,false);


        return v;
    }
}
