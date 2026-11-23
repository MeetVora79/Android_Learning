package com.example.android_learning_tasks;

import android.app.AlertDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;

public class Week_9_Task_3 extends AppCompatActivity {

    TextInputLayout txtName, txtEmail, txtPhone;
    TextInputEditText edtName, edtEmail, edtPhone;
    TextView tvCityError, tvGenderError, tvTermsError;
    LinearLayout formRoot;
    Spinner spinnerCity;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    CheckBox cbTerm;
    Button btnSubmit;
    private static final String CITY_ERR = "city_err";
    private static final String GENDER_ERR = "gender_err";
    private static final String TERMS_ERR = "terms_err";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(CITY_ERR, tvCityError.getVisibility() == View.VISIBLE);
        outState.putBoolean(GENDER_ERR, tvGenderError.getVisibility() == View.VISIBLE);
        outState.putBoolean(TERMS_ERR, tvTermsError.getVisibility() == View.VISIBLE);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean(CITY_ERR)) validateCity();
        if (savedInstanceState.getBoolean(GENDER_ERR)) validateGender();
        if (savedInstanceState.getBoolean(TERMS_ERR)) validateTerms();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.week9_task3);

        formRoot = findViewById(R.id.formRoot);
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
        InputFilter nameFilter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (!Character.isLetter(c) && c != ' ') {
                    return "";
                }
            }
            return null;
        };
        edtName.setFilters(new InputFilter[]{
                nameFilter,
                new InputFilter.LengthFilter(50)
        });
        setupLiveValidation(edtName, this::validateName);
        setupLiveValidation(edtEmail, this::validateEmail);
        setupLiveValidation(edtPhone, this::validatePhone);
        setupChoiceListeners();

        edtPhone.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                formRoot.requestFocus();
                return true;
            }
            return false;
        });

        btnSubmit.setOnClickListener(v -> {
            boolean nameOk = validateName();
            boolean emailOk = validateEmail();
            boolean phoneOk = validatePhone();
            boolean cityOk = validateCity();
            boolean genderOk = validateGender();
            boolean termsOk = validateTerms();

            View firstInvalid = null;
            if (!nameOk) firstInvalid = edtName;
            else if (!emailOk) firstInvalid = edtEmail;
            else if (!phoneOk) firstInvalid = edtPhone;
            else if (!cityOk) firstInvalid = spinnerCity;
            else if (!genderOk) firstInvalid = rbMale;
            else if (!termsOk) firstInvalid = cbTerm;

            if(firstInvalid != null){
                requestFocus(firstInvalid);
                btnSubmit.announceForAccessibility(
                        "Form has errors. Please correct the highlighted fields.");
                return;
            }
            showSuccessDialog();
        });
    }

    private void showSuccessDialog() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String city = spinnerCity.getSelectedItem().toString();
        String gender = rbMale.isChecked() ? "Male" : "Female";

        new AlertDialog.Builder(this)
                .setTitle("Registration Successful")
                .setMessage("Name: " + name + "\nEmail: " + email + "\nPhone: " + phone
                        + "\nCity: " + city + "\nGender: " + gender
                        + "\nTerms and Conditions: Accepted")
                .setPositiveButton("OK", (dialog, which) -> {
                    clearInputs();
                    dialog.dismiss();
                })
                .show();
    }

    private void requestFocus(View target){
        target.setFocusableInTouchMode(true);
        target.requestFocus();

        target.requestRectangleOnScreen(
                new Rect(0, 0, target.getWidth(), target.getHeight()), false);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(imm == null) return;

        if(target instanceof TextInputEditText){
            imm.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(target.getWindowToken(), 0);
        }
    }

    private boolean validateTerms() {
        if (!cbTerm.isChecked()) {
            tvTermsError.setText("Please accept the terms and conditions");
            tvTermsError.setVisibility(View.VISIBLE);
            return false;
        }
        tvTermsError.setVisibility(View.GONE);
        return true;
    }

    private boolean validateGender() {
        if (rgGender.getCheckedRadioButtonId() == -1) {
            tvGenderError.setText("Please select your gender");
            tvGenderError.setVisibility(View.VISIBLE);
            return false;
        }
        tvGenderError.setVisibility(View.GONE);
        return true;
    }

    private boolean validateCity() {
        if(spinnerCity.getSelectedItemPosition() == 0){
            tvCityError.setText("Please select your city");
            tvCityError.setVisibility(View.VISIBLE);
            return false;
        }
        tvCityError.setVisibility(View.GONE);
        return true;
    }

    private boolean validatePhone() {
        String phone = edtPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            txtPhone.setError("Please enter your mobile number");
            return false;
        }
        if (!phone.matches("^[6-9]\\d{9}$")) {
            txtPhone.setError("Enter a valid 10-digit phone number starting with 6-9");
            return false;
        }
        txtPhone.setError(null);
        return true;
    }

    private boolean validateEmail() {
        String email = edtEmail.getText().toString().trim();
        if (email.isEmpty()){
            txtEmail.setError("Please enter your email");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("Please enter a valid email address (e.g. user@example.com)");
            return false;
        }
        txtEmail.setError(null);
        return true;
    }

    private boolean validateName() {
        String name = edtName.getText().toString().trim();
        if (name.isEmpty()) {
            txtName.setError("Please enter your name");
            return false;
        }
        if(name.length() < 3){
            txtName.setError("Name must be at least 3 characters");
            return false;
        }
        if(!name.matches("^[A-Za-z ]+$")){
            txtName.setError("Name must contain only letters and spaces");
            return false;
        }
        txtName.setError(null);
        return true;
    }

    private void setupLiveValidation(TextInputEditText edt, Runnable validator) {
        edt.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) validator.run();
        });

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                TextInputLayout layout = (TextInputLayout) edt.getParent().getParent();
                if (layout.getError() != null) validator.run();
            }
        });
    }
    private void setupChoiceListeners() {
        rgGender.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) validateGender();
        });

        cbTerm.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (tvTermsError.getVisibility() == View.VISIBLE) validateTerms();
        });
    }


    private void clearInputs() {
        formRoot.requestFocus();

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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (tvCityError.getVisibility() == View.VISIBLE) validateCity();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
