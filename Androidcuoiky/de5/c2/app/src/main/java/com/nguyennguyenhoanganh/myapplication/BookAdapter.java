package com.nguyennguyenhoanganh.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends BaseAdapter {
    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_book, parent, false);
        }

        TextView txtBookName = convertView.findViewById(R.id.txtBookName);
        TextView txtBookPrice = convertView.findViewById(R.id.txtBookPrice);

        Book book = bookList.get(position);
        txtBookName.setText(book.getName());
        txtBookPrice.setText(String.valueOf(book.getPrice()));

        return convertView;
    }
}
