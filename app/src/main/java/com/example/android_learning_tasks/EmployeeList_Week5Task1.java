package com.example.android_learning_tasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class EmployeeList_Week5Task1 extends AppCompatActivity {

    ListView lvEmployee;
    ArrayList<EmployeeModel_w5_t1> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.employee_list_w5_t1);

        initializeEmployees();

        lvEmployee = findViewById(R.id.lvEmployee);

        ArrayAdapter<EmployeeModel_w5_t1> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employees);
        lvEmployee.setAdapter(adapter);

        lvEmployee.setOnItemClickListener((parent, view, position, id) -> {
            EmployeeModel_w5_t1 selectedEmployee = employees.get(position);
            Intent intent = new Intent(this, EmployeeDetail_Week5Task1.class);
            intent.putExtra("empId", selectedEmployee.getId());
            intent.putExtra("empName", selectedEmployee.getName());
            intent.putExtra("empRole", selectedEmployee.getRole());
            startActivity(intent);
        });
    }

    private void initializeEmployees() {
        employees = new ArrayList<>();
        employees.add(new EmployeeModel_w5_t1(101, "Meet Vora", "Android Developer"));
        employees.add(new EmployeeModel_w5_t1(102, "Harsh Patel", "Full Stack Developer"));
        employees.add(new EmployeeModel_w5_t1(103, "Riya Shah", "UI/UX Designer"));
        employees.add(new EmployeeModel_w5_t1(104, "Amit Desai", "Backend Developer"));
        employees.add(new EmployeeModel_w5_t1(105, "Neha Mehta", "QA Engineer"));
        employees.add(new EmployeeModel_w5_t1(106, "Karan Joshi", "DevOps Engineer"));
        employees.add(new EmployeeModel_w5_t1(107, "Priya Trivedi", "React.js Developer"));
        employees.add(new EmployeeModel_w5_t1(108, "Rahul Parmar", "Data Analyst"));
        employees.add(new EmployeeModel_w5_t1(109, "Sneha Gandhi", "Project Manager"));
        employees.add(new EmployeeModel_w5_t1(110, "Vivek Solanki", "iOS Developer"));
        employees.add(new EmployeeModel_w5_t1(111, "Dhruv Chauhan", "Node.js Developer"));
        employees.add(new EmployeeModel_w5_t1(112, "Anjali Bhatt", "Business Analyst"));
        employees.add(new EmployeeModel_w5_t1(113, "Jay Thakkar", "Flutter Developer"));
        employees.add(new EmployeeModel_w5_t1(114, "Pooja Rana", "Technical Writer"));
        employees.add(new EmployeeModel_w5_t1(115, "Nirav Modi", "System Administrator"));
        employees.add(new EmployeeModel_w5_t1(116, "Kavya Pandya", "Machine Learning Engineer"));
        employees.add(new EmployeeModel_w5_t1(117, "Hardik Vyas", "Database Administrator"));
        employees.add(new EmployeeModel_w5_t1(118, "Isha Dave", "Frontend Developer"));
        employees.add(new EmployeeModel_w5_t1(119, "Yash Rathod", "Kotlin Developer"));
        employees.add(new EmployeeModel_w5_t1(120, "Mansi Kapadia", "Cloud Engineer"));
    }
}
