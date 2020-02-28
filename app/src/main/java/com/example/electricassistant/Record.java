package com.example.electricassistant;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.electricassistant.data.FinalData;
import com.example.electricassistant.data.model.PaymentItem;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Record extends AppCompatActivity {
    private List<PaymentItem> paymentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initPayment();//初始化天气数据
        PaymentAdapter adapter = new PaymentAdapter(Record.this, R.layout.payment_item, paymentList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private void initPayment() {
        paymentList = FinalData.payments;

    }
}
