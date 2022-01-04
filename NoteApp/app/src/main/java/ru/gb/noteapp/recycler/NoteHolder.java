package ru.gb.noteapp.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import ru.gb.noteapp.R;
import ru.gb.noteapp.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private TextView priority;
    private TextView executeTo;
    private String priorityText;
    private Note note;

    public NoteHolder(@NonNull View itemView, NotesAdapter.OnNoteClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        priority = itemView.findViewById(R.id.note_importance);
        executeTo = itemView.findViewById(R.id.execute_to);

        priorityText = itemView.getResources().getString(R.string.priority);

        itemView.setOnClickListener(v -> listener.onNoteClick(note));
    }

    void bind(Note note)
    {
        this.note = note;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        String priorityDescription = String.format("%s %s", note.getPriority(), priorityText.toLowerCase());

        if(note.getExecuteTo() != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            priorityDescription = String.format("%s (Execute before %s)", priorityDescription, dateFormat.format(note.getExecuteTo()));
        }
        priority.setText(priorityDescription);
    }
}
