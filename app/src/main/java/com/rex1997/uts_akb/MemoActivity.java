package com.rex1997.uts_akb;

// NIM      : 10121702
// NAMA     : BINA DAMAREKSA
// KELAS    : AKB IF-7

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MemoActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseAccess databaseAccess;
    private List<Memo> memos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        databaseAccess = DatabaseAccess.getInstance(this);

        listView = findViewById(R.id.listView);
        Button btnBuat = findViewById(R.id.btnBuat);

        btnBuat.setOnClickListener(v -> {
            Intent intent  = new Intent(MemoActivity.this, EditMemo.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseAccess.open();
        this.memos = databaseAccess.getAllMemos();
        databaseAccess.close();
        MemoAdapter adapter = new MemoAdapter(this,memos);
        this.listView.setAdapter(adapter);
    }

    private class MemoAdapter extends ArrayAdapter<Memo>{

        MemoAdapter(@NonNull Context context, List<Memo> objects) {
            super(context, 0,  objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.layout_list_item, parent, false);
            }

            Button btnEdit = convertView.findViewById(R.id.btnEdit);
            Button btnDelete = convertView.findViewById(R.id.btnDelete);

            TextView txtDate =  convertView.findViewById(R.id.txtDate);
            TextView txtMemo = convertView.findViewById(R.id.txtMemo);

            final Memo memo = memos.get(position);
            txtDate.setText(memo.getDate());
            txtMemo.setText(memo.getShortText());

            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(MemoActivity.this, EditMemo.class);
                intent.putExtra("MEMO", memo);
                startActivity(intent);
            });

            btnDelete.setOnClickListener(v -> {
                databaseAccess.open();
                databaseAccess.delete(memo);
                databaseAccess.close();

                ArrayAdapter<Memo> adapter = (ArrayAdapter<Memo>) listView.getAdapter();
                adapter.remove(memo);
                adapter.notify();
            });
            return convertView;
        }
    }
}