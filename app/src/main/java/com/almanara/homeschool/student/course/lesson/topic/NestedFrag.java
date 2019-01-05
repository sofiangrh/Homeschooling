package com.almanara.homeschool.student.course.lesson.topic;

/**
 * Created by Ali on 6/24/2017.
 */


//import android.app.Fragment;

import android.content.Intent;
import android.widget.Button;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.almanara.ali.homeschool.R;

public class NestedFrag extends Fragment {
    String layout;
    public NestedFrag() {

    }
    public static NestedFrag newInstance(String layout) {
        NestedFrag fragment = new NestedFrag();
        Bundle args = new Bundle();
        args.putString("layout", layout);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layout = getArguments().getString("layout");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.nested_frag, container, false);
        Button doNestingButton = (Button) root.findViewById(R.id.image_button);
        doNestingButton.setText(doNestingButton.getText() + " <" + layout);
        doNestingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(),GenResult.class),1);
            }
        });
        return root;
    }

}