package com.thalisson.android.projectw.database;

import android.provider.BaseColumns;

/**
 * Created by thali on 22/03/2018.
 */

public class Contract {
    private Contract(){}

    public static class Cadastro implements BaseColumns {
        public static final String TABLE_NAME = "CadastroManager";
        public static final String COL_RAZAOSOCIAL = "razao_social";
        public static final String COL_NOMEFANTASIA = "nome_fantasia";
        public static final String COL_CNPJ = "cnpj";
        public static final String COL_DDD = "ddd";
        public static final String COL_TELEFONE = "telefone";
        public static final String COL_SITE = "site";
    }
}
