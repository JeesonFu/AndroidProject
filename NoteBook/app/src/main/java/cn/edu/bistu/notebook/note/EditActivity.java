package cn.edu.bistu.notebook.note;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private TextView editDate;
    private EditText editContent;
    private NotesDB nDB;
    private SQLiteDatabase sqLiteDatabase;

    public String oldcontent;
    public static boolean newtext;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        editDate = (TextView)findViewById(R.id.editDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strDate = sdf.format(new Date());
        editDate.setText(strDate);

        nDB = new NotesDB(this);//创建
        sqLiteDatabase = nDB.getReadableDatabase();//读取

        editContent = (EditText)findViewById(R.id.editContent);
        oldcontent = intent.getStringExtra("content");
        editContent.setText(oldcontent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
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
            case R.id.action_save:
                String newcontent = editContent.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strDate = sdf.format(new Date());

                if(newtext)
                    Insert(newcontent, strDate);
                else
                    Update(newcontent, strDate);

                Intent intent = new Intent();
                setResult(2,intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Insert(String newcontent, String strDate){
        if(!newcontent.equals("")){
            String sql_insert = "Insert into notes(date, content) values('" + strDate + "', '" + newcontent + "')";
            sqLiteDatabase.execSQL(sql_insert);
        }
    }

    public void Update(String newcontent, String strDate){
        String sql_update = "Update notes set date = '" + strDate + "', content = '" + newcontent + "' where _id = " + id;
        sqLiteDatabase.execSQL(sql_update);
    }
}
