package com.example.hadith.hadithy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ResponseViewHolder> {
    private Context context;
    private List<String> responseList;

    public ResponseAdapter(Context context, List<String> responseList) {
        this.context = context;
        this.responseList = responseList;
    }
    public void clearData() {
        responseList.clear(); //clear list
        notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }

    @NonNull
    @Override
    public ResponseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.response_list, null);

        return new ResponseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseViewHolder responseViewHolder, int i) {
        responseViewHolder.htmlTextView.setHtml(responseList.get(i));
    }

    @Override
    public int getItemCount() {
        return responseList.size() -1;
    }

    class ResponseViewHolder extends RecyclerView.ViewHolder {
        HtmlTextView htmlTextView;
        public ResponseViewHolder(@NonNull View itemView) {
            super(itemView);
            htmlTextView = itemView.findViewById(R.id.card_html_text_view);
        }
    }
}
