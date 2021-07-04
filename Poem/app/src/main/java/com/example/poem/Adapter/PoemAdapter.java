package com.example.poem.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.BmobQuery;
import com.example.poem.Class.CloudPoem;
import com.example.poem.activity.ActivityController;
import com.example.poem.Class.Poem;
import com.example.poem.activity.DisplayActivity;
import com.example.poem.R;

import java.util.List;

public class PoemAdapter extends RecyclerView.Adapter<PoemAdapter.ViewHolder> {

    private List<Poem> poemList;
    private Context context;
    private Activity activity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View poemView;
        GridLayout gridLayoutL;
        GridLayout gridLayoutR;
        TextView poemNameL;
        TextView poemAuthorL;
        TextView poemContentL;
        TextView poemNameR;
        TextView poemAuthorR;
        TextView poemContentR;

        /**
         * Constructor of ViewHolder class.
         *
         * @param view The outermost layout of item of RecyclerView.
         */
        private ViewHolder(View view) {
            super(view);
            poemView = view;
            gridLayoutL = view.findViewById(R.id.layout_l);
            gridLayoutR = view.findViewById(R.id.layout_r);
            poemNameL = view.findViewById(R.id.poem_name_l);
            poemAuthorL = view.findViewById(R.id.poem_author_l);
            poemContentL = view.findViewById(R.id.poem_content_l);
            poemNameR = view.findViewById(R.id.poem_name_r);
            poemAuthorR = view.findViewById(R.id.poem_author_r);
            poemContentR = view.findViewById(R.id.poem_content_r);
        }
    }

    public PoemAdapter(Context context, List<Poem> poemList) {
        this.poemList = poemList;
        this.context = context;
        this.activity = ActivityController.getActivity(this.context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* Load the item of RecyclerLayout. */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poem_item,
                parent, false);
        /* Create instance of ViewHolder by passing the view parameter. */
        final ViewHolder holder = new ViewHolder(view);

        holder.poemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(context, DisplayActivity.class);
                Poem poem = poemList.get(position);
                String toastText = "《" + poem.getName() + "》 " + poem.getAuthor();
                Toast.makeText(v.getContext(), toastText, Toast.LENGTH_SHORT).show();

                intent.putExtra("idKey", poem.getID());

                activity.startActivity(intent);
            }
        });
        /* Return the instance of ViewHolder. */
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Poem poem = poemList.get(position);

        if (poem.getPosition() % 2 == 0) {
            // holder.poemView.setBackgroundColor(0xDC143C);
            holder.gridLayoutR.setVisibility(View.VISIBLE);
            holder.gridLayoutL.setVisibility(View.GONE);
            holder.poemNameR.setText("《" + poem.getName() + "》");
            holder.poemAuthorR.setText(poem.getAuthor());
            holder.poemContentR.setText(poem.getContent());
        } else {
            holder.gridLayoutL.setVisibility(View.VISIBLE);
            holder.gridLayoutR.setVisibility(View.GONE);
            holder.poemNameL.setText("《" + poem.getName() + "》");
            holder.poemAuthorL.setText(poem.getAuthor());
            holder.poemContentL.setText(poem.getContent());
        }
    }

    /**
     * Get number of item of RecyclerView.
     *
     * @return A number.
     */
    @Override
    public int getItemCount() {
        return poemList.size();
    }
}
