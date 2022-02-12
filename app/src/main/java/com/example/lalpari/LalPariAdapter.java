package com.example.lalpari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LalPariAdapter extends RecyclerView.Adapter<LalPariAdapter.NewCostomHolder> {
    Context context;
    List<Integer> imagelist;

    public LalPariAdapter(Context context, List<Integer> imagelist) {
        this.context = context;
        this.imagelist = imagelist;
    }

    @NonNull
    @Override
    public LalPariAdapter.NewCostomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row,parent,false);
        NewCostomHolder holder = new NewCostomHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LalPariAdapter.NewCostomHolder holder, int position) {
        String text = String.valueOf(position+1);
        holder.page.setText(text);
        holder.image.setImageResource(imagelist.get(position));

    }

    @Override
    public int getItemCount() { return imagelist.size();
    }

    public class NewCostomHolder extends RecyclerView.ViewHolder {
        TextView page;
        ImageView image;
        public NewCostomHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image1);
            page = itemView.findViewById(R.id.pageno);
        }
    }
}
