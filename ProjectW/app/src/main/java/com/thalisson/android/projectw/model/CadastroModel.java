package com.thalisson.android.projectw.model;

import android.os.Bundle;

import com.thalisson.android.projectw.database.Contract;

/**
 * Created by thali on 24/03/2018.
 */

public class CadastroModel {
    public int id;
    public String razaoSocial;
    public String nomeFantasia;
    public String cnpj;
    public String ddd;
    public String telefone;
    public String site;

    public Bundle getModelBundle(){
        Bundle bundle = new Bundle();
        bundle.putInt(Contract.Cadastro._ID,id);
        bundle.putString(Contract.Cadastro.COL_RAZAOSOCIAL, razaoSocial);
        bundle.putString(Contract.Cadastro.COL_NOMEFANTASIA, nomeFantasia);
        bundle.putString(Contract.Cadastro.COL_CNPJ, cnpj);
        bundle.putString(Contract.Cadastro.COL_DDD, ddd);
        bundle.putString(Contract.Cadastro.COL_TELEFONE, telefone);
        bundle.putString(Contract.Cadastro.COL_SITE, site);
        return bundle;
    }
}
