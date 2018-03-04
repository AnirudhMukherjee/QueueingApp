package com.djunicode.queuingapp.fragment;


import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.activity.StudentListActivity;
import com.djunicode.queuingapp.activity.TeacherScreenActivity;
import com.djunicode.queuingapp.model.RecentEvents;
import com.djunicode.queuingapp.model.TeacherCreateNew;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherSubmissionFragment extends Fragment {

  private Spinner subjectSpinner, batchSpinner;
  private CardView fromTimePickerButton;
  private ImageButton studentsButton, timerButton;
  private FloatingActionButton createFab, startFab, cancelFab, createNewFab;
  private LinearLayout fabLL1, fabLL2, fabLL3;
  private CoordinatorLayout coordinatorLayout;
  private RelativeLayout relativeLayout;
  private Animation fabOpen, fabClose, rotateForward, rotateBackward;
  private Boolean isFabOpen, fromSelected, toSelected, studentsSelected;
  private String fromTime;
  private String toTime="00:00",noOfStudents;
  private int size;
  public static List<RecentEvents> recentEventsList = new ArrayList<>();
  final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

  public TeacherSubmissionFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_teacher_submission, container, false);

    String[] array = {"Select", "one", "two", "three", "four", "five", "six", "seven", "eight",
        "nine", "ten"};


    isFabOpen = false;
    fromSelected = false;
    toSelected = false;
    studentsSelected = false;
    subjectSpinner = (Spinner) view.findViewById(R.id.subjectSpinner);
    batchSpinner = (Spinner) view.findViewById(R.id.batchSpinner);
    fromTimePickerButton = (CardView) view.findViewById(R.id.fromTimePickerButton);
    studentsButton = (ImageButton) view.findViewById(R.id.studentsButton);
    timerButton = (ImageButton) view.findViewById(R.id.timerButton);
    createFab = (FloatingActionButton) view.findViewById(R.id.createFab);
    startFab = (FloatingActionButton) view.findViewById(R.id.startFab);
    cancelFab = (FloatingActionButton) view.findViewById(R.id.cancelFab);
    createNewFab = (FloatingActionButton) view.findViewById(R.id.createNewFab);
    coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.teacherCoordinatorLayout);
    relativeLayout = (RelativeLayout) view.findViewById(R.id.submissionRelativeLayout);
    fabLL1 = (LinearLayout) view.findViewById(R.id.fabLL1);
    fabLL2 = (LinearLayout) view.findViewById(R.id.fabLL2);
    fabLL3 = (LinearLayout) view.findViewById(R.id.fabLL3);
    fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
    fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
    rotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
    rotateBackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
        android.R.layout.simple_spinner_dropdown_item, array);

    subjectSpinner.setAdapter(adapter);
    batchSpinner.setAdapter(adapter);

    batchSpinner.setEnabled(false);
    batchSpinner.setAlpha(0.4f);

    subjectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
          batchSpinner.setEnabled(true);
          batchSpinner.setAlpha(1.0f);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    fromTimePickerButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (batchSpinner.getSelectedItemPosition() != 0) {
          TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),

                  new OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour, min;
                  if (hourOfDay <= 11 && minute <= 59) {

                    if(hourOfDay<10){
                        hour = "0"+Integer.toString(hourOfDay);
                    }
                    else
                        hour = Integer.toString(hourOfDay);
                    if(minute<10){
                        min = "0"+Integer.toString(minute);
                    }
                    else
                        min = Integer.toString(minute);
                    fromTime = hour + ":" + min;
                      Snackbar.make(coordinatorLayout,
                              "Submission is from " + fromTime + "am",
                              Snackbar.LENGTH_LONG).show();
                  } else {
                    if (hourOfDay == 12 && minute == 0) {
                        if(hourOfDay<10){
                            hour = "0"+Integer.toString(hourOfDay);
                        }
                        else
                            hour = Integer.toString(hourOfDay);
                        if(minute<10){
                            min = "0"+Integer.toString(minute);
                        }
                        else
                            min = Integer.toString(minute);
                        fromTime = hour + ":" + minute;
                      Snackbar.make(coordinatorLayout,
                          "Submission is from " + fromTime + "pm",
                          Snackbar.LENGTH_LONG).show();

                    } else {
                      hourOfDay -= 12;
                        if(hourOfDay<10){
                            hour = "0"+Integer.toString(hourOfDay);
                        }
                        else
                            hour = Integer.toString(hourOfDay);
                        if(minute<10){
                            min = "0"+Integer.toString(minute);
                        }
                        else
                            min = Integer.toString(minute);
                        fromTime = hour + ":" + min;
                      Snackbar.make(coordinatorLayout,
                          "Submission is from " + fromTime + "pm",
                          Snackbar.LENGTH_LONG).show();

                    }
                  }
                  fromSelected = true;
                }
              },
              0, 0, false);
          timePickerDialog.show();
        } else {
          Toast.makeText(getContext(), "Please select the batch!", Toast.LENGTH_SHORT).show();
        }
      }
    });

    timerButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (fromSelected) {
          TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
              new OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour,min;
                  if (hourOfDay <= 11 && minute <= 59) {
                      if(hourOfDay<10){
                          hour = "0"+Integer.toString(hourOfDay);
                      }
                      else
                          hour = Integer.toString(hourOfDay);
                      if(minute<10){
                          min = "0"+Integer.toString(minute);
                      }
                      else
                          min = Integer.toString(minute);
                      toTime = hour + ":" + min;
                    Snackbar.make(coordinatorLayout,
                        "Submission is till " + toTime + "am",
                        Snackbar.LENGTH_LONG).show();

                  } else {
                    if (hourOfDay == 12 && minute == 0) {
                        if(hourOfDay<10){
                            hour = "0"+Integer.toString(hourOfDay);
                        }
                        else
                            hour = Integer.toString(hourOfDay);
                        if(minute<10){
                            min = "0"+Integer.toString(minute);
                        }
                        else
                            min = Integer.toString(minute);
                        toTime = hour + ":" + min;
                      Snackbar.make(coordinatorLayout,
                          "Submission is till " + toTime + "pm",
                          Snackbar.LENGTH_LONG).show();

                    } else {
                      hourOfDay -= 12;
                        if(hourOfDay<10){
                            hour = "0"+Integer.toString(hourOfDay);
                        }
                        else
                            hour = Integer.toString(hourOfDay);
                        if(minute<10){
                            min = "0"+Integer.toString(minute);
                        }
                        else
                            min = Integer.toString(minute);
                        toTime = hour + ":" + min;
                      Snackbar.make(coordinatorLayout,
                          "Submission is till " + hourOfDay + ":" + minute + "pm",
                          Snackbar.LENGTH_LONG).show();

                    }
                  }
                  toSelected = true;
                }
              },
              0, 0, false);
          timePickerDialog.show();
        } else {
          Toast.makeText(getContext(), "Please select from time.", Toast.LENGTH_SHORT).show();
        }
      }
    });

    studentsButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (fromSelected) {
          final EditText input = new EditText(getContext());
          input.setInputType(InputType.TYPE_CLASS_NUMBER);
          AlertDialog.Builder builder = new Builder(getContext());
          builder.setTitle("Enter the number of students");
          if (input.getParent() != null) {
            ((ViewGroup) input.getParent()).removeView(input);
            input.setText("");
          }
          builder.setView(input);
          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              Toast.makeText(getContext(), input.getText().toString() + " students selected",
                  Toast.LENGTH_SHORT).show();
              size = Integer.parseInt(input.getText().toString());
              noOfStudents = input.getText().toString() + " students";
              studentsSelected = true;
            }
          });
          builder.setNegativeButton("CANCEL", null);
          builder.show();
        } else {
          Toast.makeText(getContext(), "Please select from time.", Toast.LENGTH_SHORT).show();
        }
      }
    });

    createFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        animateFab();
      }
    });

    cancelFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        animateFab();
      }
    });

    startFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        animateFab();
        Intent intent = new Intent(getContext(), StudentListActivity.class);
        startActivity(intent);
      }
    });

    createNewFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        animateFab();
        /*recentEventsList.add(new RecentEvents(subjectSpinner.getSelectedItem().toString(),
            batchSpinner.getSelectedItem().toString(), fromTime, toTime,
            TeacherLocationFragment.locationString));*/

        if (toSelected || studentsSelected) {
          AlertDialog.Builder builder = new Builder(getContext());
          builder.setMessage("Do you want to use the last location or set new location?")
              .setPositiveButton("LAST", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  if (!TeacherLocationFragment.locationUpdated) {
                    Bundle extras = new Bundle();
                    extras.putBoolean("Flag", false);
                    extras.putString("Subject", subjectSpinner.getSelectedItem().toString());
                    extras.putString("Batch", batchSpinner.getSelectedItem().toString());
                    extras.putString("From", fromTime);
                    extras.putString("To", noOfStudents);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                        .beginTransaction();

                    TeacherLocationFragment locationFragment = new TeacherLocationFragment();
                    locationFragment.setArguments(extras);

                    TeacherScreenActivity.bottomNavigationView
                        .setSelectedItemId(R.id.action_location);

                    transaction.replace(R.id.containerLayoutTeacher, locationFragment);
                    transaction.commit();

                    String message = "No last location set! Please update your location.";
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                  } else {
                    recentEventsList
                        .add(new RecentEvents(subjectSpinner.getSelectedItem().toString(),
                            batchSpinner.getSelectedItem().toString(),
                            size, fromTime, toTime,
                            TeacherLocationFragment.locationString));
                    Toast.makeText(getContext(), "Created new event!", Toast.LENGTH_SHORT).show();
                  }
                }
              })
              .setNegativeButton("NEW", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Bundle extras = new Bundle();
                  extras.putBoolean("Flag", true);
                  extras.putString("Subject", subjectSpinner.getSelectedItem().toString());
                  extras.putString("Batch", batchSpinner.getSelectedItem().toString());
                  extras.putString("From", fromTime);
                  extras.putString("To", toTime);

                  FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                      .beginTransaction();

                  TeacherLocationFragment locationFragment = new TeacherLocationFragment();
                  locationFragment.setArguments(extras);

                  TeacherScreenActivity.bottomNavigationView
                      .setSelectedItemId(R.id.action_location);

                  transaction.replace(R.id.containerLayoutTeacher, locationFragment);
                  transaction.commit();

                }
              });

          Log.i("APIError", fromTime);
          Log.i("APIError", toTime);

                Call<TeacherCreateNew> call = apiService.sendSubmissionData(subjectSpinner.getSelectedItem().toString(),
                        size, "Sheku",fromTime + ":00", toTime + ":00", "");
                call.enqueue(new Callback<TeacherCreateNew>() {

                    @Override
                    public void onResponse(Call<TeacherCreateNew> call, Response<TeacherCreateNew> response) {
                        //Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                        Bundle ids = new Bundle();
                        ids.putString("ids",Integer.toString(response.body().getId()));
                        RecentsFragment.eventIds.add(response.body().getId());
                        recentEventsList.add(new RecentEvents(subjectSpinner.getSelectedItem().toString(),size,fromTime,toTime,response.body().getId(),TeacherLocationFragment.locationString));
                        Log.i("Hello", "hello");
                        if (response.isSuccessful())
                            Log.d("Response succ", response.body().toString());
                        else
                            Log.d("Response succ", response.errorBody().toString());
                        sendNotificationToStudents(response.body().getId());
                    }

                    @Override
                    public void onFailure(Call<TeacherCreateNew> call, Throwable t) {
                        Toast.makeText(getContext(), "Submission failed", Toast.LENGTH_SHORT).show();
                    }
                });

          builder.show();
        } else {
          Toast.makeText(getContext(), "Please select all fields.", Toast.LENGTH_SHORT).show();
        }

      }
    });

    return view;
  }

  private void sendNotificationToStudents(int id){
      Call<TeacherCreateNew> call = apiService.sendStudNotification(id,"sheku");
      call.enqueue(new Callback<TeacherCreateNew>() {
          @Override
          public void onResponse(Call<TeacherCreateNew> call, Response<TeacherCreateNew> response) {

          }

          @Override
          public void onFailure(Call<TeacherCreateNew> call, Throwable t) {

          }
      });

  }
  private void animateFab() {
    if (isFabOpen) {
      fabLL1.setVisibility(View.INVISIBLE);
      fabLL2.setVisibility(View.INVISIBLE);
      fabLL3.setVisibility(View.INVISIBLE);
      isFabOpen = false;
      createFab.setAnimation(rotateBackward);
      fabLL1.animate().translationY(0).alpha(0.0f);
      fabLL2.animate().translationY(0).alpha(0.0f);
      fabLL3.animate().translationY(0).alpha(0.0f);
      if(VERSION.SDK_INT >= 23)
        relativeLayout.setForeground(new ColorDrawable(getResources().
            getColor(android.R.color.transparent)));
    } else {
      fabLL1.setVisibility(View.VISIBLE);
      fabLL2.setVisibility(View.VISIBLE);
      fabLL3.setVisibility(View.VISIBLE);
      isFabOpen = true;
      createFab.setAnimation(rotateForward);
      fabLL1.animate().translationY(-getResources().getDimension(R.dimen.standard_65)).alpha(1.0f);
      fabLL2.animate().translationY(-getResources().getDimension(R.dimen.standard_115)).alpha(1.0f);
      fabLL3.animate().translationY(-getResources().getDimension(R.dimen.standard_165)).alpha(1.0f);
      if(VERSION.SDK_INT >= 23)
        relativeLayout.setForeground(new ColorDrawable(ContextCompat.
            getColor(getContext(), R.color.transparent_white)));
    }
  }

}
