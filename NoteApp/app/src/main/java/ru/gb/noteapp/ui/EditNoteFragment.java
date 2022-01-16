package ru.gb.noteapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.gb.noteapp.R;
import ru.gb.noteapp.data.Constants;
import ru.gb.noteapp.data.Note;

public class EditNoteFragment extends Fragment {
    private static final String TAG = "lalala ";
    private EditText title;
    private EditText description;
    private Spinner prioritySpinner;
    private DatePicker executeToDatePicker;
    private DatePickerDialog datePickerDialog;
    private EditText executeToDate;
    private ImageButton showDatePickerDialog;
    private Button saveNote;
    private Note note;
    private int position;

    public static EditNoteFragment getInstance(Note note, int position) {
        EditNoteFragment fragment = new EditNoteFragment();

        Bundle args = new Bundle();
        args.putSerializable(Constants.NOTE, note);
        args.putInt(Constants.POSITION, position);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
        title = view.findViewById(R.id.note_edit_title_text);
        description = view.findViewById(R.id.note_edit_description_text);
        prioritySpinner = view.findViewById(R.id.importance_spinner);
        prioritySpinner.setSelection(1); // Normal
        // executeToDatePicker = view.findViewById(R.id.execute_to_datepicker);
        executeToDate = view.findViewById(R.id.execute_to_date);
        showDatePickerDialog = view.findViewById(R.id.show_datepicker_dialog);
        saveNote = view.findViewById(R.id.edit_note_update);

        saveNote.setOnClickListener(v -> saveNoteClick());

        Calendar calendar = Calendar.getInstance();

        Bundle args = getArguments();
        if (args != null && args.containsKey(Constants.NOTE)) {
            note = (Note) args.getSerializable(Constants.NOTE);
            position = args.getInt(Constants.POSITION);
            Log.d(TAG, "EditNoteFragment onViewCreated() called with: " + note);
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            if (note.getExecuteTo() != null) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                executeToDate.setText(df.format(note.getExecuteTo()));
            }

            for (int i = 0; i < Note.PRIORITY_CHOICES.length; i++) {
                if (note.getPriority().equals(Note.PRIORITY_CHOICES[i])) {
                    prioritySpinner.setSelection(i, true);
                    break;
                }
            }

            if (note.getExecuteTo() != null) {
                // setDateToDatePicker(executeToDatePicker, note.getExecuteTo());

                calendar.setTime(note.getExecuteTo());
            }
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this.getContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        showDatePickerDialog.setOnClickListener(v -> {
            datePickerDialog.show();
        });
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Log.d(TAG, "onDateSet() called with: view = [" + view + "], year = [" + year + "], month = [" + month + "], dayOfMonth = [" + dayOfMonth + "]");
            executeToDate.setText(String.format("%02d-%02d-%d", dayOfMonth, (month + 1), year));
        }
    };

    private void saveNoteClick() {
        Log.d(TAG, "saveNoteClick() called");

        if (title.getText().toString().equals(Constants.EMPTY_STRING)) {
            Toast.makeText(getActivity(), "Please add title", Toast.LENGTH_SHORT).show();
            return;
        }
        Date executeToDateObject = null;
        String executeToStr = executeToDate.getText().toString();
        if (!executeToStr.matches(Constants.EMPTY_STRING)) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            try {
                executeToDateObject = format.parse(executeToStr);
            } catch (ParseException e) {
                Toast.makeText(getActivity(), "Invalid date format", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (note == null) {
            note = new Note(
                    title.getText().toString(),
                    description.getText().toString(),
                    prioritySpinner.getSelectedItem().toString(),
                    executeToDateObject);
        } else {
            note.setTitle(title.getText().toString());
            note.setDescription(description.getText().toString());
            note.setPriority(prioritySpinner.getSelectedItem().toString());
            note.setExecuteTo(executeToDateObject);
        }
        Log.d(TAG, "saveNoteClick() called note " + note);
        NotesListFragment parentFragment = (NotesListFragment) getParentFragment();
        parentFragment.updateNotesList(note, position);

        getParentFragmentManager().popBackStack();

    }

//    private Date getDateFromDatePicker(DatePicker datepicker){
//        int day = datepicker.getDayOfMonth();
//        int month = datepicker.getMonth();
//        int year = datepicker.getYear();
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day);
//        return calendar.getTime();
//    }
//
//    private void setDateToDatePicker(DatePicker datePicker, Date date){
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
//    }
}
