package my.cleanhajo_project;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimerAdapter extends RecyclerView.Adapter<TimerAdapter.ViewHolder>{
    ArrayList<Timer> list = new ArrayList<Timer>();

    private String TAG = "Adapter";
    private Context mContext;
    private SparseBooleanArray mSeletedItems = new SparseBooleanArray(0);

    public TimerAdapter(Context context, ArrayList<Timer> arrayList) {
        this.list = arrayList;
        this.mContext =context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup timer, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(timer.getContext());
        View item = layoutInflater.inflate(R.layout.timer_item, timer, false);
        ViewHolder vh = new ViewHolder (item);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Timer t = list.get(position);
        holder.setItem(t);
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView texttime;
        TextView textname;
        ImageView textimage;
        TextView textsecond;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            textimage = itemView.findViewById(R.id.imageView);
            textname = itemView.findViewById(R.id.TV_W);
            texttime = itemView.findViewById(R.id.Time_w);
            textsecond = itemView.findViewById(R.id.Time_second);
        }

        public void setItem(Timer timer){
            textimage.setImageResource(timer.getImage());
            textname.setText(timer.getName());
            texttime.setText(timer.getTime());
            textsecond.setText(timer.getSecond());
        }
    }
}