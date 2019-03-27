package com.example.sfcontrolclient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.srs.common.ParamName;
import com.srs.common.ToAndroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static  final String TAG ="Отладка";




    protected void NetworkRun() {
        Log.d(TAG, "NetworkRun0");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!Network.start("192.168.1.65",8190))
                    return;
                while (true){
                    ToAndroid[] ret = Network.ping();
                    if (ret==null){
                        Log.d(TAG, "Network Ошибка обмена с сервером");
                        return;}
                    for (ToAndroid ta_rez:ret) {
                        Log.d(TAG, "Network.start: пришло:"+ta_rez.getKdName()+" Массив :"+ta_rez.getParams());
                    }
                    listParamView(ret);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Network.stop();
                        e.printStackTrace();
                    }

                }


            }
        }).start();
    }


    protected void listParamView(final ToAndroid[] params){
        Log.d(TAG, "listParamView формируем список");
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
// получаем экземпляр элемента ListView
                ListView listView = (ListView)findViewById(R.id.paramList);
                List<String> parArrey = new ArrayList<>();
                for (int i=0;i<params.length;i++){
                    Map<Integer,Integer> ta = params[i].getParams();
                    for (Integer paramName:ta.keySet()){
                        String fullStr =  "ЦПУ("+params[i].getKdName()+") - "+ ParamName.getNameByIt(paramName)+": ";
                        if(ta.get(paramName)==0)
                            fullStr = fullStr + "Нет";
                        else
                            if(ta.get(paramName)==1)
                                fullStr = fullStr + "Есть";
                            else
                                fullStr = fullStr + ta.get(paramName);


                        parArrey.add(fullStr);
                    }
                }


// определяем массив типа String
/*                final List<String> catNames = new ArrayList<String>(Arrays.asList(
                        "Рыжик","Барсик","Мурзик","Мурка","Васька",
                        "Томасина","Кристина","Пушок","Дымка","Кузя",
                        "Китти","Масяня","Симба"));
*/



// используем адаптер данных
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, parArrey);

                listView.setAdapter(adapter);

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Log.d(TAG, "onCreate: ");

        NetworkRun();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}