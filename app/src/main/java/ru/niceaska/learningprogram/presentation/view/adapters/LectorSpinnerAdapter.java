package ru.niceaska.learningprogram.presentation.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class LectorSpinnerAdapter extends BaseAdapter {

    private final List<String> lectors;

    @Override
    public int getCount() {
        return lectors.size();
    }

    public LectorSpinnerAdapter(List<String> lectors) {
        this.lectors = lectors;
    }

    @Override
    public String getItem(int position) {
        return lectors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mLectors.setText(getItem(position));
        return convertView;
    }

    private class ViewHolder {
        private final TextView mLectors;

        public ViewHolder(View v) {
            mLectors = v.findViewById(android.R.id.text1);
        }
    }
}
