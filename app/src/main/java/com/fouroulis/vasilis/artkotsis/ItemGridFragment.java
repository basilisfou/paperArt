package com.fouroulis.vasilis.artkotsis;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fouroulis.vasilis.artkotsis.databinding.FragmentGridBinding;
import com.fouroulis.vasilis.artkotsis.databinding.ItemDetailBinding;
import com.fouroulis.vasilis.artkotsis.model.PaperItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * on handsets.
 */
public class ItemGridFragment extends Fragment {
    /**
     * The fragment argument representing the PaperItem ID that this fragment
     * represents.
     */
    public static final String ARG_ITEMS = "items";
    private AutoClearedValue<FragmentGridBinding> binding;
    private List<PaperItem> paperItems;

    public ItemGridFragment() {

    }

    public static ItemGridFragment newInstance(List<PaperItem> paperItems) {

        Bundle args = new Bundle();
        Gson gson = new GsonBuilder().create();

        JsonArray myCustomArray = gson.toJsonTree(paperItems).getAsJsonArray();
        args.putString(ARG_ITEMS,myCustomArray.toString());
        ItemGridFragment fragment = new ItemGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEMS)) {
            String jsonString = getArguments().getString(ARG_ITEMS);
            Gson gson = new GsonBuilder().create();
            Type type = new TypeToken<List<PaperItem>>() {}.getType();
            paperItems = gson.fromJson(jsonString, type);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentGridBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_grid, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.get().gridRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        binding.get().gridRecyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this,paperItems));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<PaperItem> mValues;
        private int selectedPos = RecyclerView.NO_POSITION;
        private ItemListActivity.SimpleItemRecyclerViewAdapter.OnClickListener onClickListener;
        private Fragment fragment;

        SimpleItemRecyclerViewAdapter(Fragment fragment,List<PaperItem> items) {
            mValues = items;
            this.fragment = fragment;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_detail, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final SimpleItemRecyclerViewAdapter.ViewHolder holder,
                                     int position) {
            Glide.with(fragment)
                    .load(Uri.parse("file:///android_asset/images/" + mValues.get(position).getImage()))
                    .into(holder.mImage);

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public void setOnPaperClickListener(ItemListActivity.SimpleItemRecyclerViewAdapter.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView mImage;

            ViewHolder(View view) {
                super(view);
                mImage = view.findViewById(R.id.image_view_grid);

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

        interface OnClickListener {
            void onClick(int position);
        }

        private int getColorCustom(Context context, int resColor){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return context.getColor(resColor);
            } else {
                return context.getResources().getColor(resColor);
            }
        }
    }

}
