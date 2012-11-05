package ca.ualberta.cs.c301_teamproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemListAdapter extends ArrayAdapter<ItemListElement>{
	Context context;
	int layoutRID;
	ItemListElement data[] = null;
	
	public ItemListAdapter(Context context, int layoutRID, ItemListElement[] data){
		super(context, layoutRID, data);
		this.layoutRID = layoutRID;
		this.context = context;
		this.data = data;
	}
	
	static class ItemListHolder{
		ImageView rowIcon;
		TextView rowTitle;
		TextView rowSub;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;
		ItemListHolder holder = null;
		
		if(row == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutRID, parent, false);
			
			holder = new ItemListHolder();
			holder.rowIcon = (ImageView) row.findViewById(R.id.rowIcon);
			holder.rowTitle = (TextView) row.findViewById(R.id.rowTitle);
			holder.rowSub = (TextView) row.findViewById(R.id.rowSub);
			
			row.setTag(holder);
		}else{
			holder = (ItemListHolder) row.getTag();
		}
		ItemListElement itemElement = data[position];
		holder.rowIcon.setImageResource(itemElement.icon);
		holder.rowTitle.setText(itemElement.title);
		holder.rowSub.setText(itemElement.sub);
		
		return row;
	}
}
