package com.example.timer.RecyclerViewCategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.timer.Database;
import com.example.timer.R;
import com.example.timer.Sequence.Timer.Category.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private LayoutInflater inflater;
    private List<Category> categories;
    private Context context;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_category_item, parent, false);
        return new CategoryAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.nameCategory.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView nameCategory;
        final ImageButton btnDel;
        ViewHolder(View view){
            super(view);
            nameCategory = (TextView) view.findViewById(R.id.textViewNameCategory);
            btnDel = (ImageButton) view.findViewById(R.id.buttonDelCategory);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Database db = new Database(view.getContext());
                    long id = categories.get(getAdapterPosition()).getId();
                    categories.remove(getAdapterPosition());
                    db.delCategory(id);
                    notifyDataSetChanged();
                }
            });

        }
    }
}
