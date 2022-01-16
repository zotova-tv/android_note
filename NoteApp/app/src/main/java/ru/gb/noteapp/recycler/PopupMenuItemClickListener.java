package ru.gb.noteapp.recycler;

import ru.gb.noteapp.data.Note;

public interface PopupMenuItemClickListener {
    void click(int command, Note note, int position);
}
