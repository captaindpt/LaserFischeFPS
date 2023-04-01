/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;
import java.io.File;
import java.io.FileOutputStream;
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
        //implement
        return null;
    }
    
    public static List<Entry> countFilter(List<Entry> entries, String key, int min) {
        //implement
        return null;
    }
}
