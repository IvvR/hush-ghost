package com.irv.smart.hushghosts;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ivan on 21/12/17.
 */

public class AboutHGFrag  extends Fragment {


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.frag_about_hg,container, false);

    return view;
  }
}
