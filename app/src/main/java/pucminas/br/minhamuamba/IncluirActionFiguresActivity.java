package pucminas.br.minhamuamba;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pucminas.br.minhamuamba.pucminas.br.minhamuamba.database.DBAdapter;

public class IncluirActionFiguresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_action_figures);
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

        Button btnConfirmar = (Button) findViewById(R.id.btnConfirmarActionFigure);

        btnConfirmar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText txtMarca = (EditText) findViewById(R.id.txtMarcaActionFigure);
                EditText txtNome = (EditText) findViewById(R.id.txtNomeActionFigure);
                EditText txtValorOriginal = (EditText) findViewById(R.id.txtValorOriginalActionFigure);
                EditText txtValorAtual = (EditText) findViewById(R.id.txtVAlorAtualActionFigure);
                EditText txtQuantidade = (EditText) findViewById(R.id.txtQuantidadeActionFigure);
                EditText txtDefeitos = (EditText) findViewById(R.id.txtDefeitosActionFigure);

                DBAdapter dbAdapter = new DBAdapter(v.getContext());
                dbAdapter.open();
                long idMuamba = dbAdapter.insertMuamba(txtNome.getText().toString(), new Double(txtValorOriginal.getText().toString()), new Double(txtValorAtual.getText().toString()));
                dbAdapter.insertActionFigures(txtMarca.getText().toString(), new Integer(txtQuantidade.getText().toString()), txtDefeitos.getText().toString(), idMuamba);

                dbAdapter.close();

                Toast.makeText(getApplicationContext(), "Action Figure cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                txtMarca.setText("");
                txtNome.setText("");
                txtValorOriginal.setText("");
                txtValorAtual.setText("");
                txtQuantidade.setText("");
                txtDefeitos.setText("");
            }
        });

    }

}
