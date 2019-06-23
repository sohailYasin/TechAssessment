package com.sohail.TechAssessment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sohail Yasin on 6/21/2018.
 */

public class ArticleListAdapter extends BaseAdapter {

    List<Article> articleList;
    Context context;

    public ArticleListAdapter(Context context, List<Article> articleList){
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public int getCount() {
        return articleList ==null?0: articleList.size();
    }

    @Override
    public Article getItem(int i) {
        return articleList ==null?null: articleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
            view = LayoutInflater.from(context).inflate(R.layout.list_child,null);
        Article article = getItem(i);
        TextView nameTextView = (TextView)view.findViewById(R.id.title);
        TextView Writer = (TextView)view.findViewById(R.id.writer);
        TextView Date = (TextView)view.findViewById(R.id.date);
        CircleImageView article_image = (CircleImageView)view.findViewById(R.id.article_image);
        nameTextView.setText(article.getName());
        Writer.setText(article.getWriter());
        Date.setText(article.getDate());


       String Url = article.getImageURL();
        if (!Url.equals("")) {
            Picasso.get()
                    .load(Url)
                    .placeholder(R.drawable.no_image)
                    .into(article_image);

        }
        return view;
    }
}
