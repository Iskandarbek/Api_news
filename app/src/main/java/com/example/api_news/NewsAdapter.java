package com.example.api_news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {
    List<Article>newsAdapterList;
    Context context;
    PrettyTime t = new PrettyTime();
    RecyclerOnClick recyclerOnClick;


    public void setRecyclerOnClick(RecyclerOnClick recyclerOnClick){
        this.recyclerOnClick = recyclerOnClick;
    }


    public NewsAdapter(Context context, List<Article> newsAdapterList) {
        this.context = context;
        this.newsAdapterList = newsAdapterList;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);

        return new Holder(view, recyclerOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {


            holder.author.setText(newsAdapterList.get(position).getAuthor());
            holder.title.setText(newsAdapterList.get(position).getTitle());
            holder.desc.setText(newsAdapterList.get(position).getDescription());
            holder.date.setText(newsAdapterList.get(position).getPublishedAt());
            holder.source.setText(newsAdapterList.get(position).getSource().getSourceName().toUpperCase());
            holder.time.setText(newsAdapterList.get(position).getPublishedAt());

            Picasso.get()
                    .load(newsAdapterList.get(position).getUrlToImage())
                    .into(holder.imageUrl);

    }

    @Override
    public int getItemCount() {
        return newsAdapterList.size();
    }


    public static class Holder extends RecyclerView.ViewHolder{
        TextView author, date, title,desc,source, time;
        ImageView imageUrl;
        public Holder(@NonNull View itemView, RecyclerOnClick recyclerOnClick) {
            super(itemView);

            author = (TextView) itemView.findViewById(R.id.authorText);
            date = (TextView) itemView.findViewById(R.id.dateText);
            title = (TextView) itemView.findViewById(R.id.titleText);
            desc = (TextView) itemView.findViewById(R.id.desc);
            source = (TextView) itemView.findViewById(R.id.sourceText);
            time = (TextView) itemView.findViewById(R.id.timeStamp);
            imageUrl = (ImageView) itemView.findViewById(R.id.imageUrl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerOnClick.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface RecyclerOnClick{
        void onClick(int position);
    }
}
