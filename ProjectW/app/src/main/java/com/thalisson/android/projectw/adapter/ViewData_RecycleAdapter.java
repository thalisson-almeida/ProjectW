package com.thalisson.android.projectw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thalisson.android.projectw.R;
import com.thalisson.android.projectw.model.CadastroModel;

import java.util.ArrayList;

/**
 * Created by thali on 24/03/2018.
 */

public class ViewData_RecycleAdapter extends RecyclerView.Adapter<ViewData_RecycleAdapter.ViewHolder>{

    private ArrayList<CadastroModel> dataset;
    private LayoutInflater mInflater;
    private ItemClickListener clickListener;
    private Context context;

    public interface ItemClickListener {
        void onDeleteButton(View view, int position);
        void onEditButton(View view, int position);
    }

    public ViewData_RecycleAdapter(Context context, ArrayList<CadastroModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.dataset = data;
        this.context = context;
    }

    public void setDataset(ArrayList<CadastroModel>  data){
        this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.view_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setDataInView(dataset.get(position));

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setClickListener(ItemClickListener itemClickListener)  {
        this.clickListener = itemClickListener;
    }


    public CadastroModel getItem(int position){
        return dataset.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView razaoSocial;
        public TextView idCadastro;
        public TextView cnpj;
        public TextView nomeFantasia;
        public TextView ddd;
        public TextView telefone;
        public TextView site;
        public LinearLayout layoutSite;

        public Button editButton;
        public Button deleteButton;

        public ViewHolder(View v) {
            super(v);

            razaoSocial = itemView.findViewById(R.id.rview_razaoSocial);
            idCadastro = itemView.findViewById(R.id.rview_id);
            cnpj = itemView.findViewById(R.id.rview_cnpj);
            nomeFantasia = itemView.findViewById(R.id.rview_nomeFantasia);
            ddd = itemView.findViewById(R.id.rview_ddd);
            telefone = itemView.findViewById(R.id.rview_telefone);
            site = itemView.findViewById(R.id.rview_site);
            layoutSite = itemView.findViewById(R.id.rview_siteLayout);

            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);

        }

        public void setDataInView(CadastroModel model){
            idCadastro.setText(model.id+"");
            razaoSocial.setText(model.razaoSocial);
            cnpj.setText(model.cnpj);
            nomeFantasia.setText(model.nomeFantasia);
            ddd.setText(model.ddd);
            telefone.setText(model.telefone);
            site.setText(model.site);
            if(model.site.isEmpty()){
                layoutSite.setVisibility(View.GONE);
            }else{
                layoutSite.setVisibility(View.VISIBLE);
            }

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onDeleteButton(view,getAdapterPosition());
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onEditButton(view,getAdapterPosition());
                }
            });

        }
    }

}
