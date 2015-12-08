package pucminas.br.minhamuamba;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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

        Button btnIncluirGames = (Button) findViewById(R.id.btnIncluirGames);
        btnIncluirGames.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getResources().getString(R.string.JANELA_INCLUIR_GAMES));
                startActivity(it);
            }
        });

        atualizarListaGames();

    }

    private void atualizarListaGames() {

        ListView lstGames = (ListView) findViewById(R.id.listGames);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        Cursor cursor = dbAdapter.getAllGamesComplete();

        String[] from = new String[]{"descricaoconsole", DBAdapter.MUAMBA_KEY_NOME, DBAdapter.MUAMBA_KEY_VALORPAGO, DBAdapter.MUAMBA_KEY_VALORATUAL  };

        int[] to = new int[]{R.id.txtColuna01, R.id.txtColuna02, R.id.txtColuna03, R.id.txtColuna04};

        dataAdapter = new SimpleCursorAdapter(this, R.layout.activity_row_games, cursor, from, to, 0);

        lstGames.setAdapter(dataAdapter);

        dbAdapter.close();

    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarListaGames();
    }
}
