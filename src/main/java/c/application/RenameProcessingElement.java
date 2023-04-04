/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 98910
 */
class RenameProcessingElement implements ProcessingElement {

    static public List<Entry> process(List<Entry> entries, String suffix) {
        List<Entry> updatedEntries = new ArrayList<>();
        for (Entry entry:entries){
            String oldname = "";
            String path = "";
            if(entry instanceof RemoteEntry) {
                RemoteEntry e = (RemoteEntry) entry;
                path = RemoteEntry.DownloadFile(e.getRepoId(), e.getEntryID());
                oldname = path.substring(path.lastIndexOf("\\"));
            } else {
                LocalEntry e = (LocalEntry) entry;
                path = e.getPath();
                oldname = path.substring(path.lastIndexOf("\\"));
            }
            if(new File(path).isDirectory()){
                continue;
            }
            
            String newName = oldname.substring(0,oldname.lastIndexOf('.')) + suffix + oldname.substring(oldname.lastIndexOf('.'));
            File f = new File(path);
            File f2 = new File(f.getParent(),newName);
            f.renameTo(f2);
            path = path.substring(0, path.lastIndexOf("\\")) + newName;
            Entry updatedEntry = new LocalEntry(path);
            System.out.println("renamed to... " + path);
            updatedEntries.add(updatedEntry);
        }
        
            
        return updatedEntries;
    }
//    public static void main(String[] args) {
//        process(LocalEntry.GenerateListOfEntry("C:\\Users\\98910\\Documents\\NetBeansProjects\\Application\\dir2"), "sffff");
//    }
}