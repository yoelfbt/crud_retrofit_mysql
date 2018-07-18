package com.example.yoelfebryan.crudmysql.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yoelfebryan.crudmysql.MainActivity;
import com.example.yoelfebryan.crudmysql.R;
import com.example.yoelfebryan.crudmysql.model.DataModel;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {

    private List<DataModel> mList;
    private Context context;

    public AdapterData (Context context, List<DataModel> mList){
        this.context = context;
        this.mList = mList;
    }


    @Override
    public HolderData onCreateViewHolder( ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlist, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder( HolderData holder, int position) {
        DataModel dataModel = mList.get(position);
        holder.nama.setText(dataModel.getNama());
        holder.usia.setText(dataModel.getUsia());
        holder.domisili.setText(dataModel.getDomisili());
        holder.dm = dataModel;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nama,usia,domisili;
        LinearLayout ItemList;
        DataModel dm;
        public HolderData (View view){
            super(view);

            nama = (TextView) view.findViewById(R.id.tvnama);
            usia = (TextView) view.findViewById(R.id.tvusia);
            domisili = (TextView) view.findViewById(R.id.tvdomisili);
            ItemList = (LinearLayout) view.findViewById(R.id.list_item);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,MainActivity.class);
                    intent.putExtra("id",dm.getId());
                    intent.putExtra("nama",dm.getNama());
                    intent.putExtra("usia",dm.getUsia());
                    intent.putExtra("domisili",dm.getDomisili());

                    context.startActivity(intent);
                }
            });
        }
    }
}
