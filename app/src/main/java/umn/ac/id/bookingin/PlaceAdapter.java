package umn.ac.id.bookingin;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.VH> {

    private List<UserPlaceInfo> placeList = new ArrayList<>();

    private static final String TAG ="PlaceAdapter";
    int count = 0;


    public PlaceAdapter(List<UserPlaceInfo> placeList) {
        this.placeList = placeList;

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: "+ count++);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.place_list, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        UserPlaceInfo Id = placeList.get(position);
        holder.txtname.setText(Id.getPlaceName());
        holder.txtloc.setText(Id.getPlaceLoc());


    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        private TextView txtname, txtloc;
        Context nContext;

        public VH(@NonNull View itemView) {
            super(itemView);
            txtname = (TextView)itemView.findViewById(R.id.placeListName);
            txtloc = (TextView)itemView.findViewById(R.id.placeListLoc);
        }
    }
}