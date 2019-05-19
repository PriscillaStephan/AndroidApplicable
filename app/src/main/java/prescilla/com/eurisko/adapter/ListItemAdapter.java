package prescilla.com.eurisko.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import prescilla.com.eurisko.R;
import prescilla.com.eurisko.model.DataList;
import prescilla.com.eurisko.ui.ListDetailsActivity;

import static prescilla.com.eurisko.utils.Config.LIST_COMPLETED;
import static prescilla.com.eurisko.utils.Config.LIST_ID;
import static prescilla.com.eurisko.utils.Config.LIST_IDUSER;
import static prescilla.com.eurisko.utils.Config.LIST_TITLE;


public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.MyViewHolder> {
    private List<DataList> list;
    private Context context;
    HashMap<String, Integer> mapColored = new HashMap<>();

    public ListItemAdapter(Context context, List<DataList> list) {
        this.list = list;
        this.context = context;
    }

    int Position = -1;

    @NonNull
    @Override
    public ListItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);
        return new MyViewHolder(v);
    }

    private final SparseBooleanArray array = new SparseBooleanArray();

    @Override
    public void onBindViewHolder(@NonNull ListItemAdapter.MyViewHolder holder, int i) {
        Position = i;
        final DataList listData = list.get(i);
        holder.title.setText(listData.getTitle());
        Random rnd = new Random();
        if (listData.getCompleted()) {
            mapColored.put("key-clicked", i);
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.mainlayout.setBackgroundColor(color);
        }

        if (mapColored != null && mapColored.get("key-clicked") != null)
            if (mapColored.get("key-clicked") == i) {
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                holder.mainlayout.setBackgroundColor(color);
            } else {
                holder.mainlayout.setBackgroundColor(Color.rgb(255, 255, 255));
            }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView title;
        private LinearLayout mainlayout;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            mainlayout = view.findViewById(R.id.row_layout);

            mainlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DataList listData = list.get(getAdapterPosition());
                    context.startActivity(new Intent(context, ListDetailsActivity.class)
                            .putExtra(LIST_ID, String.valueOf(listData.getId()))
                            .putExtra(LIST_IDUSER, String.valueOf(listData.getUserId()))
                            .putExtra(LIST_TITLE, listData.getTitle())
                            .putExtra(LIST_COMPLETED, String.valueOf(listData.getCompleted())));

                }
            });
        }
    }
}
