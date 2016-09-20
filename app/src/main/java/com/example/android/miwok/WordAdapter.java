/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link WordAdapter} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Word} objects.
 */
public class WordAdapter extends ArrayAdapter<Word>  {

    /**
     * Create a new {@link WordAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param words is the list of {@link Word}s to be displayed.
     */
    public WordAdapter(Context context, ArrayList<Word> words) {

        //Here we initialize the ArrayAdapter's internal storage for the context and the list.
        //the first parameter, context is the NumbersActivity in this case
        //the second parameter, the layout resource id is 0 because we are not going to use the ArrayAdapter to inflate or create a single text view which is the default, we are going to use a custom view.
        //android.R.layout.simple_list_item_1 is an example of a layout resouce id that has only 1 textview
        //the third parameter, words is the ArrayList of Word objects passed in the declaration from the NumbersActivity.java class
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        //the view will be null if you are creating items in the list in the view for the first time when first opening the activity
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list, the getItem method is originally defined in the ArrayAdapter you extended
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        defaultTextView.setText(currentWord.getDefaultTranslation());

        //for the Phase Activity, there will be no image displayed since there is none
        ImageView miwokImageView = (ImageView) listItemView.findViewById(R.id.miwok_image);
        if (currentWord.hasImage()) {
            miwokImageView.setImageResource(currentWord.getmMiwokImageId());
        }
        else
        {
            //View.GONE - this view is invisible, and it doesn't take any space for layout purpose
            miwokImageView.setVisibility(View.GONE);

            //convert 88dp into pixels
            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (88 * scale + 0.5f);

            //get the relative layout then set the height to 88dp equialent in pixels
            RelativeLayout r1 = (RelativeLayout) listItemView.findViewById(R.id.items_layout);
            RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, pixels);
            r1.setLayoutParams(rel_btn);
        }

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}