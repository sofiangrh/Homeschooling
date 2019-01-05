package com.almanara.homeschool.childEnrolledCourses;


import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.almanara.homeschool.data.firebase.CourseCreated;
import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.data.firebase.EnrolledCourseModel;
import com.almanara.homeschool.student.course.lesson.LessonActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCoursesFragment extends Fragment {
   // EnrolledCoursesAdapter1 enrolledCoursesAdapter;
    RecyclerView enrolledRecyclerView;
   // private static final int CURSOR_LOADER_ID = 1;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference db;
    TextView noMyCourse ;
   // List<Courses2> enrolledCoursesList;
    List<Courses> enrolledCoursesList;
    List<CourseCreated> coursesNames;
    ValueEventListener listener;

    public MyCoursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(container!=null){
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_courses_fragmetnt, container, false);
        noMyCourse =(TextView) view.findViewById(R.id.no_mycourse);
                enrolledRecyclerView = (RecyclerView) view.findViewById(R.id.enrolledCourses);
        enrolledRecyclerView.setHasFixedSize(true);
        LinearLayoutManager categoryLayoutManger = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        enrolledRecyclerView.setLayoutManager(categoryLayoutManger);
        Log.v("Test","MyCoursesFragment");
        return view;
    }

    @Override
    public void onPause() {
        if (listener != null)
            db.removeEventListener(listener);
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
       listener = new ValueEventListener() {
//                    enrolledCourses = new ArrayList;
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        //enrolledCoursesList = new ArrayList<Courses2>();
                       // enrolledCoursesList = new ArrayList<Courses>();
                        coursesNames = new ArrayList<CourseCreated>();
                        for (DataSnapshot s : dataSnapshot.getChildren()) {
                            Log.v("REBE", "Inside " + s);
                            EnrolledCourseModel c = s.getValue(EnrolledCourseModel.class);
                            Log.v("REBE", "Outside " + c);

                            db.child("courses").child(c.getCourse_id())
                                    .addValueEventListener(new ValueEventListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                        @Override
                                        public void onDataChange(DataSnapshot inside) {
                                            Log.e("Fire", "Inside " + inside.toString());
                                            CourseCreated courses = new CourseCreated();
                                            //Courses2 courseCreated = new Courses2();
                                            courses= inside.getValue(CourseCreated.class);
                                            Log.e("Test","Inside Childs "+courses.getName().toString() );
                                                for (DataSnapshot x : inside.getChildren()){
//                                                    if(Objects.equals(x.getKey(), "course_id")){
//                                                        Log.v("Test","course id " +x.getValue());
////                                                        courses.setCourse_id(x.getValue().toString());
//                                                        courses.setCourse_id(x.getValue().toString());
//                                                    }else if(Objects.equals(x.getKey(), "name")){
//                                                        Log.v("Test","Des " +x.getValue());
//                                                        courses.setName(x.getValue().toString());
//                                                        courses.setDescriptionS(x.getValue().toString());
//                                                    }

                                                    Log.e("myCoursesdatasnapshot: ",x.toString() );

                                                    //courses = x.getValue(Courses.class);
                                                }
//                                                Courses2 course2=null ;
                                               // Log.e("mappingcoursesfromfire ", courses.getLessons() + " Description : "
                                                      //  + courses.getDescriptionS() + " Name : " + courses.getName());
//                                                enrolledCoursesList.add(course2);
                                            coursesNames.add(courses);

                                                Log.e("Test", "Enrolled Size Updated:" + coursesNames.size());
                                                EnrolledCoursesAdapter1 enrolledCoursesAdapter1 = new EnrolledCoursesAdapter1(
                                                        coursesNames,
                                                        new EnrolledCoursesAdapter1.OnClickHandler() {
                                                            @Override
                                                            public void onClick(CourseCreated test) {
                                                                Intent intent = new Intent(getActivity(),
                                                                        LessonActivity.class);
                                                                intent.putExtra("course",test);
                                                           //     Log.v("Testsssss","Course Lesson : " + test.getPhoto_url().toString());
//                                                                Set<Map.Entry<String, LessonModel>> entry =test.getLessons().entrySet();
//                                                                Log.v("Test","Lessons entry.size());" +entry.size());
                                                                startActivity(intent);
                                                            }
                                                        } , getActivity());
                                               // Log.v("Test", "Enrolled Size New:" + enrolledCoursesList.size());
                                            enrolledRecyclerView.setAdapter(enrolledCoursesAdapter1 );

                                            if(coursesNames.size() <0){
                                                noMyCourse.setVisibility(View.VISIBLE);
                                            }else{
                                                noMyCourse.setVisibility(View.GONE);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                        }
                        // [END_EXCLUDE]
                    }

                };
        db.child("users").child(user.getUid()).child("enrolledcourses")
                .addValueEventListener(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}