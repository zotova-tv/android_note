package ru.gb.noteapp.recycler;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.gb.noteapp.R;
import ru.gb.noteapp.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    private TextView title;
    private TextView description;
    private TextView priority;
    private String priorityText;
    private Note note;

    private ImageView noteMenu;
    private PopupMenu popupMenu;
    private PopupMenuItemClickListener listener;

    public NoteHolder(@NonNull View itemView, PopupMenuItemClickListener listener) {
        super(itemView);

        this.listener = listener;

        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        priority = itemView.findViewById(R.id.note_importance);

        priorityText = itemView.getResources().getString(R.string.priority);

        noteMenu = itemView.findViewById(R.id.show_note_item_actions);

        popupMenu = new PopupMenu(itemView.getContext(), noteMenu);
        popupMenu.inflate(R.menu.note_item_actions);

        noteMenu.setOnClickListener(v -> popupMenu.show());
        popupMenu.setOnMenuItemClickListener(this);

        // itemView.setOnClickListener(v -> listener.onNoteClick(note));
    }

    void bind(Note note)
    {
        this.note = note;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        String priorityDescription = String.format("%s %s", note.getPriority(), priorityText.toLowerCase());

        if(note.getExecuteTo() != null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            priorityDescription = String.format("%s (Execute before %s)", priorityDescription, dateFormat.format(note.getExecuteTo()));
        }
        priority.setText(priorityDescription);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actions_delete:
            case R.id.actions_modify:
                listener.click(item.getItemId(), note, getAdapterPosition());
                return true;
            default:
                return false;
        }


    }
}
