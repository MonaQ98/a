package com.example.electricassistant;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.electricassistant.data.FinalData;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class Electricity extends Fragment {
    TextView amount_month;
    TextView money_month;
    TextView amount_year;
    TextView money_year;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    private ElectricityViewModel mViewModel;
    public static Electricity newInstance() {
        return new Electricity();
    }
    private void showLongMessageDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("本月电费余额")
                .setMessage("您本月电费余额为"+FinalData.electricity_month+"度")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.electricity_fragment, container, false);
        amount_month = view.findViewById(R.id.amount_month);
        amount_year = view.findViewById(R.id.amount_year);
        money_month = view.findViewById(R.id.money_month);
        money_year = view.findViewById(R.id.money_year);
        System.out.println(FinalData.amount_year.toString());
        amount_year.setText(FinalData.amount_year.toString());
        amount_month.setText(FinalData.amount_month.toString());
        money_year.setText(FinalData.money_year.toString());
        money_month.setText(FinalData.money_month.toString());



        LinearLayout record = (LinearLayout)view.findViewById(R.id.payment_record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Record.class);
                startActivity(intent);//打开新的activity
            }
        });
        LinearLayout statistic = (LinearLayout)view.findViewById(R.id.statistics);
        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Statistics.class);
                startActivity(intent);//打开新的activity
            }
        });


        LinearLayout pay = (LinearLayout)view.findViewById(R.id.pay_directively);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Payment.class);
                startActivity(intent);//打开新的activity
            }
        });
        LinearLayout recharge = (LinearLayout)view.findViewById(R.id.recharge);
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Recharge.class);
                startActivity(intent);//打开新的activity
            }
        });
        LinearLayout show_sum = (LinearLayout)view.findViewById(R.id.show_sum);
        show_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLongMessageDialog();
            }
        });



        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(ElectricityViewModel.class);
        // TODO: Use the ViewModel
    }

}
