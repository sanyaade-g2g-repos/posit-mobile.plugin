package org.hfoss.posit.android.plugin.outsidein;

import java.util.List;

import org.hfoss.posit.android.R;
import org.hfoss.posit.android.api.Find;
import org.hfoss.posit.android.api.fragment.FindFragment;
import org.hfoss.posit.android.api.fragment.ListFindsFragment;
import org.hfoss.posit.android.api.plugin.FindPluginManager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class OutsideInListFindsFragment extends ListFindsFragment {
	
	@Override
	protected void displayFind(int index, String action, Bundle extras, FindFragment findFragment) {
		super.displayFind(index, action, extras, new OutsideInFindFragment());
	}
	
	/**
	 * Sets up a custom list adapter specific to OutsideIn finds.
	 */
	@Override
	protected ListAdapter setUpAdapter() {

		List<? extends Find> list = this.getHelper().getAllFinds();

		int resId = getResources().getIdentifier(FindPluginManager.mFindPlugin.mListFindLayout,
			    "layout", getActivity().getPackageName());
		
		OutsideInFindsListAdapter adapter = new OutsideInFindsListAdapter(getActivity(),
				resId, list);

		return adapter;
	}

	/**
	 * Adapter for displaying finds, extends FindsListAdapter to 
	 * take care of displaying the extra fields in OutsideInFind.
	 * 
	 */
	private class OutsideInFindsListAdapter extends FindsListAdapter{

		public OutsideInFindsListAdapter(Context context, int textViewResourceId,
				List list) {
			super(context, textViewResourceId, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.outsidein_list_row, null);
			}
			OutsideInFind find = (OutsideInFind)items.get(position);
			if (find != null) {
				TextView tv = (TextView) v.findViewById(R.id.guid);
				tv.setText(find.getGuid());
				tv = (TextView) v.findViewById(R.id.latitude);
				tv.setText(String.valueOf(find.getLatitude()));
				tv = (TextView) v.findViewById(R.id.longitude);
				tv.setText(String.valueOf(find.getLongitude()));
				tv = (TextView) v.findViewById(R.id.syringes_in);
				tv.setText(String.valueOf(find.getSyringesIn()));
				tv = (TextView) v.findViewById(R.id.syringes_out);
				tv.setText(String.valueOf(find.getSyringesOut()));
				tv = (TextView) v.findViewById(R.id.id);
				tv.setText(Integer.toString(find.getId()));
				tv = (TextView) v.findViewById(R.id.synced);
				tv.setText(find.getStatusAsString());			
				tv = (TextView) v.findViewById(R.id.timestamp);
				tv.setText(find.getTime().toLocaleString());
			}
			return v;
		}
	}
}
