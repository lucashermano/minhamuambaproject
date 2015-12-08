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

public class MainActionFiguresActivity extends AppCompatActivity {

    DBAdapter dbAdapter;
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action_figures);
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


        Button btnIncluirActionFigures = (Button) findViewById(R.id.btnIncluirActionFigures);
        btnIncluirActionFigures.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getResources().getString(R.string.JANELA_INCLUIR_ACTION_FIGURES));
                startActivity(it);
            }
        });

        atualizarLista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {

        ListView lstActionFigures = (ListView) findViewById(R.id.listActionFigures);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        Cursor cursor = dbAdapter.getAllActionFiguresComplete();

        String[] from = new String[]{DBAdapter.ACTION_FIGURES_KEY_MARCA, DBAdapter.MUAMBA_KEY_NOME, DBAdapter.MUAMBA_KEY_VALORPAGO, DBAdapter.MUAMBA_KEY_VALORATUAL  };

        int[] to = new int[]{R.id.txtColuna01, R.id.txtColuna02, R.id.txtColuna03, R.id.txtColuna04};

        dataAdapter = new SimpleCursorAdapter(this, R.layout.activity_row_action_figures, cursor, from, to, 0);

        lstActionFigures.setAdapter(dataAdapter);

        dbAdapter.close();

    }

}
