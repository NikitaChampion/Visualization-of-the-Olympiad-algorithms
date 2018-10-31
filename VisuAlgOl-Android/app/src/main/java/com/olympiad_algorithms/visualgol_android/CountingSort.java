package com.olympiad_algorithms.visualgol_android;

//import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static java.lang.Math.abs;

public class CountingSort extends AppCompatActivity implements View.OnClickListener {

    Button cou_sort;
    Button btnSave;
    EditText edit_text;
    private int childPosition = 0;
    private TextView []txt_num;
    private TextView []index;
    private TextView []numb;
    final Random random = new Random();
    private int []numbers = new int[8];
    private int []numbers_2 = new int[7];
    private Handler handler = new Handler();

    private final static String FILE_NAME = "qwerty.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_sort);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null)
            childPosition = arguments.getInt("num", 0);

        txt_num = new TextView[8];
        txt_num[0] = findViewById(R.id.txt_num1);
        txt_num[1] = findViewById(R.id.txt_num2);
        txt_num[2] = findViewById(R.id.txt_num3);
        txt_num[3] = findViewById(R.id.txt_num4);
        txt_num[4] = findViewById(R.id.txt_num5);
        txt_num[5] = findViewById(R.id.txt_num6);
        txt_num[6] = findViewById(R.id.txt_num7);
        txt_num[7] = findViewById(R.id.txt_num8);

        numb = new TextView[7];
        numb[0] = findViewById(R.id.num1);
        numb[1] = findViewById(R.id.num2);
        numb[2] = findViewById(R.id.num3);
        numb[3] = findViewById(R.id.num4);
        numb[4] = findViewById(R.id.num5);
        numb[5] = findViewById(R.id.num6);
        numb[6] = findViewById(R.id.num7);

        index = new TextView[7];
        index[0] = findViewById(R.id.ind1);
        index[1] = findViewById(R.id.ind2);
        index[2] = findViewById(R.id.ind3);
        index[3] = findViewById(R.id.ind4);
        index[4] = findViewById(R.id.ind5);
        index[5] = findViewById(R.id.ind6);
        index[6] = findViewById(R.id.ind7);

        for (int i = 0; i < numbers.length; ++i)
            numbers[i] = abs(random.nextInt()) % 7;

        for (int i = 0; i < index.length; ++i)
            index[i].setText(String.valueOf(i));

        cou_sort = findViewById(R.id.cou_sort);
        cou_sort.setOnClickListener(this);

        edit_text = findViewById(R.id.edit_text);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        ContestSet();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cou_sort:
                handler.removeCallbacksAndMessages(null);
                ContestSet();
                counting_sort();
                break;
            case R.id.btnSave:
                if (edit_text.getText().toString().equals("1 2 3"))
                    saveText('1');
                else saveText('0');
                break;
            default:
                break;
        }
    }

    public void ContestSet() {
        for (int i = 0; i < numbers.length; ++i) {
            txt_num[i].setText(String.valueOf(numbers[i]));
            txt_num[i].setBackgroundResource(R.drawable.rectangle_search_1);
        }
        for (int i = 0; i < numbers_2.length; ++i) {
            numbers_2[i] = 0;
            numb[i].setText(String.valueOf(numbers_2[i]));
            index[i].setBackgroundResource(R.drawable.rectangle_white);
        }
    }

    public void counting_sort(){
        animation_counting();
    }

    public void animation_counting() {
        long current = 1;
        for (int i = 0; i < numbers.length; ++i) {
            final int x = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    txt_num[x].setBackgroundResource(R.drawable.rectangle_search_3);
                    index[numbers[x]].setBackgroundResource(R.drawable.rectangle_search_3);
                }
            }, 1250*current);
            ++current;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ++numbers_2[numbers[x]];
                    txt_num[x].setText("");
                    txt_num[x].setBackgroundResource(R.drawable.rectangle_search_1);
                    numb[numbers[x]].setText(String.valueOf(numbers_2[numbers[x]]));
                    index[numbers[x]].setBackgroundResource(R.drawable.rectangle_white);
                }
            }, 1250*current);
            ++current;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int curIndex = 0;
                for (int i = 0; i < numbers_2.length; ++i) {
                    while (numbers_2[i] > 0) {
                        --numbers_2[i];
                        numb[i].setText(String.valueOf(numbers_2[i]));
                        txt_num[curIndex].setText(String.valueOf(i));
                        ++curIndex;
                        if (curIndex == txt_num.length) return;
                    }
                }
            }
        }, 1250*current);
    }

    static String convertStreamToString(FileInputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public String loadText() {
        try {
            FileInputStream fin = openFileInput(FILE_NAME);
            String str = convertStreamToString(fin);
            fin.close();
            return str;
        } catch(IOException ex) {
            //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            StringBuilder curBuilder = new StringBuilder();
            for (int i = 0; i < 100; ++i)
                curBuilder.append('0');
            return curBuilder.toString();
        }
    }
    public void saveText(char ch) {
        char[] c = loadText().toCharArray();
        c[childPosition] = ch;
        String str = new String(c);
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(str.getBytes());
            fos.close();
        } catch(IOException ex) {
            //Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (ch == '1') Toast.makeText(this, "Right answer, text saved", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Wrong answer, try again", Toast.LENGTH_SHORT).show();
    }
}