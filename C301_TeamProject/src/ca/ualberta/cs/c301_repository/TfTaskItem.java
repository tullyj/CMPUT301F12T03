package ca.ualberta.cs.c301_repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_utils.Utility;

/**
 * TfTaskItem encapsulates an item requirement in a task. Example: user can
 * define a photo item requirement, give the number of photos requested, and
 * the Item will contain all the photos added.
 * @author colinhunt
 *
 */
public class TfTaskItem implements TaskItem {

    private List<String> fileList = new ArrayList<String>();
    
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
        List<File> filePtrList = new ArrayList<File>();
        for (int i = 0; i < fileList.size(); ++i) {
            filePtrList.add(Utility.base64ToFile(fileList.get(i), 
                    type.toString() + i));
        }
        return filePtrList;
    }

    public void addFiles(List<File> files) throws Exception {
        for (File f : files) {
            fileList.add(Utility.fileToBase64(f));
        }
    }
    
    public File getFile(int index) {
        return Utility.base64ToFile(fileList.get(index), 
                type.toString() + index);
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
