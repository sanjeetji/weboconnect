package com.sanjeet.weboconnect.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjeet.weboconnect.R;
import com.sanjeet.weboconnect.interfac.OnAndroidRemoveBtnClickListener;
import com.sanjeet.weboconnect.interfac.OnIosRemoveBtnClickListener;
import com.sanjeet.weboconnect.interfac.OnWebRemoveBtnClickListener;
import com.sanjeet.weboconnect.model.Employee;
import com.sanjeet.weboconnect.ui.adapter.AndroidDepartmentAdapter;
import com.sanjeet.weboconnect.ui.adapter.IosDepartmentAdapter;
import com.sanjeet.weboconnect.ui.adapter.WebDepartmentAdapter;
import com.sanjeet.weboconnect.util.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        View.OnClickListener, OnAndroidRemoveBtnClickListener, OnIosRemoveBtnClickListener,
        OnWebRemoveBtnClickListener {

    private List<String> departmentName;
    private AndroidDepartmentAdapter androidDepartmentAdapter;
    private IosDepartmentAdapter iosDepartmentAdapter;
    private WebDepartmentAdapter webDepartmentAdapter;
    private RecyclerView androidRecyclerview, iosRecyclerview, webRecyclerview;
    private List<Employee> androidEmployeeList, iosEmployeeList, webEmployeeList;
    private HashSet<String> androidEmployeeList1, iosEmployeeList1, webEmployeeList1;
    public static HashSet<String> comingAndroidEmp, comingIosEmp, comingWebEmp;
    private ConstraintLayout androidConstraint, iosConstraint, webConstraint;
    private ImageButton androidAddBtn, iosAddBtn, webAddBtn;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
        Spinner departmentSpinner = findViewById(R.id.department_spinner);
        androidRecyclerview = findViewById(R.id.rv_android);
        iosRecyclerview = findViewById(R.id.rv_iso);
        webRecyclerview = findViewById(R.id.rv_web);
        androidConstraint = findViewById(R.id.androidTopView);
        iosConstraint = findViewById(R.id.iosTopView);
        webConstraint = findViewById(R.id.webTopView);
        androidAddBtn = findViewById(R.id.android_img_btn);
        iosAddBtn = findViewById(R.id.ios_img_btn);
        webAddBtn = findViewById(R.id.web_img_btn);

        androidEmployeeList = new ArrayList<Employee>();
        iosEmployeeList = new ArrayList<Employee>();
        webEmployeeList = new ArrayList<Employee>();

        comingAndroidEmp = new HashSet<String>();
        comingIosEmp = new HashSet<String>();
        comingWebEmp = new HashSet<String>();

        androidEmployeeList1 = new HashSet<>();
        iosEmployeeList1 = new HashSet<>();
        webEmployeeList1 = new HashSet<>();

        androidConstraint.setVisibility(View.GONE);
        androidRecyclerview.setVisibility(View.GONE);

        iosConstraint.setVisibility(View.GONE);
        iosRecyclerview.setVisibility(View.GONE);

        webConstraint.setVisibility(View.GONE);
        webRecyclerview.setVisibility(View.GONE);

        //listener
        androidAddBtn.setOnClickListener(this);
        iosAddBtn.setOnClickListener(this);
        webAddBtn.setOnClickListener(this);


        //set Department spinner
        departmentSpinner.setOnItemSelectedListener(this);
        departmentName = new ArrayList<>();
        departmentName.add("Select Department");
        departmentName.add("Android");
        departmentName.add("iOS");
        departmentName.add("Web");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, departmentName);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(dataAdapter);

        comingAndroidEmp = (HashSet<String>) sharedPrefManager.getAndroidEmployee();
        comingIosEmp = (HashSet<String>) sharedPrefManager.getIosEmployee();
        comingWebEmp = (HashSet<String>) sharedPrefManager.getWebEmployee();

        if (comingAndroidEmp.size() > 0) {
            for (int i = 0; i < comingAndroidEmp.size(); i++) {
                String[] employee = comingAndroidEmp.toArray(new String[comingAndroidEmp.size()]);
                androidEmployeeList.add(new Employee(employee[i]));
            }
            androidConstraint.setVisibility(View.VISIBLE);
            androidRecyclerview.setVisibility(View.VISIBLE);
            androidDepartmentAdapter = new AndroidDepartmentAdapter(androidEmployeeList, MainActivity.this, this);
            androidRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            androidRecyclerview.setAdapter(androidDepartmentAdapter);
        }
        if (comingIosEmp.size() > 0) {
            for (int i = 0; i < comingIosEmp.size(); i++) {
                String[] employee = comingIosEmp.toArray(new String[comingIosEmp.size()]);
                iosEmployeeList.add(new Employee(employee[i]));
            }
            iosConstraint.setVisibility(View.VISIBLE);
            iosRecyclerview.setVisibility(View.VISIBLE);
            iosDepartmentAdapter = new IosDepartmentAdapter(iosEmployeeList, MainActivity.this, this);
            iosRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            iosRecyclerview.setAdapter(iosDepartmentAdapter);
        }
        if (comingWebEmp.size() > 0) {
            for (int i = 0; i < comingWebEmp.size(); i++) {
                String[] employee = comingWebEmp.toArray(new String[comingWebEmp.size()]);
                webEmployeeList.add(new Employee(employee[i]));
            }
            webConstraint.setVisibility(View.VISIBLE);
            webRecyclerview.setVisibility(View.VISIBLE);
            webDepartmentAdapter = new WebDepartmentAdapter(webEmployeeList, MainActivity.this, this);
            webRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            webRecyclerview.setAdapter(webDepartmentAdapter);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (androidEmployeeList.size() > 0) {
            for (Employee employee : androidEmployeeList) {
                androidEmployeeList1.add(employee.getEmployeeEmail());
            }

        }

        if (iosEmployeeList.size() > 0) {
            for (Employee employee : iosEmployeeList) {
                iosEmployeeList1.add(employee.getEmployeeEmail());
            }
        }
        if (webEmployeeList.size() > 0) {
            for (Employee employee : webEmployeeList) {
                webEmployeeList1.add(employee.getEmployeeEmail());
            }
        }
        sharedPrefManager.saveAndroidEmployee(androidEmployeeList1);
        sharedPrefManager.saveIosEmployee(iosEmployeeList1);
        sharedPrefManager.saveWebEmployee(webEmployeeList1);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (androidEmployeeList.size() > 0) {
            for (Employee employee : androidEmployeeList) {
                androidEmployeeList1.add(employee.getEmployeeEmail());
            }
        }
        if (iosEmployeeList.size() > 0) {
            for (Employee employee : iosEmployeeList) {
                iosEmployeeList1.add(employee.getEmployeeEmail());
            }
        }
        if (webEmployeeList.size() > 0) {
            for (Employee employee : webEmployeeList) {
                webEmployeeList1.add(employee.getEmployeeEmail());
            }
        }
        sharedPrefManager.saveAndroidEmployee(androidEmployeeList1);
        sharedPrefManager.saveIosEmployee(iosEmployeeList1);
        sharedPrefManager.saveWebEmployee(webEmployeeList1);
    }

    private void setAndroidRecyclerview() {
        //android recyclerview
        androidEmployeeList = new ArrayList<Employee>();
        androidDepartmentAdapter = new AndroidDepartmentAdapter(androidEmployeeList, MainActivity.this, this);
        androidRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        androidEmployeeList.add(new Employee());
        androidRecyclerview.setAdapter(androidDepartmentAdapter);
    }

    private void setIosRecyclerview() {
        //ios recyclerview
        iosEmployeeList = new ArrayList<Employee>();
        iosDepartmentAdapter = new IosDepartmentAdapter(iosEmployeeList, MainActivity.this, this);
        iosRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        iosEmployeeList.add(new Employee());
        iosRecyclerview.setAdapter(iosDepartmentAdapter);
    }

    private void setWebRecyclerview() {
        //web recyclerview
        webEmployeeList = new ArrayList<Employee>();
        webDepartmentAdapter = new WebDepartmentAdapter(webEmployeeList, MainActivity.this, this);
        webRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        webEmployeeList.add(new Employee());
        webRecyclerview.setAdapter(webDepartmentAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == 0) {
            onNothingSelected(adapterView);
        } else {
            String departmentNameStr = departmentName.get(position);

            if (departmentNameStr.equals("Android")) {
                androidConstraint.setVisibility(View.VISIBLE);
                androidRecyclerview.setVisibility(View.VISIBLE);
                if (androidEmployeeList.size() > 0) {
                    Toast.makeText(this, "You have selected already Android department", Toast.LENGTH_SHORT).show();
                } else {
                    setAndroidRecyclerview();
                }
            }
            if (departmentNameStr.equals("iOS")) {
                iosConstraint.setVisibility(View.VISIBLE);
                iosRecyclerview.setVisibility(View.VISIBLE);
                if (iosEmployeeList.size() > 0) {
                    Toast.makeText(this, "You have selected already IOS department", Toast.LENGTH_SHORT).show();
                } else {
                    setIosRecyclerview();
                }
            }
            if (departmentNameStr.equals("Web")) {
                webConstraint.setVisibility(View.VISIBLE);
                webRecyclerview.setVisibility(View.VISIBLE);
                if (webEmployeeList.size() > 0) {
                    Toast.makeText(this, "You have selected already IOS department", Toast.LENGTH_SHORT).show();
                } else {
                    setWebRecyclerview();
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.android_img_btn) {
            androidEmployeeList.add(new Employee());
            setAndroidAdapter();
        } else if (id == R.id.ios_img_btn) {
            iosEmployeeList.add(new Employee());
            setIosAdapter();
        } else if (id == R.id.web_img_btn) {
            webEmployeeList.add(new Employee());
            setWebAdapter();
        }
    }

    private void setAndroidAdapter() {
        androidDepartmentAdapter = new AndroidDepartmentAdapter(androidEmployeeList, this, this);
        androidRecyclerview.setAdapter(androidDepartmentAdapter);
    }

    private void setIosAdapter() {
        iosDepartmentAdapter = new IosDepartmentAdapter(iosEmployeeList, this, this);
        iosRecyclerview.setAdapter(iosDepartmentAdapter);
    }

    private void setWebAdapter() {
        webDepartmentAdapter = new WebDepartmentAdapter(webEmployeeList, this, this);
        webRecyclerview.setAdapter(webDepartmentAdapter);
    }

    @Override
    public void onAndroidRemoveBtnClick(int position) {
        androidEmployeeList.remove(position);
        androidDepartmentAdapter.notifyItemRemoved(position);
        if (androidEmployeeList.size() == 0) {
            androidConstraint.setVisibility(View.GONE);
        }
    }

    @Override
    public void onIosRemoveBtnClick(int position) {
        iosEmployeeList.remove(position);
        iosDepartmentAdapter.notifyItemRemoved(position);
        if (iosEmployeeList.size() == 0) {
            iosConstraint.setVisibility(View.GONE);
        }
    }

    @Override
    public void onWebRemoveBtnClickListener(int position) {
        webEmployeeList.remove(position);
        webDepartmentAdapter.notifyItemRemoved(position);
        if (webEmployeeList.size() == 0) {
            webConstraint.setVisibility(View.GONE);
        }
    }
}