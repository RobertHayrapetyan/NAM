package com.example.rob.try3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ExampleViewHolder> {
    private Context mContext;
    private ArrayList<RecyclerViewItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public RecyclerViewAdapter(Context context, ArrayList<RecyclerViewItem> exampleList){
        mContext = context;
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        RecyclerViewItem currentItem = mExampleList.get(position);

        String eventPoster = currentItem.getEvevtPoster();
        String eventName = currentItem.getEventName();
        String eventDate = currentItem.getEventDate();
        String eventDescription = currentItem.getEventDescription();

        holder.mTextViewEventName.setText(eventName);
        holder.mTextViewEventDate.setText(eventDate);
        holder.mTextViewEventDescription.setText(eventDescription);
        Picasso.with(mContext).load(eventPoster).fit().centerInside().into(holder.mEventPoster);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mEventPoster;
        public TextView mTextViewEventName;
        public TextView mTextViewEventDate;
        public TextView mTextViewEventDescription;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            mEventPoster = itemView.findViewById(R.id.event_poster);
            mTextViewEventName = itemView.findViewById(R.id.text_view_event_name);
            mTextViewEventDate = itemView.findViewById(R.id.text_view_event_date);
            mTextViewEventDescription = itemView.findViewById(R.id.text_view_event_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
