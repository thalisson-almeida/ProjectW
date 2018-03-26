package com.thalisson.android.projectw.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thalisson.android.projectw.database.Contract;
import com.thalisson.android.projectw.database.SqLiteHelper;
import com.thalisson.android.projectw.model.CadastroModel;

import java.util.ArrayList;

/**
 * Created by thali on 22/03/2018.
 */

public class CadastroManager {

    public static final int ERRO_DADOS_INVALIDO = -2;
    public static final int ERRO_AO_INSERIR = -1;
    public static final int ERRO_AO_EDITAR = -3;
    public static final int SUCESSO = 1;

    public boolean delete(Context context, int id){
        SqLiteHelper helper= new SqLiteHelper(context);
        final SQLiteDatabase db = helper.getReadableDatabase();
        int response = db.delete(Contract.Cadastro.TABLE_NAME,Contract.Cadastro._ID+"=?",new String[]{id+""});
        return response>0;
    }



    public ArrayList<CadastroModel> getAll(Context context){
        SqLiteHelper helper= new SqLiteHelper(context);
        final SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(Contract.Cadastro.TABLE_NAME,null,null,null,null,null,null);
        ArrayList<CadastroModel> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CadastroModel model = new CadastroModel();
                model.id = cursor.getInt(cursor.getColumnIndex(Contract.Cadastro._ID));
                model.razaoSocial = cursor.getString(cursor.getColumnIndex(Contract.Cadastro.COL_RAZAOSOCIAL));
                model.nomeFantasia = cursor.getString(cursor.getColumnIndex(Contract.Cadastro.COL_NOMEFANTASIA));
                model.cnpj = cursor.getString(cursor.getColumnIndex(Contract.Cadastro.COL_CNPJ));
                model.ddd = cursor.getString(cursor.getColumnIndex(Contract.Cadastro.COL_DDD));
                model.telefone = cursor.getString(cursor.getColumnIndex(Contract.Cadastro.COL_TELEFONE));
                model.site = cursor.getString(cursor.getColumnIndex(Contract.Cadastro.COL_SITE));
                list.add(model);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public int insertCadastro(Context context, ContentValues values) {
        int retorno = -2;
        if(validateCadastro(values)){
            SqLiteHelper helper = new SqLiteHelper(context);
            final SQLiteDatabase db = helper.getReadableDatabase();
            long qtd = db.insert(Contract.Cadastro.TABLE_NAME, null, values);
            retorno = (qtd>0)?SUCESSO:ERRO_AO_INSERIR;
        }

        return retorno;
    }

    public int updateCadastro(Context context, ContentValues values,int id) {
        int retorno = -2;
        if(validateCadastro(values)){
            SqLiteHelper helper = new SqLiteHelper(context);
            final SQLiteDatabase db = helper.getReadableDatabase();

            long qtd = db.update(Contract.Cadastro.TABLE_NAME,values,Contract.Cadastro._ID+"=?",new String[]{id+""});
            retorno = (qtd>0)?SUCESSO:ERRO_AO_EDITAR;
        }

        return retorno;
    }

    private boolean validateCadastro(ContentValues values){
        String razaoSocial = values.getAsString(Contract.Cadastro.COL_RAZAOSOCIAL);
        String nomeFantasia = values.getAsString(Contract.Cadastro.COL_NOMEFANTASIA);
        String cnpj = values.getAsString(Contract.Cadastro.COL_CNPJ);
        String ddd = values.getAsString(Contract.Cadastro.COL_DDD);
        String telefone = values.getAsString(Contract.Cadastro.COL_TELEFONE);

        boolean resultado = !razaoSocial.isEmpty() &&
                !nomeFantasia.isEmpty() &&
                validarCNPJ(cnpj) &&
                !ddd.isEmpty() && ddd.length()==2 &&
                !telefone.isEmpty();


        return resultado;
    }

    private boolean validarCNPJ(String cnpj){
        boolean retorno = false;
        if(cnpj.length()==14){
            int somaPrimeiroSet = 0;
            int somaSegundoSet = 0;

            char[] numerosCNPJ = cnpj.toCharArray();
            int[] primeiroSet = new int[]{5,4,3,2,9,8,7,6,5,4,3,2,0};
            int[] segundoSet = new int[]{6,5,4,3,2,9,8,7,6,5,4,3,2};

            for(int i=0;i<13;i++){
                int numero = Integer.parseInt(numerosCNPJ[i]+"");
                somaPrimeiroSet += numero*primeiroSet[i];
                somaSegundoSet += numero*segundoSet[i];
            }

            int resultadoDivisao1 = somaPrimeiroSet%11;
            String primeiroValor;
            if(resultadoDivisao1<2){
                primeiroValor = "0";
            }else{
                primeiroValor = (11-resultadoDivisao1)+"";
            }

            int resultadoDivisao2 = somaSegundoSet%11;
            String segundoValor;
            if(resultadoDivisao2<2){
                segundoValor = "0";
            }else{
                segundoValor = (11-resultadoDivisao2)+"";
            }

            retorno = (primeiroValor.equals(numerosCNPJ[12]+"") ) && (segundoValor.equals(numerosCNPJ[13]+""));

        }
        return retorno;

    }

}
