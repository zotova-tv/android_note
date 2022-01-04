package ru.gb.noteapp.data;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    public static String[] IMPORTANCE_CHOICES = {"Low", "Normal", "High"};
    private Integer id;
    private String title;
    private String description;
    private String priority;
    private Date executeTo = null;

    // public Note() {}

    public Note(String title, String description) {
        this(title, description, "Normal", null);
    }

    public Note(String title, String description, String priority) {
        this(title, description, priority, null);
    }

    public Note(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = "Normal";
    }

    public Note(String title, String description, String priority, Date executeTo) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.executeTo = executeTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", importance='" + priority + '\'' +
                '}';
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getExecuteTo() {
        return executeTo;
    }

    public void setExecuteTo(Date executeTo) {
        this.executeTo = executeTo;
    }
}
