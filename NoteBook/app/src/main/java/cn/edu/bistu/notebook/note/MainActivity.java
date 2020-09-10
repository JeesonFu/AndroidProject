package cn.edu.bistu.notebook.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    //private Button bt_add;//新建备忘录按钮
    private TextView itemContent;//item的内容部分
    private ListView listView;//列表
    private NotesDB nDB;//数据库创建
    private List<Map<String,String>> list;//所有item的集合
    private SimpleAdapter simpleAdapter;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.notelist);//获取列表
        list = new ArrayList<Map<String, String>>();

        nDB = new NotesDB(this);//创建
        sqLiteDatabase = nDB.getReadableDatabase();//读取

        refreshList();

        listView.setOnItemClickListener(this);//item点击事件
        listView.setOnItemLongClickListener(this);//item长按事件
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_create:
                create();
                return true;
            case R.id.action_search:
                search();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void create(){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        EditActivity.newtext = true;
        intent.putExtra("content", "");
        startActivityForResult(intent,1);
    }

    public void search(){
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.search, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("查找记录");
        builder.setView(tableLayout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int n) {
                String keyWord = ((EditText)tableLayout.findViewById(R.id.keyWord)).getText().toString();
                refreshSearch(keyWord);
            }
        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
        builder.create();
        builder.show();
    }

    public void refreshSearch(String key){
        clear();

        String sql = "select * from notes where content like ? order by content desc";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{"%" + key + "%"});
        while (cursor.moveToNext()){
            Map<String,String> item = new HashMap<String, String>();
            item.put(nDB.COLUMN_NAME_DATE,String.valueOf(cursor.getString(cursor.getColumnIndex(nDB.COLUMN_NAME_DATE))));
            item.put(nDB.COLUMN_NAME_CONTENT,String.valueOf(cursor.getString(cursor.getColumnIndex(nDB.COLUMN_NAME_CONTENT))));
            list.add(item);
        }
        cursor.close();

        simpleAdapter = new SimpleAdapter(this, list, R.layout.item,
                new String[]{nDB.COLUMN_NAME_DATE, nDB.COLUMN_NAME_CONTENT},
                new int[]{R.id.itemDate, R.id.itemContent});
        listView.setAdapter(simpleAdapter);

        final Button button = (Button)findViewById(R.id.clean);
        button.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshList();
                button.setVisibility(View.INVISIBLE);
            }
        });
    }

    //刷新列表
    public void refreshList(){
        clear();

        simpleAdapter = new SimpleAdapter(this, getAll(), R.layout.item,
                new String[]{nDB.COLUMN_NAME_DATE, nDB.COLUMN_NAME_CONTENT},
                new int[]{R.id.itemDate, R.id.itemContent});
        listView.setAdapter(simpleAdapter);
    }
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
        Cursor cursor = sqLiteDatabase.query(nDB.TABLE_NAME, null, "content!=\"\"", null, null, null, null);
        while (cursor.moveToNext()){
            Map<String,String> item = new HashMap<String, String>();
            item.put(nDB.COLUMN_NAME_DATE,String.valueOf(cursor.getString(cursor.getColumnIndex(nDB.COLUMN_NAME_DATE))));
            item.put(nDB.COLUMN_NAME_CONTENT,String.valueOf(cursor.getString(cursor.getColumnIndex(nDB.COLUMN_NAME_CONTENT))));
            list.add(item);
        }
        cursor.close();
        return list;
    }

    //点击一个Item
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //获得listview中被点击item的内容
        String listitem = listView.getItemAtPosition(i) + "";
        //获得该item中的所需内容
        String itemcontent = listitem.substring(listitem.indexOf("=") + 1, listitem.indexOf(","));
        //在数据库中查找该内容的对应项
        Cursor cursor = sqLiteDatabase.query("notes", null,
                "content=" + "'" + itemcontent + "'", null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            Intent intent = new Intent();
            EditActivity.id = Integer.parseInt(id);//获取并设置当前item的ID
            EditActivity.newtext = false;
            intent.putExtra("content",itemcontent);
            intent.setClass(MainActivity.this, EditActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 2)
            refreshList();
    }

    //长按删除
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("删除记录");
        builder.setMessage("确认删除？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int n) {
                    String listitem = listView.getItemAtPosition(i) + "";
                    String itemcontent = listitem.substring(listitem.indexOf("=") + 1, listitem.indexOf(","));

                    Cursor cursor = sqLiteDatabase.query("notes", null,
                            "content=" + "'" + itemcontent + "'", null, null, null, null);

                    while (cursor.moveToNext()) {
                        String id = cursor.getString(cursor.getColumnIndex("_id"));
                        String delete = "Delete from notes where _id=" + id;
                        sqLiteDatabase.execSQL(delete);
                        refreshList();
                    }
                }
            })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {                    }
                });
        builder.create();
        builder.show();
        return true;
    }
}
