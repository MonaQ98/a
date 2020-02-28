package com.example.electricassistant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.electricassistant.data.model.PaymentItem;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentAdapter extends ArrayAdapter<PaymentItem> {
    private int resourceId;

    public static String dateToStr(java.sql.Timestamp time, String strFormat) {
        DateFormat df = new SimpleDateFormat(strFormat);
        String str = df.format(time);
        return str;
        //将上下文、ListView子项布局的id、数据 传递进来
    }
    public PaymentAdapter(Context context, int textViewResourceId, List<PaymentItem> obj){
        super(context, textViewResourceId, obj);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PaymentItem payment = getItem(position);//获取当前项的Weather实例
        //LayoutInflater的inflate()方法接收3个参数：需要实例化布局资源的id、ViewGroup类型视图组对象、false
        //false表示只让父布局中声明的layout属性生效，但不会为这个view添加父布局
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        //获取实例

        TextView money = (TextView) view.findViewById(R.id.money);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView amount = (TextView) view.findViewById(R.id.amount);
        //设置图片和文字
        DecimalFormat df = new DecimalFormat("0.00");
        amount.setText(df.format(payment.getAmount()));
        money.setText(df.format(payment.getMoney()));
        time.setText(dateToStr(payment.getTime(),"yyyy年MM月dd日 HH:mm:ss"));
        return view;
    }
}
