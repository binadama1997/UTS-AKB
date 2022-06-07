package com.rex1997.uts_akb;

// NIM      : 10121702
// NAMA     : BINA DAMAREKSA
// KELAS    : AKB IF-7

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditMemo extends AppCompatActivity {

    private EditText etText;
    private Memo memo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        this.etText =  findViewById(R.id.etText);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel =  findViewById(R.id.btnCencel);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            memo = (Memo)bundle.get("MEMO");
            if (memo != null){
                this.etText.setText(memo.getText());
            }
        }

        btnSave.setOnClickListener(v -> onSaveClicked());
        btnCancel.setOnClickListener(v -> finish());
    }

    public void onSaveClicked(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        if (memo == null){
            Memo temp = new Memo();
            temp.setText(etText.getText().toString());
            databaseAccess.save(temp);
        }else {
            memo.setText(etText.getText().toString());
            databaseAccess.update(memo);
        }
        databaseAccess.close();
        this.finish();
    }
}
