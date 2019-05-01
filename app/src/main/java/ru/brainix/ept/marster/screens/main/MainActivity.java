package ru.brainix.ept.marster.screens.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import ru.brainix.ept.marster.R;
import ru.brainix.ept.marster.network.DataModel;
import ru.brainix.ept.marster.screens.main.list.CardAdapter;


public class MainActivity extends AppCompatActivity implements IMainView {

    //private final String LOG_TAG = "MainActivity ";

    private MainPresenter mainPresenter;

		private TextView textNoItem;
		private ProgressBar pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

				mainPresenter = new MainPresenter();

				pBar = findViewById(R.id.progressBar);

				textNoItem = findViewById(R.id.textNoItem);

				mainPresenter.onAttachActivity(this);
				mainPresenter.onActivityCreate();


    }

		@Override
		protected void onDestroy() {
			super.onDestroy();
			mainPresenter.onDeattachActivity();
		}


		@Override
		public void setMainList(List<DataModel> list) {

			  RecyclerView recyclerView = findViewById(R.id.mainList);

			  CardAdapter adapter = new CardAdapter(this, list);
				recyclerView.setAdapter(adapter);
				recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

			}


		@Override
		public void showLoadingError() {

				textNoItem.setVisibility(View.VISIBLE);

			}


		@Override
		public void showStatusBar() {

			pBar.setVisibility(View.VISIBLE);

		}


		@Override
		public void hideStatusBar() {

			pBar.setVisibility(View.GONE);

		}


}
