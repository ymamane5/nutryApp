package com.example.nadav.nutryapp.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.nadav.nutryapp.Models.User;
import com.example.nadav.nutryapp.R;

public class RegisterDialog extends DialogFragment {

    Button btnReg, btnCancel;
    EditText uName, uPass;
    TextView err;

    private RegisterHandler registerHandler;
    public interface RegisterHandler {
        void registerBtnPressed(User u);
    }

    public static RegisterDialog newInstance() {return new RegisterDialog(); }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisterHandler) {
            registerHandler = (RegisterHandler) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement 'RegisterHandler' interface in fragment 'RegisterDialog'.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_register, container, false);

        btnReg = view.findViewById(R.id.bt_reg);
        btnCancel = view.findViewById(R.id.bt_cancel);
        uName = view.findViewById(R.id.et_uName);
        uPass = view.findViewById(R.id.et_Pass);
        err = view.findViewById(R.id.tv_error);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = uName.getText().toString();
                String pass = uPass.getText().toString();

                if (name.equals("") || pass.equals("")) {
                    err.setText("one or more fields are empty");
                }
                else {
                    err.setText("");
                    registerHandler.registerBtnPressed(new User(name, pass));
                    dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }


}
