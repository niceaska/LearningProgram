package ru.niceaska.learningprogram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class DisplayModeAdapter extends ArrayAdapter<String> {

    DisplayModeAdapter(Context context, List <String> mods) {
        super(context, android.R.layout.simple_list_item_1, mods);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            DisplayModeAdapter.ViewHolder viewHolder = new DisplayModeAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        DisplayModeAdapter.ViewHolder holder = (DisplayModeAdapter.ViewHolder) convertView.getTag();
        holder.mMods.setText(getItem(position));
        return convertView;
    }

    private class ViewHolder {
        private final TextView mMods;

        ViewHolder(View v) {
            mMods = v.findViewById(android.R.id.text1);
        }
    }
}
