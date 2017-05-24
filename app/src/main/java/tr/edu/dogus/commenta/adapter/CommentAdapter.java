package tr.edu.dogus.commenta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import tr.edu.dogus.commenta.model.Comment;
import tr.edu.dogus.commenta.R;


/**
 * Created by ardaltunyay on 22.05.2017.
 */

public class CommentAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Comment> comments;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null)
            row = layoutInflater.inflate(R.layout.row_comment, null);

        TextView tvUserName = (TextView) row.findViewById(R.id.tvUserName);
        TextView tvComment = (TextView) row.findViewById(R.id.tvComment);
        TextView tvDate = (TextView) row.findViewById(R.id.tvDate);

        tvUserName.setText(comments.get(position).getUserId());
        tvComment.setText(comments.get(position).getComment());
        tvDate.setText(comments.get(position).getDate());


        return row;
    }
}