package ca.ualberta.cs.c301_teamproject;

import java.io.File;
import java.util.List;

public interface TaskItem {
    
    public List<File> getAllFiles();
    
    public ItemType getType();
    
    public Integer getNumber();
    
    public String getDescription();
}
