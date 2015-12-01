package pucminas.br.minhamuamba;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import pucminas.br.minhamuamba.pucminas.br.minhamuamba.database.DBAdapter;

public class MainGamesActivity extends AppCompatActivity {

    DBAdapter dbAdapter;
    private SimpleCursorAdapter dataAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_games);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        ListView lstGames = (ListView) findViewById(R.id.listGames);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        //long idMuamba = dbAdapter.insertMuamba("Tales of Zestiria", 200.0d, 180.0d);
        //dbAdapter.insertGames(1, idMuamba);

        Cursor cursor = dbAdapter.getAllGames();

        String[] from = new String[]{DBAdapter.GAMES_KEY_ROWID,
                DBAdapter.GAMES_KEY_CONSOLE, DBAdapter.GAMES_KEY_ID_MUAMBA   };

        int[] to = new int[]{R.id.txtColuna01, R.id.txtColuna02, R.id.txtColuna03};

        dataAdapter = new SimpleCursorAdapter(this, R.layout.activity_row_games, cursor, from, to, 0);

        lstGames.setAdapter(dataAdapter);

        dbAdapter.close();

    }

}
