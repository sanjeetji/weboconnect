package com.sanjeet.weboconnect.ui.adapter;

import static com.sanjeet.weboconnect.ui.MainActivity.comingAndroidEmp;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjeet.weboconnect.R;
import com.sanjeet.weboconnect.interfac.OnAndroidRemoveBtnClickListener;
import com.sanjeet.weboconnect.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class AndroidDepartmentAdapter extends RecyclerView.Adapter<AndroidDepartmentAdapter.ViewHolder> {

    private long delay = 2000;
    private long last_text_edit = 0;
    private Handler handler = new Handler();
    private List<Employee> employeeList;
    private Context context;
    private OnAndroidRemoveBtnClickListener onAndroidRemoveBtnClickListener;
    private List<String> checkData, comingData;
    private String etValue;

    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                checkData.add(etValue);
            }
        }
    };

    public AndroidDepartmentAdapter(List<Employee> employeeList, Context context, OnAndroidRemoveBtnClickListener onAndroidRemoveBtnClickListener) {
        this.employeeList = employeeList;
        this.context = context;
        this.onAndroidRemoveBtnClickListener = onAndroidRemoveBtnClickListener;
    }

    @NonNull
    @Override
    public AndroidDepartmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_row_item, parent, false);
        return new ViewHolder(view, onAndroidRemoveBtnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AndroidDepartmentAdapter.ViewHolder holder, int position) {
        checkData = new ArrayList<>();
        comingData = new ArrayList<>();
        Employee employee = employeeList.get(position);

        if (comingAndroidEmp.size() > 0) {
            for (int i = 0; i < comingAndroidEmp.size(); i++) {
                String[] employee1 = comingAndroidEmp.toArray(new String[comingAndroidEmp.size()]);
                comingData.add(employee1[i]);
            }
        }

        if (employee.getEmployeeEmail() != null) {
            holder.etEmployeeEmail.setText(employee.getEmployeeEmail());
        } else {
            employee.setEmployeeEmail(holder.etEmployeeEmail.getText().toString());
        }
        holder.etEmployeeEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);
                if (checkData.contains(s.toString())) {
                    holder.etEmployeeEmail.setError("You can't add duplicate employee");
                    holder.etEmployeeEmail.requestFocus();
                    return;
                }

                if (comingData != null) {
                    if (comingData.contains(s.toString())) {
                        holder.etEmployeeEmail.setError("You can't add duplicate employee");
                        holder.etEmployeeEmail.requestFocus();
                        return;
                    }
                }

                employee.setEmployeeEmail(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
                etValue = s.toString();
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                    Log.e("====", "If");
                } else {
                    Log.e("====", "Else");
                }

            }
        });
        holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    holder.etEmployeeEmail.setEnabled(false);
                } else {
                    holder.etEmployeeEmail.setEnabled(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private EditText etEmployeeEmail;
        private TextView tvRemove;
        private SwitchCompat switchCompat;
        OnAndroidRemoveBtnClickListener onAndroidRemoveBtnClickListener;

        public ViewHolder(@NonNull View itemView, OnAndroidRemoveBtnClickListener onAndroidRemoveBtnClickListener) {
            super(itemView);
            this.onAndroidRemoveBtnClickListener = onAndroidRemoveBtnClickListener;
            etEmployeeEmail = itemView.findViewById(R.id.et_employee_email);
            tvRemove = itemView.findViewById(R.id.tv_remove);
            switchCompat = itemView.findViewById(R.id.switchBtn);

            tvRemove.setOnClickListener(view -> {
                if (onAndroidRemoveBtnClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onAndroidRemoveBtnClickListener.onAndroidRemoveBtnClick(position);
                    }
                }
            });
        }
    }
}
