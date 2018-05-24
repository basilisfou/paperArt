package com.fouroulis.vasilis.artkotsis;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fouroulis.vasilis.artkotsis.model.PaperItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a activity representing
 * PaperItem details. On tablets, the activity presents the list of items and
 * PaperItem details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;

        try {
            setupRecyclerView((RecyclerView) recyclerView, createList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<PaperItem> createList() throws IOException {
        List<PaperItem> paperItems = new ArrayList<>();
        List<String> images =  getImages(this);

        for(int i = 0; i < images.size(); i++){
            PaperItem item = new PaperItem(i,"80",
                    "16",
                    "200",
                    "grayscale",
                    "resolution",
                    "7.932.774",
                    "Fabriano Copy 3",
                    "HP ScanJet G2710",
                     "04/01/2017 2.30.45",
                    "serial number 8001348103363",
                     images.get(i));
            paperItems.add(item);
        }

        return paperItems;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, final List<PaperItem> paperItems) {
        SimpleItemRecyclerViewAdapter adapter = new SimpleItemRecyclerViewAdapter(this,paperItems);
        adapter.setOnPaperClickListener(new SimpleItemRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, ItemDetailFragment.newInstance(paperItems.get(position)))
                        .commit();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<PaperItem> mValues;
        private OnClickListener onClickListener;

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<PaperItem> items) {
            mValues = items;
            mParentActivity = parent;

        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder,
                                     int position) {
            holder.mIdView.setText(mValues.get(position).getId() + "/" + mValues.size() );
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void setOnPaperClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;


            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.onClick(getAdapterPosition());
                    }
                });
            }
        }

        interface OnClickListener {
            void onClick(int position);
        }
    }

    private List<String> getImages(Context context) throws IOException {
        String[] images = context.getAssets().list("images");
        return new ArrayList<>(Arrays.asList(images));
    }
}
