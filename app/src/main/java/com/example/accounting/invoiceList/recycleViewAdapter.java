package com.example.accounting.invoiceList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accounting.R;

import java.util.ArrayList;


/**
 * Class recycleViewAdapter: The Adapter for InvoiceList(type:recycleList)
 */
public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<PostInvoice> mData;

    /**
     * Constructor for recycleViewAdapter
     * @param context
     * @param data  the invoice array which want to view
     */
    public recycleViewAdapter(Context context, ArrayList<PostInvoice> data) {
        this.mContext = context;
        this.mData = data;
    }

    /**
     * This method set the view, wrapping with the holder and initialize the widget to holder
     * @param parent
     * @param viewType
     * @return holder: ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cell_post,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.tvContent = view.findViewById(R.id.tvContent);
        holder.tvPosterName = view.findViewById(R.id.tvPosterName);
        return holder;
    }

    /**
     * This method used to bind the data with holder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PostInvoice postinvoice = mData.get(position);
        holder.tvPosterName.setText(postinvoice.posterName);
        holder.tvContent.setText(postinvoice.content);

        /*
        TODO:Used or NOT Used
         */
        /*if (post.content == null) {
            holder.tvContent.setVisibility(View.GONE);
        }*/
    }

    /**
     * This method used to get the number of current items
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * Class ViewHolder
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPosterName;
        public TextView tvContent;

        /**
         * Constructor for ViewHolder
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }



}
