package com.almanara.homeschool.student.course.lesson.topic.template;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.Constants;

import static com.almanara.ali.homeschool.R.string.answer;

/**
 * Created by Ali on 7/1/2017.
 */

public class ColorFragment extends Fragment  {
    String layout;
    private final String HOLD = " ,HO##LD,";
    String[] parms;
    private TextView  colorText;
    private ImageView cameraIcon;

    public ColorFragment() {
    }

    public static ColorFragment newInstance(String layout) {
        ColorFragment fragment = new ColorFragment();
        Bundle args = new Bundle();
        args.putString("layout", layout);
        fragment.setArguments(args);
        return fragment;
    }

    public boolean openApp(Context context, String packageName, String data, int requestCode, String activityName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
//                return false;
                throw new PackageManager.NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_DEFAULT);
            i.putExtra("Data", data);
            i.setFlags(0);
            i.setClassName(packageName,packageName+"."+activityName);
            getActivity().startActivityForResult(i, requestCode);

            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layout = getArguments().getString("layout");
            if (layout != null) {
                parms = layout.split("(?<=" + HOLD + ")");
                for (int i = 0; i < parms.length; i++) {
                    parms[i] = parms[i].replaceAll(HOLD, " ");
                }
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.color_fragment, container, false);
//        questionText = (TextView) view.findViewById(R.id.textView1);
        colorText = (TextView) view.findViewById(R.id.textView2);
        colorText.setText(parms[0]);
        cameraIcon = (ImageView) view.findViewById(R.id.camera_icon);


        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!openApp(getActivity(), "com.example.android.color",
                        parms[0], Constants.Color_Request,"MainActivity")) {
                    Snackbar.make(getView(), R.string.please,Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.download, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = "https://dl.dropboxusercontent.com/s/wol3h7olint9z03/app-debug.apk?dl=0";
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                }
                            })
                            .show();
                }

            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       /* if (speech != null) {
            speech.destroy();
        }*/

    }


}
