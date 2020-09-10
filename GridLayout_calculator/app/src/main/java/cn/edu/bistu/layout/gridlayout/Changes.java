package cn.edu.bistu.layout.gridlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class Changes extends AppCompatActivity{

    EditText e_binary, e_octal, e_string, e_hex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        e_binary = (EditText)findViewById(R.id.binary);
        e_octal = (EditText)findViewById(R.id.octal);
        e_string = (EditText)findViewById(R.id.string);
        e_hex = (EditText)findViewById(R.id.hex);

        final List<EditText> list = new LinkedList<EditText>();
        list.add(e_binary);
        list.add(e_octal);
        list.add(e_string);
        list.add(e_hex);

        Button s = (Button)findViewById(R.id.switchs);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str;
                for(int i = 0; i < list.size(); i++){
                    str = list.get(i).getText().toString();
                    if(!str.equals("")){
                        if(i==0)
                            fromBinary(str);
                        else if(i==1)
                            fromOctal(str);
                        else if(i==2)
                            fromString(str);
                        else if(i==3)
                            fromHex(str);
                        break;
                    }
                }
            }
        });

        Button c = (Button)findViewById(R.id.clear);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e_binary.setText("");
                e_octal.setText("");
                e_string.setText("");
                e_hex.setText("");
            }
        });


        Button b = (Button)findViewById(R.id.back);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Changes.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //二进制转
    public void fromBinary(String str){
        try {
            e_octal.setText(Integer.toOctalString(Integer.parseInt(str, 2)));
            e_string.setText(Integer.valueOf(str, 2).toString());
            e_hex.setText(Integer.toHexString(Integer.parseInt(str, 2)));
        }catch (Exception e){
            error();
        }
    }

    //八进制转
    public void fromOctal(String str){
        try {
            e_binary.setText(Integer.toBinaryString(Integer.valueOf(str, 8)));
            e_string.setText(Integer.valueOf(str, 8).toString());
            e_hex.setText(Integer.toHexString(Integer.valueOf(str, 8)));
        }catch (Exception e){
            error();
        }
    }

    //十进制转
    public void fromString(String str){
        try {
            e_binary.setText(Integer.toBinaryString(Integer.parseInt(str)));
            e_octal.setText(Integer.toOctalString(Integer.parseInt(str)));
            e_hex.setText(Integer.toHexString(Integer.parseInt(str)));
        }catch (Exception e){
            error();
        }
    }

    //十六进制转
    public void fromHex(String str){
        try {
            e_binary.setText(Integer.toBinaryString(Integer.valueOf(str, 16)));
            e_octal.setText(Integer.toOctalString(Integer.valueOf(str, 16)));
            e_string.setText(Integer.valueOf(str, 16).toString());
        }catch (Exception e){
            error();
        }
    }

    public void error(){
        Toast.makeText(this, "格式错误", Toast.LENGTH_SHORT).show();
    }

}
