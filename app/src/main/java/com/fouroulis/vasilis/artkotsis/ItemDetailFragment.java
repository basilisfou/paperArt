package com.fouroulis.vasilis.artkotsis;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fouroulis.vasilis.artkotsis.databinding.ItemDetailBinding;
import com.fouroulis.vasilis.artkotsis.model.PaperItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the PaperItem ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM = "item";
    private AutoClearedValue<ItemDetailBinding> binding;
    private PaperItem paperItem;

    public ItemDetailFragment() {

    }

    public static ItemDetailFragment newInstance(PaperItem paperItem) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM,paperItem);
        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM)) {
            paperItem = (PaperItem) getArguments().getSerializable(ARG_ITEM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ItemDetailBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.item_detail, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.get().setItem(paperItem);

        try {
            binding.get().imageView.setImageDrawable(takeImageFromAssets(paperItem.getImage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Drawable takeImageFromAssets(String image) throws IOException {
        InputStream inputstream = getActivity().getAssets().open("images/" +image);
        return Drawable.createFromStream(inputstream, null);
    }

}
