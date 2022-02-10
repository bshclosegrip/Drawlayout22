package com.ds.drawlayout.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.ds.drawlayout.G;
import com.ds.drawlayout.R;
import com.ds.drawlayout.data.ChatItemData;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {

    ArrayList<ChatItemData> messageItems;
    LayoutInflater layoutInflater;

    public ChatAdapter(ArrayList<ChatItemData> messageItems, LayoutInflater layoutInflater) {
        this.messageItems = messageItems;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ChatItemData item = messageItems.get(position);
        View itemView=null;

        // no glide problem
        // but  i love you and added
        if(item.getName().equals(G.nickName)){
            itemView = layoutInflater.inflate(R.layout.box_message_right,viewGroup,false);
        }else{
            itemView = layoutInflater.inflate(R.layout.box_message_right,viewGroup,false);
        }

        //만들어진 itemView에 값들 설정
        CircleImageView iv= itemView.findViewById(R.id.iv);
        TextView tvName= itemView.findViewById(R.id.tv_name);
        TextView tvMsg= itemView.findViewById(R.id.tv_msg);
        TextView tvTime= itemView.findViewById(R.id.tv_time);

        tvName.setText(item.getName());
        tvMsg.setText(item.getMessage());
        tvTime.setText(item.getTime());

        Glide.with(itemView).load(item.getPofileUrl()).into(iv);

        return itemView;
    }
}
