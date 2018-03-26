package com.thalisson.android.projectw.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.thalisson.android.projectw.R;
import com.thalisson.android.projectw.adapter.ViewData_RecycleAdapter;
import com.thalisson.android.projectw.controller.CadastroManager;
import com.thalisson.android.projectw.model.CadastroModel;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity implements ViewData_RecycleAdapter.ItemClickListener {
    private static final int CREATE_CODE = 10;
    private static final int EDIT_CODE = 20;

    private RecyclerView recyclerView;
    private ViewData_RecycleAdapter adapter;
    private TextView noDataMesage;

    private boolean reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_data);
        Toolbar toolbar = findViewById(R.id.view_toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_view_data);

        noDataMesage = findViewById(R.id.noData);

        recyclerView = findViewById(R.id.recycler_view);
        loadDataFromDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_data, menu);
        return true;
    }

    public void callActivity_Add(MenuItem item) {
        Intent add_activity = new Intent(this, CreateData.class);
        startActivityForResult(add_activity,CREATE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_CODE || requestCode == EDIT_CODE) {
            this.reload = (resultCode == Activity.RESULT_OK);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(reload){
            loadDataFromDatabase();
        }

    }

    public void loadDataFromDatabase(){
        CadastroManager manager = new CadastroManager();
        ArrayList<CadastroModel> list = manager.getAll(this);

        if(list.size()==0){
            noDataMesage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            noDataMesage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new ViewData_RecycleAdapter(this,list);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        }

    }


    @Override
    public void onDeleteButton(View view, final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.tit_delete));
        alertDialog.setMessage(getString(R.string.msg_delete));
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.bt_cancelar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.bt_delete),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deletarCadastro(position);
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void deletarCadastro(int position){
        CadastroModel model = adapter.getItem(position);
        CadastroManager manager = new CadastroManager();
        manager.delete(this,model.id);
        loadDataFromDatabase();
    }

    @Override
    public void onEditButton(View view, int position) {
        Intent edit_activity = new Intent(this, EditData.class);
        CadastroModel model = adapter.getItem(position);
        edit_activity.putExtra("model",model.getModelBundle());
        startActivityForResult(edit_activity,EDIT_CODE);
    }
}
