package com.example.harun.myflower.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harun.myflower.DetailsHaber;
import com.example.harun.myflower.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mayne on 4.12.2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private Context context;
    private List<post_model> postList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView category;
        public ImageView imageView;
        public TextView link;
        public TextView yazı;
        View v;

        public MyViewHolder(View itemView) {
            super(itemView);
            v = itemView;
            title = (TextView) itemView.findViewById(R.id.title);
            category = (TextView) itemView.findViewById(R.id.category);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            //link = (TextView) itemView.findViewById(R.id.link);
            yazı = (TextView) itemView.findViewById(R.id.yazı);


        }
    }

    public PostAdapter(Context context, List<post_model> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final post_model post = postList.get(position);
        holder.title.setText(post.getTitle());
       // holder.category.setText(post.getCategory().get(0));
        holder.category.setText(post.getPubDate());
       // holder.link.setText(post.getLink());
         holder.yazı.setText(Html.fromHtml(post.getDescription()));
       // holder.yazı.setText(post.getDescription());
        Picasso.with(context).load(post.getImageUrl()).into(holder.imageView);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsHaber.class);
                intent.putExtra("detaylar", post.getContent());
                intent.putExtra("haber",post.getTitle());
                intent.putExtra("resim",post.getImageUrl());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

}
