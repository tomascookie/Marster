package ru.brainix.ept.marster.list.setting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import ru.brainix.ept.marster.MainActivity;
import ru.brainix.ept.marster.MainModel;
import ru.brainix.ept.marster.R;

public class DialogFragment extends android.support.v4.app.DialogFragment {

    private final String LOG_TAG = "DialogFragment  ";

    //Создаем диалоговое окно
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int imageId = getArguments().getInt("image_id");

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.yes_button, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Меняем статус отображения картинки на неактивный, кэш оставляем
                        MainModel model = new MainModel();

                        model.delImage(getContext(), imageId);

                        //Обновляем данные в списке
                        ((MainActivity) getContext()).startRecycler();




                    }
                })
                .setNegativeButton(R.string.no_button, null)
                .create();
    }
}