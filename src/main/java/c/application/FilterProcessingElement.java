/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import com.laserfiche.repository.api.clients.impl.model.Entry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 98910
 */
  class FilterProcessingElement {
      //Operator enum used for lengthFilter
       public enum Operator{
            EQ, NEQ, GT, GTE, LT, LTE
        }
    public List<Entry> nameFilter(List<Entry> entries, String key) {
        List<Entry> filteredNames = new ArrayList<>();
        
        if (entries instanceof RemoteEntry){
        for (Entry entry: entries){
            if(entry.getName().contains(key)){
            filteredNames.add(entry);
            }
         }
       }
        else if (entries instanceof LocalEntry){
        for (Entry entry: entries){
            if(entry.getName().contains(key)){
            filteredNames.add(entry);
            }
        }
      }
        return filteredNames;
    }
    
    public static List<Entry> lengthFilter(List<Entry> entries, long length, Operator operator) {
        List<Entry> filteredLen = new ArrayList<>();
       
        for (Entry entry : entries){
            if(entry.getEntryType().toString().equals("Document")){
             Long fileLen = entry.getElecDocumentSize();  
             
             switch (operator) {
                 case EQ:
                     if(fileLen == length){
                         filteredLen.add(entry);
                     }
                     break;
                 case NEQ: 
                     if (fileLen != length){
                         filteredLen.add(entry);
                     }
                     break;
                 case GT: 
                     if(fileLen > length) {
                         filteredLen.add(entry);
                     }
                     break;
                 case GTE: 
                     if(fileLen >= length){
                         filteredLen.add(entry);
                     }
                     break;
                 case LTE: 
                     if(fileLen <= length){
                         filteredLen.add(entry);
                     }
                     break;
                 case LT: 
                     if(fileLen < length){
                         filteredLen.add(entry);
                     }
                     break;
                 default: 
                     break;
              }
           }
        }
        return null;
    }
    
    public static List<Entry> contentFilter(List<Entry> entries, String key) {
        List<Entry> filteredContent = new ArrayList<>();
        for (Entry entry: entries){
            if(entry.getEntryType().toString().equals("Document")){
                if(containsKey(entry, key)){
                   filteredContent.add(entry); 
                }
            }
        }
        return filteredContent;
    }
            public static boolean containsKey(Entry entries, String key){
                try(BufferedReader reader = new BufferedReader( new FileReader(entries.toString()))){
                    String line;
                    while((line = reader.readLine()) != null){
                        if (line.contains(key)){
                            return true;
                        }
                    }
                    return false;
                } catch(IOException e){
                    return false;
                }
            }
    
    
    public static List<Entry> countFilter(List<Entry> entries, String key, int min) {
        //implement
        return null;
    }
}
