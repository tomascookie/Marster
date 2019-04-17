package ru.brainix.ept.marster;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.brainix.ept.marster.list.setting.CardAdapter;
import ru.brainix.ept.marster.list.setting.DialogFragment;


public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "MainActivity ";

    private MainPresenter mainPresenter;

    TextView textNoItem;
    RecyclerView recyclerView;
    ProgressBar pBar;

    GetList getList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);



        //Устанавливаем прогрессбар и текст исключения по умолчанию

        textNoItem = findViewById(R.id.textNoItem);
        textNoItem.setVisibility(View.GONE);

        pBar = findViewById(R.id.progressBar);
        pBar.setVisibility(View.GONE);



        startRecycler();



    }


    //Устанавливаем список
    public void startRecycler(){

        getList = new GetList();
        getList.execute(getBaseContext());

    }


    //Вызываем диалоговое окно для удаления элемента(убрать в презентер)
    public void getDialogFragment(int imageId){


       DialogFragment dialog = new DialogFragment();

        Bundle args = new Bundle();
        args.putInt("image_id", imageId);
        dialog.setArguments(args);

        dialog.show(getSupportFragmentManager(), "custom");



    }


    //Получаем адаптер и ставим его в список
    class GetList extends AsyncTask<Context, Void, CardAdapter>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pBar.setVisibility(View.VISIBLE);

            recyclerView = findViewById(R.id.mainList);
            recyclerView.setAdapter( null);
            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));


            //btn.setEnabled(false);
        }

        @Override
        protected void onPostExecute(CardAdapter adapter) {
            super.onPostExecute(adapter);

            if (adapter==null){

                textNoItem.setVisibility(View.VISIBLE);
            }

            else {

                textNoItem.setVisibility(View.GONE);


                recyclerView.setAdapter( adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

            }

            pBar.setVisibility(View.GONE);
            //btn.setEnabled(true);


        }

        @Override
        protected CardAdapter doInBackground(Context... contexts) {


            mainPresenter = new MainPresenter();

            CardAdapter adapter = mainPresenter.launch(contexts[0]);




            return adapter;
        }
    }



}
