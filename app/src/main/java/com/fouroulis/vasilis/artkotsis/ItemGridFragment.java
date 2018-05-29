package com.fouroulis.vasilis.artkotsis;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private AutoClearedValue<ItemDetailBinding> binding;
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
        ItemDetailBinding dataBinding = DataBindingUtil.inflate(inflater,
                R.layout.item_detail, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        try {
////            binding.get().imageView.setImageDrawable(takeImageFromAssets(paperItem.getImage()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private Drawable takeImageFromAssets(String image) throws IOException {
        InputStream inputstream = getActivity().getAssets().open("images/" +image);
        return Drawable.createFromStream(inputstream, null);
    }

}
