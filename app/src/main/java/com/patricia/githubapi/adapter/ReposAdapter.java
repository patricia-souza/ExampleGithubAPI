package com.patricia.githubapi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.patricia.githubapi.R;
import com.patricia.githubapi.model.GitHubRepo;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder>  {

    private List<GitHubRepo> repositories;
    private int rowLayout;
    private Context context;


    public ReposAdapter(List<GitHubRepo> repos, int rowLayout, Context context) {
        this.setRepos(repos);
        this.setRowLayout(rowLayout);
        this.setContext(context);
    }

    public List<GitHubRepo> getRepos() {return repositories;}

    public void setRepos(List<GitHubRepo> repos) {this.repositories = repos;}

    public int getRowLayout() {return rowLayout;}

    public void setRowLayout(int rowLayout) {this.rowLayout = rowLayout;}

    public Context getContext() {return context;}

    public void setContext(Context context) {this.context = context;}

    public static class ReposViewHolder extends RecyclerView.ViewHolder {

        LinearLayout reposLayout;
        TextView repoName;
        TextView repoDescription;
        TextView repolanguage;
        TextView repoLink;

        public ReposViewHolder(View view) {
            super(view);
            reposLayout = (LinearLayout) view.findViewById(R.id.repo_item_layout);
            repoName = (TextView) view.findViewById(R.id.repoName);
            repoDescription = (TextView) view.findViewById(R.id.repoDescription);
            repolanguage = (TextView) view.findViewById(R.id.repoLanguage);
            repoLink = (TextView) view.findViewById(R.id.repoLink);
        }
    }

    @Override
    public ReposAdapter.ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ReposViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ReposViewHolder holder, final int position) {
        holder.repoName.setText(repositories.get(position).getName());
        holder.repoDescription.setText(repositories.get(position).getDescription());
        holder.repolanguage.setText(repositories.get(position).getLanguage());
        holder.repoLink.setText(repositories.get(position).getLink());
    }

    @Override
    public int getItemCount() { return repositories.size();}
}

