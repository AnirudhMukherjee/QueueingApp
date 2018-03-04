package com.djunicode.queuingapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.SessionManagement.SessionManager;
import com.djunicode.queuingapp.activity.StudentScreenActivity;
import com.djunicode.queuingapp.activity.TeacherScreenActivity;
import com.djunicode.queuingapp.customClasses.HashingPassword;
import com.djunicode.queuingapp.model.Teacher;
import com.djunicode.queuingapp.model.TeacherCreateNew;
import com.djunicode.queuingapp.model.TeacherForId;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpTeacherFragment extends Fragment {

  private EditText usernameTeacherEditText, sapIDTeacherEditText, passwordTeacherEditText;
  private Spinner departmentTeacherSpinner;
  private CardView signUpTeacherButton;
  private TextInputLayout teacherSignUpinputLayoutUsername, teacherSignUpinputLayoutPassword,
      teacherSignUpinputLayoutDepartment, teacherSignUpinputLayoutSAPId;
  private String password,username,department,sapId;
  private String salt, intermediate_password, hashed_password;
  SessionManager session;
  final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
  private Random random;
  private int location;
  public SignUpTeacherFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_sign_up_teacher, container, false);

    teacherSignUpinputLayoutUsername = (TextInputLayout) view.findViewById(R.id.signUp_teacher_username);
    teacherSignUpinputLayoutPassword = (TextInputLayout) view.findViewById(R.id.signUp_teacher_password);
    teacherSignUpinputLayoutDepartment = (TextInputLayout) view.findViewById(R.id.signUp_teacher_Department);
    teacherSignUpinputLayoutSAPId = (TextInputLayout) view.findViewById(R.id.signUp_teacher_SAPId);
    usernameTeacherEditText = (EditText) view.findViewById(R.id.usernameTeacherEditText);
    passwordTeacherEditText = (EditText) view.findViewById(R.id.passwordTeacherEditText);
    sapIDTeacherEditText = (EditText) view.findViewById(R.id.sapIDTeacherEditText);
    departmentTeacherSpinner = (Spinner) view.findViewById(R.id.departmentTeacherSpinner);
    signUpTeacherButton = (CardView) view.findViewById(R.id.signUpTeacherButton);
    random = new Random();
    location = random.nextInt(65535);

    // Session Manager
    session = new SessionManager(getContext(), "Teacher");

    signUpTeacherButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(validSubmission()) {
          username = usernameTeacherEditText.getText().toString();
          password = passwordTeacherEditText.getText().toString();
          sapId = sapIDTeacherEditText.getText().toString();
          department = departmentTeacherSpinner.getSelectedItem().toString();
          hash_password(password);
          session.createLoginSession(usernameTeacherEditText.getText().toString(),
              passwordTeacherEditText.getText().toString());
          getIdforTeacher();
          Intent intent = new Intent(getContext(), TeacherScreenActivity.class);
          // StudentScreenActivity just for demo till the time teacher fragments are not ready
          startActivity(intent);
          Toast.makeText(getContext(), usernameTeacherEditText.getText().toString(),
              Toast.LENGTH_SHORT).show();
          Toast.makeText(getContext(), passwordTeacherEditText.getText().toString(),
              Toast.LENGTH_SHORT).show();
        }
      }
    });

    return view;
  }
  private void getIdforTeacher() {
      Call<TeacherForId> call = apiService.getTeacherId(username, password,"abc@hmail.com");
      call.enqueue(new Callback<TeacherForId>() {
          @Override
          public void onResponse(Call<TeacherForId> call, Response<TeacherForId> response) {
              Log.v("Tatti", "tatti");
              if (response.isSuccessful()) {
                  Log.i("idFromServer", Integer.toString(response.body().getId()));
                  sendTeacherDataToServer(response.body().getId());
              }
              else
                  Log.e("Err", response.errorBody().toString());
          }

          @Override
          public void onFailure(Call<TeacherForId> call, Throwable t) {

          }
      });
  }

    private void sendTeacherDataToServer(int id) {
        Call<Teacher> call = apiService.createTeacherAccount(id, username,sapIDTeacherEditText.getText().toString(),"AOA",46);
        call.enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                //Check here for valid response
//                if(response.isSuccessful())
                Log.d("Teacher",response.body().toString());
            }

            @Override
            public void onFailure(Call<Teacher> call, Throwable t) {

            }
        });
    }


  private Boolean validSubmission () {

    if (!validateUsername()) {
      return false;
    }

    if (!validateDepartment()) {
      return false;
    }

    if (!validateSAPId()) {
      return false;
    }

    if (!validatePassword()) {
      return false;
    }

    return true;
  }

  private boolean validateUsername() {
    if (usernameTeacherEditText.getText().toString().trim().isEmpty()) {
      teacherSignUpinputLayoutUsername.setError(getString(R.string.err_msg_username));
      requestFocus(usernameTeacherEditText);
      return false;
    } else if ((usernameTeacherEditText.getText().toString().length() < 5)) {
      teacherSignUpinputLayoutUsername.setError(getString(R.string.err_msg_username_inappropriate_length));
      requestFocus(usernameTeacherEditText);
      return false;
    }else {
      teacherSignUpinputLayoutUsername.setErrorEnabled(false);
    }

    return true;
  }

  private boolean validateSAPId() {
    if (sapIDTeacherEditText.getText().toString().trim().isEmpty()) {
      teacherSignUpinputLayoutSAPId.setError(getString(R.string.err_msg_SAPId));
      requestFocus(sapIDTeacherEditText);
      return false;
    } else if ((sapIDTeacherEditText.getText().toString().length() != 11)) {
      teacherSignUpinputLayoutSAPId.setError(getString(R.string.err_msg_SAPId_inappropriate_length));
      requestFocus(sapIDTeacherEditText);
      return false;
    }else {
      teacherSignUpinputLayoutSAPId.setErrorEnabled(false);
    }

    return true;
  }

  private boolean validatePassword() {
    if (passwordTeacherEditText.getText().toString().trim().isEmpty()) {
      teacherSignUpinputLayoutPassword.setError(getString(R.string.err_msg_password));
      requestFocus(passwordTeacherEditText);
      return false;
    } else if ((passwordTeacherEditText.getText().toString().length() < 5)) {
      teacherSignUpinputLayoutPassword.setError(getString(R.string.err_msg_password_inappropriate_length));
      requestFocus(passwordTeacherEditText);
      return false;
    }else {
      teacherSignUpinputLayoutPassword.setErrorEnabled(false);
    }

    return true;
  }

  private boolean validateDepartment() {
    if(departmentTeacherSpinner.getSelectedItemPosition() == 0) {
      teacherSignUpinputLayoutDepartment.setError(getString(R.string.err_msg_department));
      requestFocusSpinner(departmentTeacherSpinner);
      return false;
    }
    else {
      teacherSignUpinputLayoutDepartment.setError("");
      return true;
    }
  }

  private void requestFocus (View view) {

    if(view.requestFocus()){
      getActivity().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
  }

  private void requestFocusSpinner(View view) {
    if (view.requestFocus()) {
      getActivity().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
  }
  private void hash_password (String password) {

    HashingPassword hashingPassword = new HashingPassword();
    salt = hashingPassword.generate_salt();
    intermediate_password = password + salt;
    hashed_password = hashingPassword.hash_the_password(intermediate_password);
  }
}
