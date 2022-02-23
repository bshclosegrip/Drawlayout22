package com.ds.drawlayout.adapter;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ds.drawlayout.MainActivity;
import com.ds.drawlayout.R;
import com.ds.drawlayout.data.RecyclerviewData;
import com.ds.drawlayout.ui.home.HomeFragment;
import com.ds.drawlayout.ui.map.MapFragment;

import java.util.ArrayList;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
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
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_recyclerview, parent, false);
        return new RecyclerviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.ViewHolder holder, int position) {
        holder.onBind(mRecyclerList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String name = holder.name.getText().toString();
//                Intent intent;//인텐트 선언
//                intent = new Intent(mContext, MapFragment.class);
//                intent.putExtra("mname", name);
//                mContext.startActivity(intent);
            }
        });
    }

    public void setRecyclerList(ArrayList<RecyclerviewData> list) {
        this.mRecyclerList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView profile;
        TextView name;
        TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = (ImageView) itemView.findViewById(R.id.profile);
            name = (TextView) itemView.findViewById(R.id.name);
            message = (TextView) itemView.findViewById(R.id.message);
            itemView.setOnClickListener(this);
        }

        void onBind(RecyclerviewData item) {
            profile.setImageResource(item.getResourceId());
            name.setText(item.getName());
            message.setText(item.getMessage());
        }

        @Override
        public void onClick(View view) {
            Log.d("RecyA", "case R.id.recyclerview_settings");
//            Intent intent = new Intent(mContext, MainActivity.class);
//            mContext.startActivity(intent);

//            switch (view.getId()) {
//                case R.id.recyclerview_settings:
//                    Log.d("RecyA", "case R.id.recyclerview_settings");
//                    int pos = getAdapterPosition();
//                    if (pos != RecyclerView.NO_POSITION) {
//                        if(mListener !=null){
//                            mListener.onItemClick(view, pos);
//                        }
//                    }
//                    break;
//                default:
//                    break;
//            }
        }
    }
}