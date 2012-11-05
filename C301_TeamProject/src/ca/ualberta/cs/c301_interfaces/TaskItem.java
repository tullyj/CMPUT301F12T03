package ca.ualberta.cs.c301_interfaces;

import java.io.File;
import java.util.List;

/**
 * Interface for interacting with an item object.
 * @author colinhunt
 *
 */
public interface TaskItem {
    
    public List<File> getAllFiles();
    
    public Boolean addFiles(List<File> files);
    
    public ItemType getType();
    
    public Integer getNumber();
    
    public String getDescription();
}
