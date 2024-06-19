package com.nguyennguyenhoanganh.myapplication;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private List<Book> bookList;
    private BookAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        loadBooks();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDialog(bookList.get(position));
                return true;
            }
        });

        Button btnAddBook = findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    private void loadBooks() {
        bookList = db.getAllBooks();
        adapter = new BookAdapter(this, bookList);
        listView.setAdapter(adapter);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_book, null);
        builder.setView(dialogView);

        final EditText edtBookName = dialogView.findViewById(R.id.edtBookName);
        final EditText edtBookPrice = dialogView.findViewById(R.id.edtBookPrice);

        builder.setTitle("Add Book")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = edtBookName.getText().toString();
                        double price = Double.parseDouble(edtBookPrice.getText().toString());
                        db.addBook(new Book(0, name, price));
                        loadBooks();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEditDialog(final Book book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_book, null);
        builder.setView(dialogView);

        final EditText edtBookName = dialogView.findViewById(R.id.edtBookName);
        final EditText edtBookPrice = dialogView.findViewById(R.id.edtBookPrice);

        edtBookName.setText(book.getName());
        edtBookPrice.setText(String.valueOf(book.getPrice()));

        builder.setTitle("Edit Book")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        book.setName(edtBookName.getText().toString());
                        book.setPrice(Double.parseDouble(edtBookPrice.getText().toString()));
                        db.updateBook(book);
                        loadBooks();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteBook(book.getId());
                        loadBooks();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
