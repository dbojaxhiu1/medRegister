package com.example.medregister.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.example.medregister.R;
import com.example.medregister.models.Pill;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PillListAdapter extends ArrayAdapter<Pill> {

    private static final String TAG = "PillListAdapter";

    private int mLayoutResource;
    private Context mContext;
    private LayoutInflater mInflater;

    public PillListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pill> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayoutResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder {
        TextView pillName, pillInstruction, pillNumberInPackage, pillDailyUsage;
        ImageView mTrash;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.pillName = (TextView) convertView.findViewById(R.id.pill_name_list);
            holder.pillInstruction = (TextView) convertView.findViewById(R.id.pill_instruction_list);
            holder.pillNumberInPackage = (TextView) convertView.findViewById(R.id.pill_number_in_package_list);
            holder.pillDailyUsage = (TextView) convertView.findViewById(R.id.pill_daily_usage_list);
            holder.mTrash = (ImageView) convertView.findViewById(R.id.icon_trash);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            //set the pill name
            holder.pillName.setText(getItem(position).getPill_name());
            holder.pillInstruction.setText(getItem(position).getPill_instruction());
            holder.pillNumberInPackage.setText(getItem(position).getPill_number_in_package());
            holder.pillDailyUsage.setText(getItem(position).getPill_daily_usage());

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child(mContext.getString(R.string.db_pills))
                    .orderByChild(mContext.getString(R.string.field_pill_id));

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: Found pill: ");

                        //Problem should be here for listing pills
                        String pill_name = "Pill Name: " + singleSnapshot.getValue(Pill.class).getPill_name();
                        holder.pillName.setText(pill_name);
                        holder.pillInstruction.setText(singleSnapshot.getValue(Pill.class).getPill_instruction());
                        String number_in_package = "Package contains: " + singleSnapshot.getValue(Pill.class).getPill_number_in_package() + " pills";
                        holder.pillNumberInPackage.setText(number_in_package);
                        String daily_usage = "Daily Usage: " + singleSnapshot.getValue(Pill.class).getPill_daily_usage();
                        holder.pillDailyUsage.setText(daily_usage);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            holder.mTrash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getItem(position).getCreator_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        Log.d(TAG, "onClick: asking for permission to delete icon.");
                        //((ChatActivity)mContext).showDeletePillDialog(getItem(position).getPill_id());
                    } else {
                        Toast.makeText(mContext, "You didn't create this pill", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } catch (NullPointerException e) {
            Log.e(TAG, "getView: NullPointerException: ", e.getCause());
        }
        return convertView;
    }
}