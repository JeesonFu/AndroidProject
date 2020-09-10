package cn.edu.bistu.layout.gridlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt0,bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8,bt9,btC,btDel,btAdd,btSub,btMul,btDvd,btEq,btDot;
    Button bt_square,bt_squareOf,bt_recip,bt_fact,bt_P,bt_E,bt_lg,bt_ln;
    Button bt_sin,bt_cos,bt_tan,bt_sin1,bt_cos1,bt_tan1;
    TextView result;

    boolean operate = false;//是否已输入运算符
    boolean point = false;//是否已输入小数点
    boolean nxt_form = false;//true为可以输入下一运算数
    boolean jc_fact = false;//阶乘

    private static final int DEF_DIV_SCALE = 10;//商，保留10位小数

    //优先级
    static Map<String, Integer> priority = new HashMap<String, Integer>(){
        {
            put("+", 1);
            put("-", 1);
            put("*", 2);
            put("/", 2);
            put("^", 3);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button s = (Button)findViewById(R.id.switchs);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Changes.class);
                startActivity(intent);
                finish();
            }
        });

        result = (TextView)findViewById(R.id.view);

        btC = (Button)findViewById(R.id.clear);
        btDel = (Button)findViewById(R.id.delete);
        bt0 = (Button)findViewById(R.id.zero);
        bt1 = (Button)findViewById(R.id.one);
        bt2 = (Button)findViewById(R.id.two);
        bt3 = (Button)findViewById(R.id.three);
        bt4 = (Button)findViewById(R.id.four);
        bt5 = (Button)findViewById(R.id.five);
        bt6= (Button)findViewById(R.id.six);
        bt7 = (Button)findViewById(R.id.seven);
        bt8 = (Button)findViewById(R.id.eight);
        bt9 = (Button)findViewById(R.id.nine);
        btAdd = (Button)findViewById(R.id.add);
        btSub = (Button)findViewById(R.id.sub);
        btMul = (Button)findViewById(R.id.multi);
        btDvd = (Button)findViewById(R.id.divide);
        btEq = (Button)findViewById(R.id.equal);
        btDot = (Button)findViewById(R.id.dot);

        bt_square = (Button)findViewById(R.id.square);
        bt_squareOf = (Button)findViewById(R.id.squareOf);
        bt_recip = (Button)findViewById(R.id.recip);
        bt_fact = (Button)findViewById(R.id.fact);
        bt_lg = (Button)findViewById(R.id.lg);
        bt_ln = (Button)findViewById(R.id.ln);
        bt_P = (Button)findViewById(R.id.pi);
        bt_E = (Button)findViewById(R.id.e);

        bt_sin = (Button)findViewById(R.id.sin);
        bt_cos = (Button)findViewById(R.id.cos);
        bt_tan = (Button)findViewById(R.id.tan);
        bt_sin1 = (Button)findViewById(R.id.sin1);
        bt_cos1 = (Button)findViewById(R.id.cos1);
        bt_tan1 = (Button)findViewById(R.id.tan1);

        btC.setOnClickListener(this);
        btDel.setOnClickListener(this);
        bt0.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        btAdd.setOnClickListener(this);
        btSub.setOnClickListener(this);
        btMul.setOnClickListener(this);
        btDvd.setOnClickListener(this);
        btEq.setOnClickListener(this);
        btDot.setOnClickListener(this);

        bt_square.setOnClickListener(this);
        bt_squareOf.setOnClickListener(this);
        bt_recip.setOnClickListener(this);
        bt_fact.setOnClickListener(this);
        bt_lg.setOnClickListener(this);
        bt_ln.setOnClickListener(this);
        bt_P.setOnClickListener(this);
        bt_E.setOnClickListener(this);

        bt_sin.setOnClickListener(this);
        bt_cos.setOnClickListener(this);
        bt_tan.setOnClickListener(this);
        bt_sin1.setOnClickListener(this);
        bt_cos1.setOnClickListener(this);
        bt_tan1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        String res = result.getText().toString();
        switch (view.getId()){
            case R.id.clear:
                nxt_form=false;
                operate=false;
                point=false;
                res="0";
                result.setText("0");
                break;
            case R.id.delete:
                if(res.equals(""))
                    break;
                else
                    result.setText(res.substring(0,res.length()-1));
                break;

            case R.id.zero:
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
                if(nxt_form && !operate){
                    nxt_form=false;
                    operate=false;
                    point=false;
                    res="0";
                    result.setText("0");
                }
                operate=false;
                if(res.equals("0"))
                    result.setText(((Button)view).getText());
                else
                   result.setText(res + ((Button)view).getText());
                break;

            case R.id.add:
            case R.id.sub:
            case R.id.multi:
            case R.id.divide:
                if(operate) {
                    String tmp = res.substring(0,res.length()-1);
                    result.setText(tmp + ((Button)view).getText());
                } else {
                    result.setText(res + ((Button)view).getText());
                    operate = true;
                }
                point=false;
                break;
            case R.id.dot:
                if(!point && !operate){
                    point=true;
                    result.setText(res + ((Button)view).getText());
                } else
                    return;
                break;
            case R.id.equal:
                calculate((String)result.getText());
                break;

            case R.id.square:
                if(operate) {
                    String tmp = res.substring(0, res.length() - 1);
                    result.setText(tmp + "^2");
                }else
                    result.setText(res + "^2");
                operate=false;
                point=false;
                break;
            case R.id.squareOf:
                if(operate) {
                    String tmp = res.substring(0,res.length()-1);
                    result.setText(tmp + "^");
                } else {
                    result.setText(res + "^");
                    operate = true;
                }
                point=false;
                break;
            case R.id.pi:
                result.setText(String.valueOf(Math.PI));
                break;
            case R.id.e:
                result.setText(String.valueOf(Math.E));
                break;
            case R.id.recip:
                reciprocal(res);
                break;
            case R.id.fact:
                if(jc_fact) {
                    jc_fact=false;
                    error(4);
                    break;
                }
                factorial(res);
                jc_fact=true;
                break;
            case R.id.lg:
                logarithm(res,1);
                break;
            case R.id.ln:
                logarithm(res,2);
                break;

            case R.id.sin:
                tri(res,1);
                break;
            case R.id.cos:
                tri(res,2);
                break;
            case R.id.tan:
                tri(res,3);
                break;
            case R.id.sin1:
                arctri(res,1);
                break;
            case R.id.cos1:
                arctri(res,2);
                break;
            case R.id.tan1:
                arctri(res,3);
                break;
        }
    }

    //计算函数
    public void calculate(String formula) {
        nxt_form=true;//可以进行下一个运算
        String[] resOfSplit = split(formula);//中缀
        try {
            List<String> after = toAfter(resOfSplit);//后缀
            getValue(after);//获得结果
        } catch (Exception e) {
            error(1);
        }
    }

    //对表达式进行分割
    public String[] split(String str){
        int preOp=-1;//上一个运算符的位置
        StringBuffer sb=new StringBuffer();
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) != '.' && (str.charAt(i) < '0' || str.charAt(i) > '9')){
                if(i-1==preOp){ //连续两个运算符
                    sb.append(str.charAt(i)+"#");
                } else
                    sb.append("#"+str.charAt(i)+"#");
                preOp=i;//更新当前最后一个运算符的位置
            }else{
                sb.append(str.charAt(i));
            }
        }
        String[] split = sb.toString().split("#");
        return split;
    }

    //转为后缀表达式
    public List<String> toAfter(String [] ros) throws Exception{
        LinkedList<String> after = new LinkedList<String>();
        LinkedList<String> opTmp = new LinkedList<String>();//临时，运算符栈
        for(String s:ros){ //遍历分割后的算式数组
            if(priority.get(s)==null){//数字
                after.add(s);
            }else {//运算符
                while(!opTmp.isEmpty() && priority.get(s)<=priority.get(opTmp.peek())){
                    after.add(opTmp.pop());
                }
                opTmp.push(s);
            }
        }
        while(!opTmp.isEmpty())
            after.add(opTmp.pop());
        return after;
    }

    //计算结果
    public void getValue(List<String> after) throws Exception{//求值
        LinkedList<Double> n = new LinkedList<Double>();//临时，数据栈
        for(String s:after){
            if(priority.get(s)!=null){//运算符
                BigDecimal y = new BigDecimal(Double.toString(n.pop()));
                BigDecimal x = new BigDecimal(Double.toString(n.pop()));
                if(s.equals("+"))
                    n.push(x.add(y).doubleValue());
                else if(s.equals("-"))
                    n.push(x.subtract(y).doubleValue());
                else if(s.equals("*"))
                    n.push(x.multiply(y).doubleValue());
                else if(s.equals("/")){
                    if(y.doubleValue()==0) {
                        error(5);
                        return;
                    }
                        n.push(x.divide(y, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue());
                }else if(s.equals("^"))
                    n.push(Math.pow(x.doubleValue(),y.doubleValue()));

            }else{
                n.push(Double.valueOf(s));
            }
        }
        fixRes(n.pop());
    }

    //结果修正
    public void fixRes(Double res) throws Exception{
        if(res>Double.MAX_VALUE){
            error(3);
            return;
        } else if(res>Integer.MAX_VALUE){
            result.setText(new DecimalFormat("#.#########E0").format(res));
            return;
        } else {
            if (res % 1 == 0) {
                result.setText(String.valueOf(res.intValue()));
            } else
                result.setText(String.valueOf(res));
        }
    }

    //错误提示
    public void error(int i){
        switch (i){
            case 1:
                Toast.makeText(this, "格式错误或暂不支持此类运算", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "请输入一个数字", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "数值过大，请重新输入", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "请重新输入数字", Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(this, "除数不能为0", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(this, "数字不能为0", Toast.LENGTH_SHORT).show();
                break;
            case 7:
                Toast.makeText(this, "请输入角度数", Toast.LENGTH_SHORT).show();
                break;
        }
        result.setText("0");
    }

    //倒数计算
    public void reciprocal(String str){
        try {
            BigDecimal y = new BigDecimal(str);
            BigDecimal x = new BigDecimal(1.0);
            fixRes(x.divide(y, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue());
        } catch (Exception e) {
            error(2);
        }
    }

    //阶乘计算
    public void factorial(String str){
        try {
            BigDecimal n = new BigDecimal(str);
            Double r=1.0;
            for(int i = 1; i <= n.intValue(); i++){
                r *= i;
            }
            fixRes(r);
        } catch (Exception e) {
            error(2);
        }
    }

    //对数计算
    public void logarithm(String str, int i){
        try{
            Double n = Double.parseDouble(str);
            if(n==0){
                error(6);
                return;
            }
            if(i==1)
                fixRes(Math.log(n)/Math.log(10));
            else if(i==2)
                fixRes(Math.log(n)/Math.log(Math.E));
        }catch (Exception e){
            error(2);
        }
    }

    //三角函数计算
    public void tri(String str, int i){
        try{
            Double n = Math.toRadians(Double.parseDouble(str));
            if(i==1){
                fixRes(Math.sin(n));
            } else if(i==2){
                fixRes(Math.cos(n));
            } else if(i==3){
                fixRes(Math.tan(n));
            }
        }catch (Exception e){
            error(7);
        }
    }
    public void arctri(String str, int i){
        try{
            Double n = Double.parseDouble(str);
            if(i==1){
                if(n<-1 || n>1) {
                    error(4);
                    return;
                }
                fixRes(Math.toDegrees(Math.asin(n)));
            } else if(i==2){
                if(n<-1 || n>1) {
                    error(4);
                    return;
                }
                fixRes(Math.toDegrees(Math.acos(n)));
            } else if(i==3){
                fixRes(Math.toDegrees(Math.atan(n)));
            }
        }catch (Exception e){
            error(4);
        }
    }
}
