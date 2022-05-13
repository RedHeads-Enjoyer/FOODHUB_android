package com.example.foodhub;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodhub.Add.AddRecipeFragment;
import com.example.foodhub.Search.SearchRecipeFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final int CARD_ITEM_SIZE = 3;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull @Override public Fragment createFragment(int position) {
        Fragment page_fragment = null;

        switch (position) {
            case 0:
                page_fragment = new SearchRecipeFragment();
                break;
            case 1:
                page_fragment = new AddRecipeFragment();
                break;
            default:
                page_fragment = new UserProfileFragment();
                break;
        }
        return page_fragment;
    }
    @Override public int getItemCount() {
        return CARD_ITEM_SIZE;
    }
}