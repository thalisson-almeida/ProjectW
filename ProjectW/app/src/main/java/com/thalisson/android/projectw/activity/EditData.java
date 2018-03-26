package com.thalisson.android.projectw.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.thalisson.android.projectw.R;
import com.thalisson.android.projectw.controller.CadastroManager;
import com.thalisson.android.projectw.database.Contract;

public class EditData extends AppCompatActivity {

    EditText et_edit_razaoSocial;
    EditText et_edit_nomeFantasia;
    EditText et_edit_cnpj;
    EditText et_edit_ddd;
    EditText et_edit_telefone;
    EditText et_edit_site;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.create_toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_add_data);

        Bundle bundle  = getIntent().getBundleExtra("model");

        id = bundle.getInt(Contract.Cadastro._ID);

        et_edit_razaoSocial =  (EditText)findViewById(R.id.create_razaoSocial);
        et_edit_razaoSocial.setText(bundle.getString(Contract.Cadastro.COL_RAZAOSOCIAL));

        et_edit_nomeFantasia =  (EditText)findViewById(R.id.create_nomeFantasia);
        et_edit_nomeFantasia.setText(bundle.getString(Contract.Cadastro.COL_NOMEFANTASIA));

        et_edit_cnpj =  (EditText)findViewById(R.id.create_cnpj);
        et_edit_cnpj.setText(bundle.getString(Contract.Cadastro.COL_CNPJ));

        et_edit_ddd =  (EditText)findViewById(R.id.create_ddd);
        et_edit_ddd.setText(bundle.getString(Contract.Cadastro.COL_DDD));

        et_edit_telefone =  (EditText)findViewById(R.id.create_telefone);
        et_edit_telefone.setText(bundle.getString(Contract.Cadastro.COL_TELEFONE));

        et_edit_site =  (EditText)findViewById(R.id.create_site);
        et_edit_site.setText(bundle.getString(Contract.Cadastro.COL_SITE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_data, menu);
        return true;
    }

    public void returnToDataView(MenuItem item) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void editar(View v){

        String mensagem = "";

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Cadastro.COL_RAZAOSOCIAL, et_edit_razaoSocial.getText().toString());
        contentValues.put(Contract.Cadastro.COL_NOMEFANTASIA, et_edit_nomeFantasia.getText().toString());
        contentValues.put(Contract.Cadastro.COL_CNPJ, et_edit_cnpj.getText().toString());
        contentValues.put(Contract.Cadastro.COL_DDD, et_edit_ddd.getText().toString());
        contentValues.put(Contract.Cadastro.COL_TELEFONE, et_edit_telefone.getText().toString());
        contentValues.put(Contract.Cadastro.COL_SITE, et_edit_site.getText().toString());

        CadastroManager manager = new CadastroManager();
        int retorno = manager.updateCadastro(this,contentValues,id);
        if(retorno>0) {
            setResult(Activity.RESULT_OK);
            mensagem = getString(R.string.sucesso_addCadastro);
            Toast.makeText(this,mensagem,Toast.LENGTH_SHORT).show();
            finish();
        }else{
            switch (retorno){
                case CadastroManager.ERRO_AO_INSERIR:
                    mensagem = this.getString(R.string.erro_addCadastro_FalhaDB);
                    break;
                case CadastroManager.ERRO_DADOS_INVALIDO:
                    mensagem  = getString(R.string.erro_addCadastro_Invalido);
                    break;
            }

            Toast.makeText(this,mensagem,Toast.LENGTH_SHORT).show();

        }
    }
}
