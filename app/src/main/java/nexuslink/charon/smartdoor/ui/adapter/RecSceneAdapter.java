package nexuslink.charon.smartdoor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nexuslink.charon.smartdoor.R;
import nexuslink.charon.smartdoor.contract.main.MainContract;
import nexuslink.charon.smartdoor.model.main.MainModel;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2018/3/6 15:59
 * 修改人：Charon
 * 修改时间：2018/3/6 15:59
 * 修改备注：
 */

public class RecSceneAdapter extends RecyclerView.Adapter{
    private List<MainModel.ItemModel> list;
    private Context mContext;
    private static final String TAG = RecDoorAdapter.class.getSimpleName();
    private MainContract.View mainView;


    public RecSceneAdapter( Context mContext,List<MainModel.ItemModel> list ,MainContract.View mainView) {
        this.mContext = mContext;
        this.list = list;
        this.mainView = mainView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder)holder).mIv.setImageResource(list.get(position).getImg());
        ((MyViewHolder)holder).mTv.setText(list.get(position).getName());
        ((MyViewHolder)holder).mRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainView.changeMode(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIv;
        private TextView mTv;
        private RelativeLayout mRl;
        public MyViewHolder(View itemView) {
            super(itemView);
            mRl = itemView.findViewById(R.id.pop_item_relative);
            mIv = itemView.findViewById(R.id.pop_item_img);
            mTv = itemView.findViewById(R.id.pop_item_text);
        }
    }
}
