package cn.edu.bistu.step.pedometer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    ListView listView;//列表
    List<Map<String, String>> list;//日期，步数
    StepsDB DB;
    SQLiteDatabase sqLiteDatabase;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = (ListView)findViewById(R.id.his_list);//获取列表
        list = new ArrayList<Map<String, String>>();

        DB = new StepsDB(this);//创建
        sqLiteDatabase = DB.getReadableDatabase();//读取

        refreshList();//刷新列表

    }

    //刷新列表
    public void refreshList(){
        clear();
        simpleAdapter = new SimpleAdapter(this, getAll(), R.layout.item_history,
                new String[]{DB.COLUMN_NAME_DATE, DB.COLUMN_NAME_STEPS},
                new int[]{R.id.tv_date, R.id.tv_step});
        listView.setAdapter(simpleAdapter);
    }
    //清空列表
    public void clear(){
        int count = list.size();
        if(count > 0){//清空
            list.removeAll(list);
            simpleAdapter.notifyDataSetChanged();//重绘
            listView.setAdapter(simpleAdapter);
        }
    }

    //获取全部记录
    public List<Map<String, String>> getAll() {
        Cursor cursor = sqLiteDatabase.query(DB.TABLE_NAME_S, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            Map<String,String> item = new HashMap<String, String>();
            item.put(DB.COLUMN_NAME_DATE,String.valueOf(cursor.getString(cursor.getColumnIndex(DB.COLUMN_NAME_DATE))));
            item.put(DB.COLUMN_NAME_STEPS,String.valueOf(cursor.getString(cursor.getColumnIndex(DB.COLUMN_NAME_STEPS))));
            list.add(item);
        }
        cursor.close();
        return list;
    }

}
