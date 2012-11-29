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
    
    public void addFiles(List<File> files) throws Exception;
    
    public File getFile(int index);
    
    public ItemType getType();
    
    public Integer getNumber();
    
    public String getDescription();
}
