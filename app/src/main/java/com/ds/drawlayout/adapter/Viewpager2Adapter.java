package com.ds.drawlayout.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ds.drawlayout.R;
import com.ds.drawlayout.data.RecyclerviewData;
import com.ds.drawlayout.ui.map.MapFragment;

import java.util.ArrayList;

public class Viewpager2Adapter extends RecyclerView.Adapter<Viewpager2Adapter.ViewHolder> {
    private ArrayList<RecyclerviewData> mRecyclerList;
    private OnItemClickListener mListener = null ;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @NonNull
    @Override
    public Viewpager2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager2, parent, false);
        return new Viewpager2Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewpager2Adapter.ViewHolder holder, int position) {
        holder.onBind(mRecyclerList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = holder.name.getText().toString();
                Intent intent;
                intent = new Intent(mContext, MapFragment.class);
                intent.putExtra("mname", name);
                mContext.startActivity(intent);
            }
        });
    }

    public void setPagerList(ArrayList<RecyclerviewData> list) {
        this.mRecyclerList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = (ImageView) itemView.findViewById(R.id.profile);
            name = (TextView) itemView.findViewById(R.id.name);
            message = (TextView) itemView.findViewById(R.id.message);
        }

        void onBind(RecyclerviewData item) {
            profile.setImageResource(item.getResourceId());
            name.setText(item.getName());
            message.setText(item.getMessage());
        }
    }
}