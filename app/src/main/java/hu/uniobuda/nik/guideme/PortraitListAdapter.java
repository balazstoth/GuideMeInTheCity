package hu.uniobuda.nik.guideme;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tothb on 2017. 04. 19..
 */

public class PortraitListAdapter extends BaseAdapter
{
    List<Monument> items;

    public PortraitListAdapter(List<Monument> items)
    {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Monument c = items.get(i);
        String monumentString = "Name: " + c.getName() + "\n" + "Built in: " + c.getDate() + ", Rate: " + c.getRate();
        View listItemView = view;

        if(listItemView == null)
            listItemView = View.inflate(parent.getContext(), R.layout.list_monument_p, null);

        TextView textView_monument = (TextView) listItemView.findViewById(R.id.textView_monument);
        textView_monument.setText(monumentString);
        return listItemView;
    }

    public void RefreshList(List<Monument> list) {
        this.items = list;

    }
}
