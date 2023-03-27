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
    private String suffix;

    public RenameProcessingElement(String suffix) {
        this.suffix = suffix;
    }

    public List<Entry> process(List<Entry> input) {
        // Implement rename logic here
        return null;
    }
}