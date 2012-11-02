package ca.ualberta.cs.c301_repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_interfaces.TaskItem;

public class TfTaskItem implements TaskItem {

    private List<File> fileList = new ArrayList<File>();
    
    private ItemType type;
    
    private Integer number;
    
    private String description;

    public TfTaskItem(ItemType type, Integer number, String description) {
        super();
        this.type = type;
        this.number = number;
        this.description = description;
    }

    public List<File> getAllFiles() {
        return fileList;
    }

    public Boolean addFiles(List<File> files) {
        Boolean success = fileList.addAll(files);
        return success;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
