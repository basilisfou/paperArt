package com.fouroulis.vasilis.artkotsis;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fouroulis.vasilis.artkotsis.databinding.ItemDetailBinding;
import com.fouroulis.vasilis.artkotsis.model.PaperItem;

import java.io.IOException;
import java.io.InputStream;


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
    private OnDestroyFragmentListener onDestroyFragment;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        onDestroyFragment = (OnDestroyFragmentListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM)) {
            paperItem = (PaperItem) getArguments().getSerializable(ARG_ITEM);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroyFragment.onDestroyFragment(true);
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
        onDestroyFragment.onStartFragment(true);
        binding.get().setItem(paperItem);
    }

    public interface OnDestroyFragmentListener {
        void onDestroyFragment(boolean isDestroyed);
        void onStartFragment(boolean isStarted);
    }

}
