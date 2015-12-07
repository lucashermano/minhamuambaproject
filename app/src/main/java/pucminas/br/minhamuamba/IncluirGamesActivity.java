package pucminas.br.minhamuamba;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import pucminas.br.minhamuamba.pucminas.br.minhamuamba.database.DBAdapter;

public class IncluirGamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_games);
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


        Button btnConfirmar = (Button) findViewById(R.id.btnConfirmarGames);

        btnConfirmar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText txtNomeGame = (EditText) findViewById(R.id.txtNomeGame);
                EditText txtValorOriginal = (EditText) findViewById(R.id.txtValorOriginal);
                EditText txtValorAtual = (EditText) findViewById(R.id.txtValorAtual);

                int console = 0;
                RadioGroup radioGrupoConsoles = (RadioGroup) findViewById(R.id.radioGrupoConsoles);

                int selectedId = radioGrupoConsoles.getCheckedRadioButtonId();

                RadioButton radioSelecionado = (RadioButton) findViewById(selectedId);

                if (radioSelecionado.getText().equals("PS3")) {
                    console = 1;
                } else if (radioSelecionado.getText().equals("PS4")) {
                    console = 2;
                } else if (radioSelecionado.getText().equals("WiiU")) {
                    console = 3;
                } else if (radioSelecionado.getText().equals("3DS")) {
                    console = 4;
                }

                DBAdapter dbAdapter = new DBAdapter(v.getContext());
                dbAdapter.open();
                long idMuamba = dbAdapter.insertMuamba(txtNomeGame.getText().toString(), new Double(txtValorOriginal.getText().toString()), new Double(txtValorAtual.getText().toString()));
                dbAdapter.insertGames(console, idMuamba);

                dbAdapter.close();

                Toast.makeText(getApplicationContext(), "Game cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                txtNomeGame.setText("");
                txtValorOriginal.setText("");
                txtValorAtual.setText("");

            }
        });

    }

}
