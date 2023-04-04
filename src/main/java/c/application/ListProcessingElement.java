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
class ListProcessingElement {

    static public List<Entry> process(List<Entry> entries, int max) {
        List<Entry> result = new ArrayList<>();
        String path = "";
        for(Entry e : entries) {
            if(e instanceof RemoteEntry) {
                RemoteEntry e1 = (RemoteEntry) e;
                path = RemoteEntry.DownloadFile(e1.getRepoId(), e1.getEntryID());
            } else {
                LocalEntry e1 = (LocalEntry) e;
                path = e1.getPath();
            }
            File f = new File(path);
            if(f.isDirectory()) {
               File[] files = f.listFiles();
               for(int i=0; i<files.length && i<= max; i++){
                   if(files[i].isFile())
                       result.add(new LocalEntry(files[i].getAbsolutePath()));
               }
            }
        }
        return result;
    }
}