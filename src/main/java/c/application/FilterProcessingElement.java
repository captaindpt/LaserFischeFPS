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
  class FilterProcessingElement implements ProcessingElement {
    static public List<Entry> nameFilter(List<Entry> entries, String key) {
        //implement
    }
    
    static public List<Entry> lengthFilter(List<Entry> entries, long length, String operator) {
        //implement
    }
    
    static public List<Entry> contentFilter(List<Entry> entries, String key) {
        //implement
    }
    
    static public List<Entry> countFilter(List<Entry> entries, String key, int min) {
        //implement
    }
}
