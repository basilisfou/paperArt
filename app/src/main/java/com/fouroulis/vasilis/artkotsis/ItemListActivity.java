package com.fouroulis.vasilis.artkotsis;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.concurrent.TimeUnit;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a activity representing
 * PaperItem details. On tablets, the activity presents the list of items and
 * PaperItem details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements ItemDetailFragment.OnDestroyFragmentListener, ItemGridFragment.ChangeSelectionIntoNavigation {
    private List<PaperItem> paperItems;

    private boolean started = false;

    private Handler handler = new Handler();

    private SimpleItemRecyclerViewAdapter adapter;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(paperItems != null) {
                openGridFragment(paperItems);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        hideStatusBar();

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;

        try {
            paperItems = createList();
            setupRecyclerView((RecyclerView) recyclerView, paperItems);
            openGridFragment(paperItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    public void stopTimer() {
        started = false;
        handler.removeCallbacks(runnable);
    }

    public void startTimer() {
        started = true;
        handler.postDelayed(runnable, TimeUnit.MINUTES.toMillis(1));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    private void hideStatusBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void openGridFragment(List<PaperItem> paperItems){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.item_detail_container, ItemGridFragment.newInstance(paperItems))
                .commit();
    }

    private List<PaperItem> createList() throws IOException {
        List<PaperItem> paperItems = new ArrayList<>();
        List<String> images =  getImages(this);
        images.subList(images.size() - 2, images.size()).clear();

        String[] bytes = getResources().getStringArray(R.array.bytes);
        String[] dates = getResources().getStringArray(R.array.dates);
        String[] time = getResources().getStringArray(R.array.times);

        for(int i = 0; i < images.size(); i++){
            try {
                PaperItem item = new PaperItem((i) + "/" + images.size(),i,bytes[i],dates[i],time[i],images.get(i));
                paperItems.add(item);
            } catch (java.lang.IndexOutOfBoundsException e){
                PaperItem item = new PaperItem((i) + "/" + images.size(),i,bytes[0],dates[0],time[0],images.get(i));
                paperItems.add(item);
            }

        }

        return paperItems;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, final List<PaperItem> paperItems) {
        adapter = new SimpleItemRecyclerViewAdapter(this,paperItems);
        adapter.setOnPaperClickListener(position -> getSupportFragmentManager().beginTransaction()
                .replace(R.id.item_detail_container, ItemDetailFragment.newInstance(paperItems.get(position)))
                .commit());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyFragment(boolean isDestroyed) {
        if(isDestroyed){
            stopTimer();
        }
    }

    @Override
    public void onStartFragment(boolean isStarted) {
        if(isStarted){
            startTimer();
        }
    }

    @Override
    public void onGridItemSelect(int position) {

        assert(adapter != null);
        adapter.setSelectedPos(position + 1 );
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

                if(selectedPos == position){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        holder.itemView.setBackgroundColor(mParentActivity.getColor(R.color.alpha));
                    } else {
                        holder.itemView.setBackgroundColor(mParentActivity.getResources().getColor(R.color.alpha));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        holder.itemView.setBackgroundColor(mParentActivity.getColor(R.color.trasparent));
                    } else {
                        holder.itemView.setBackgroundColor(mParentActivity.getResources().getColor(R.color.trasparent));
                    }
                }

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
                view.setOnClickListener(view1 -> {
                    onClickListener.onClick(getAdapterPosition());
                    selectedPos = getLayoutPosition();
                    notifyDataSetChanged();
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

        public void setSelectedPos(int pos){
            selectedPos = pos;
            notifyDataSetChanged();
        }
    }

    private List<String> getImages(Context context) throws IOException {
        String[] images = context.getAssets().list("images");
        return new ArrayList<>(Arrays.asList(images));
    }
}
