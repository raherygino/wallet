package com.gsoft.wallet.view.dialog;

import static com.gsoft.wallet.utils.Utils.LIGHT;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import com.gsoft.wallet.R;
import com.gsoft.wallet.utils.Utils;
import com.gsoft.wallet.viewmodel.DialogExportViewModel;

public class ExportDialog extends SweetDialog{

    public EditText editTextFilename;
    public Context context;
    public Button btnOk;

    public ExportDialog(Context context) {
        super(context, R.layout.dialog_export);
        onCancel(R.id.btn_cancel_dialog_export);
        this.context = context;
        this.initView();
        new DialogExportViewModel(this);
    }

    private void initView() {
        Utils utils = new Utils(context);
        editTextFilename = (EditText) setView(R.id.edit_filename,utils.getString(R.string.default_filename) ,LIGHT);
        btnOk = (Button) setView(R.id.btn_ok_dialog_export, utils.getString(R.string.ok), LIGHT);
    }


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
