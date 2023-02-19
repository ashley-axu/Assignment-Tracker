package com.assignmenttracker;

//Super class
public class Assignment{
    private String name;
    private String type;
    private String dueDate;
    private String assignClass;
    private String notes;
    private String edit;

    public Assignment(String name, String type, String dueDate, String assignClass, String notes, String edit)
    {
        this.name = name;
        this.type = type;
        this.dueDate = dueDate;
        this.assignClass = assignClass;
        this.notes = notes;
        this.edit = edit;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getType(){
        return type;
    }
    public void setTYpe(String type){
        this.type = type;
    }
    public String getDueDate(){
        return dueDate;
    }
    public void setDueDate(String dueDate){
        this.dueDate = dueDate;
    }
    public String getAssignClass(){
        return assignClass;
    }
    public void setAssignClass(String assignClass){
        this.assignClass = assignClass;
    }
    public String getNotes(){
        return notes;
    }
    public void setNotes(String notes){
        this.notes = notes;
    }
    public String getEdit()
    {
        return edit;
    }

    public void setEdit(String edit)
    {
        this.edit = edit;
    }

    // toString() method
    public String toString(){
        return "Enter assignment name: " + this.name + "Select Assignment Type" + this.type
        + "Select Due Date: " + this.dueDate + "Select the class: " + this.assignClass + 
                "Enter Notes(E.g 'Chapters 5-6')" + this.notes;
    }
    
}
