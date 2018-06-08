package com.fouroulis.vasilis.artkotsis;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(getTitle());

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;

        try {
            List<PaperItem> paperItems = createList();
            setupRecyclerView((RecyclerView) recyclerView, paperItems);
            openGridFragment(paperItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openGridFragment(List<PaperItem> paperItems){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.item_detail_container, ItemGridFragment.newInstance(paperItems))
                .commit();
    }

    private List<PaperItem> createList() throws IOException {
        List<PaperItem> paperItems = new ArrayList<>();
        List<String> images =  getImages(this);

        for(int i = 0; i < images.size(); i++){
            PaperItem item = new PaperItem(i + "/" + images.size() , i,"80",
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
        private int selectedPos = RecyclerView.NO_POSITION;
        private OnClickListener onClickListener;

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<PaperItem> items) {
            mValues = items;
            mParentActivity = parent;

        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {

            if(viewType == R.layout.haeder_list){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.haeder_list, parent, false);
                return new HeaderViewHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list_content, parent, false);
                return new ItemViewHolder(view);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder,
                                     int position) {

            if(holder.getItemViewType() == R.layout.haeder_list){

            } else {
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.mtitle.setText(mValues.get(position).getId() + " / " + mValues.size() );
                itemViewHolder.mSecondTitle.setText(mValues.get(position).getId() + " / " + mValues.size() );
            }

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        @Override
        public int getItemViewType(int position) {

            if(position == 0 || position == mValues.size() - 1){
                return R.layout.haeder_list;
            }

            return super.getItemViewType(position);
        }

        public void setOnPaperClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        class ItemViewHolder extends ViewHolder {
            final TextView mtitle;
            final TextView mSecondTitle;


            ItemViewHolder(View view) {
                super(view);
                mtitle = view.findViewById(R.id.id_text);
                mSecondTitle = view.findViewById(R.id.id_text_second);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.onClick(getAdapterPosition());
                        selectedPos = getLayoutPosition();
                        notifyDataSetChanged();
                    }
                });
            }
        }

        class HeaderViewHolder extends ViewHolder{

            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        interface OnClickListener {
            void onClick(int position);
        }

        private int getColorCustom(Context context,int resColor){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return context.getColor(resColor);
            } else {
                return context.getResources().getColor(resColor);
            }
        }
    }

    private List<String> getImages(Context context) throws IOException {
        String[] images = context.getAssets().list("images");
        return new ArrayList<>(Arrays.asList(images));
    }
}
