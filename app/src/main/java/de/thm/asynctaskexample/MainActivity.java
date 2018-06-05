package de.thm.asynctaskexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Yannick Bals on 05.06.2018.
 */

public class MainActivity extends AppCompatActivity {

    private EditText amountEdit;
    private Button goButton;
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;

    private ArrayList<Integer> numbersList;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        numbersList = new ArrayList<>();

        amountEdit = findViewById(R.id.sizeEdit);
        goButton = findViewById(R.id.goButton);
        goButton.setOnClickListener(new GenerateListener());

        refreshLayout = findViewById(R.id.refreshLayout);

        listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, numbersList);
        listView.setAdapter(adapter);

    }

    private void generate() {

        String amountString = amountEdit.getText().toString();

        if (amountString != null && !amountString.equals("")) {
            try {
                int amount = Integer.parseInt(amountString);
                refreshLayout.setRefreshing(true);
                new RandomNumberGenerator().execute(amount);
            } catch (Exception e) {
                Toast.makeText(this, "Please enter an integer", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter an integer.", Toast.LENGTH_SHORT).show();
        }

    }

    class RandomNumberGenerator extends AsyncTask<Integer, Void, ArrayList<Integer>> {

        @Override
        protected ArrayList<Integer> doInBackground(Integer... integers) {

            ArrayList<Integer> randomNos = new ArrayList<>();
            Random r = new Random();

            for (int i = 0; i < integers[0]; i++) {
                randomNos.add(r.nextInt(1001));
            }

            return randomNos;
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> integers) {
            super.onPostExecute(integers);

            adapter.clear();
            adapter.addAll(integers);
            refreshLayout.setRefreshing(false);

        }
    }

    class GenerateListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            generate();
        }
    }

}
