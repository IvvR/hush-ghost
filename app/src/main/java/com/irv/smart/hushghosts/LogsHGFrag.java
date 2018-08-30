package com.irv.smart.hushghosts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 21/12/17.
 */

public class LogsHGFrag extends Fragment {


  private List<String> listItems = new ArrayList<>();
  private ArrayAdapter<String> adapter;
  private ListView logListView;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.frag_log_hg, container, false);
  }

  public void onViewCreated(View view, Bundle savedInstanceState) {

    listItems.addAll(HushGhostLogger.loadLogFileAsList(getActivity()));
    logListView = (ListView) view.findViewById(R.id.logListView);
    adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listItems);
    logListView.setAdapter(adapter);

  }


}
