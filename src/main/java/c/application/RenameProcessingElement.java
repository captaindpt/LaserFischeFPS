/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;

import java.util.List;

/**
 *
 * @author 98910
 */
class RenameProcessingElement implements ProcessingElement {

    static public List<Entry> process(List<Entry> entries, String suffix) {
        List<Entry> updatedEntries = new ArrayList<>();
        for (Entry entry:entries){
            String oldname = entry.getName();
            String newName = oldname.substring(0,oldname.lastIndexOf(".")) + suffix + oldname.substring(oldname.lastIndexOf("."));
            Entry updatedEntry = new Entry(updatedName, entry.getSize());
            updatedEntries.add(updatedEntry);
        }
        
            
        return updatedEntries;
    }
}
