package com.example.electricassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.electricassistant.data.FinalData;

import java.math.BigDecimal;

public class Recharge extends AppCompatActivity {
    TextView wallet;
    TextView electricity_month;
    TextView electricity_to_money;

    public static Double Calculate_Electricity_Price(double amount){
        Double price = 0.0;
        if(amount>400){
            price += (amount-400)*0.7883;
            amount = 400;
        }
        if (amount>240){
            price += (amount-240)*0.5383;
            amount = 240;
        }
        price+=amount*0.4883;
        return price;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        wallet = findViewById(R.id.wallet);
        electricity_month = findViewById(R.id.show_electricity_month);
        electricity_to_money = findViewById(R.id.electricity_to_money);
        Double money = 0.0;
        money = Calculate_Electricity_Price(FinalData.electricity_month);
        BigDecimal bg = new BigDecimal(money);

        money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        electricity_to_money.setText("共计"+money+"元");
        wallet.setText(FinalData.wallet+"元");
        electricity_month.setText("您有"+FinalData.electricity_month+"度的用电等待支付");

    }
}
