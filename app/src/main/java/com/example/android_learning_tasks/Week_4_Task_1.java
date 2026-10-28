package com.example.android_learning_tasks;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;

public class Week_4_Task_1 extends AppCompatActivity {

    TextInputLayout txtName, txtEmail, txtPhone;
    TextInputEditText edtName, edtEmail, edtPhone;
    TextView tvCityError, tvGenderError, tvTermsError;
    Spinner spinnerCity;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    CheckBox cbTerm;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week4_task1);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        tvCityError = findViewById(R.id.tvCityError);
        tvGenderError = findViewById(R.id.tvGenderError);
        tvTermsError = findViewById(R.id.tvTermsError);
        spinnerCity = findViewById(R.id.spinnerCity);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        cbTerm = findViewById(R.id.cbTerm);
        btnSubmit = findViewById(R.id.btnSubmit);

        setupCitySpinner();

        btnSubmit.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String city = spinnerCity.getSelectedItem().toString();
            String gender = "";
            if (rbMale.isChecked()) gender = "Male";
            if (rbFemale.isChecked()) gender = "Female";
            boolean isChecked = cbTerm.isChecked();

            boolean hasError = false;

            if (name.isEmpty()) {
                txtName.setError("Please enter your name");
                hasError = true;
            } else {
                txtName.setError(null);
            }

            if (email.isEmpty()) {
                txtEmail.setError("Please enter your email address");
                hasError = true;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtEmail.setError("Please enter a valid email address (e.g. user@example.com)");
                hasError = true;
            } else {
                txtEmail.setError(null);
            }

            if (phone.isEmpty()) {
                txtPhone.setError("Please enter your mobile number");
                hasError = true;
            } else if (!phone.matches("^[6-9]\\d{9}$")) {
                txtPhone.setError("Please enter a valid 10-digit mobile number");
                hasError = true;
            } else {
                txtPhone.setError(null);
            }

            if (spinnerCity.getSelectedItemPosition() == 0 || city.equals("Select City")) {
                tvCityError.setText("Please select your city");
                tvCityError.setVisibility(View.VISIBLE);
                hasError = true;
            } else {
                tvCityError.setVisibility(View.GONE);
            }

            if (rgGender.getCheckedRadioButtonId() == -1) {
                tvGenderError.setText("Please select your gender");
                tvGenderError.setVisibility(View.VISIBLE);
                hasError = true;
            } else {
                tvGenderError.setVisibility(View.GONE);
            }

            if (!cbTerm.isChecked()) {
                tvTermsError.setText("Please accept the terms and conditions");
                tvTermsError.setVisibility(View.VISIBLE);
                hasError = true;
            } else {
                tvTermsError.setVisibility(View.GONE);
            }

            if (hasError) return;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Registration Form");
            builder.setMessage("Name: " + name + "\nEmail: " + email + "\nPhone: "
                    + phone + "\nCity: " + city + "\nGender: " + gender
                    + "\nTerms and Conditions: " + (isChecked ? "Accepted" : "Not Accepted"));
            builder.setPositiveButton("OK", (dialog, which) -> {
                clearInputs();
                dialog.dismiss();
            });
            builder.show();
        });
    }

    private void clearInputs() {
        edtName.setText("");
        edtEmail.setText("");
        edtPhone.setText("");
        spinnerCity.setSelection(0);
        rgGender.clearCheck();
        cbTerm.setChecked(false);

        txtName.setError(null);
        txtEmail.setError(null);
        txtPhone.setError(null);
        tvCityError.setVisibility(View.GONE);
        tvGenderError.setVisibility(View.GONE);
        tvTermsError.setVisibility(View.GONE);
    }

    private void setupCitySpinner() {
        ArrayList<String> cities = new ArrayList<>();
        cities.add("Select City");
        cities.add("Surat");
        cities.add("Jetpur");
        cities.add("Rajkot");
        cities.add("Ahmedabad");
        cities.add("Gandhinagar");
        cities.add("Vadodara");
        cities.add("Delhi");
        cities.add("Mumbai");
        cities.add("Kolkata");
        cities.add("Chennai");
        cities.add("Bangalore");
        cities.add("Hyderabad");
        cities.add("Pune");
        cities.add("Jaipur");
        cities.add("Lucknow");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
