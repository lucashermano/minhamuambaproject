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

public class IncluirMangasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_mangas);
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

        Button btnConfirmar = (Button) findViewById(R.id.btnConfirmarMangas);

        btnConfirmar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText txtNome = (EditText) findViewById(R.id.txtNomeManga);
                EditText txtVolume = (EditText) findViewById(R.id.txtVolumeManga);
                EditText txtValorOriginal = (EditText) findViewById(R.id.txtValorOriginalManga);
                EditText txtValorAtual = (EditText) findViewById(R.id.txtValorAtualManga);

                DBAdapter dbAdapter = new DBAdapter(v.getContext());
                dbAdapter.open();
                long idMuamba = dbAdapter.insertMuamba(txtNome.getText().toString(), new Double(txtValorOriginal.getText().toString()), new Double(txtValorAtual.getText().toString()));
                dbAdapter.insertMangas(new Integer(txtVolume.getText().toString()), idMuamba);

                dbAdapter.close();

                Toast.makeText(getApplicationContext(), "Mangá cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                txtNome.setText("");
                txtVolume.setText("");
                txtValorOriginal.setText("");
                txtValorAtual.setText("");

            }
        });
    }

}
