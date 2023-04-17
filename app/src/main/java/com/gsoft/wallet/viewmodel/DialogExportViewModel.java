package com.gsoft.wallet.viewmodel;

import android.view.View;

import com.gsoft.wallet.R;
import com.gsoft.wallet.model.database.DatabaseHelper;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.view.dialog.ExportDialog;

public class DialogExportViewModel {
    public DialogExportViewModel(ExportDialog dialog) {
        dialog.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = dialog.editTextFilename.getText().toString();
                if (filename.isEmpty()) {
                    dialog.editTextFilename.setError(new Utils(dialog.context).getString(R.string.message_filename_invalid));
                } else {
                    DatabaseHelper database = new DatabaseHelper(dialog.context);
                    database.saveFile(dialog.editTextFilename.getText().toString());
                    dialog.dismiss();
                }
            }
        });
    }
}
