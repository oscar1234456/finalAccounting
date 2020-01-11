package com.example.accounting.invoiceList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.accounting.R;
import com.example.accounting.dataStructure.expensesStruc;
import com.example.accounting.dataStructure.incomeStruc;

import java.util.ArrayList;


/**
 * Class recycleViewAdapter: The Adapter for InvoiceList(type:recycleList)
 */
public class recycleViewExpenseAdapter extends RecyclerView.Adapter<recycleViewExpenseAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<expensesStruc> mData;

    private OnItemClickListener mClickListener;

    /**
     * Constructor for recycleViewAdapter
     * @param context
     * @param data  the invoice array which want to view
     */
    public recycleViewExpenseAdapter(Context context, ArrayList<expensesStruc> data) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.ex_cell_post,parent,false);

        return new ViewHolder(view,mClickListener);
    }


    public interface OnItemClickListener{
        //引數（父元件，當前單擊的View,單擊的View的位置，資料）
        void onItemClick(View view, int postion);
    }
    /**
     * This method used to bind the data with holder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        expensesStruc postinvoice = mData.get(position);
        holder.tvPosterName.setText(postinvoice.getExpenseClass());
        holder.tvContent.setText(postinvoice.getExpenseAmount());

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
    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvPosterName;
        public TextView tvContent;
        private OnItemClickListener mListener;// 宣告自定義監聽介面
        /**
         * Constructor for ViewHolder
         * @param itemView
         */
        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mListener = listener;

            itemView.setOnClickListener(this);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvContent.setTextColor(Color.RED);
            tvPosterName = itemView.findViewById(R.id.tvPosterName);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v, getLayoutPosition());
        }

    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }


}
