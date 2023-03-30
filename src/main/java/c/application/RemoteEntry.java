/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;

/**
 *
 * @author 98910
 */
class RemoteEntry implements Entry {
    private String repoId;
    private int entryId;
    private String name;
    private long length;
    private boolean isDirectory;

    public RemoteEntry(String repoId, int entryId, String name, long length, boolean isDirectory) {
        this.repoId = repoId;
        this.entryId = entryId;
        this.name = name;
        this.length = length;
        this.isDirectory = isDirectory;
    }
    
    public int getEntryID(){
        return entryId;
    }
    public String getName() {
        return name;
    }

    public long getLength() {
        return length;
    }

    public boolean isDirectory() {
        return isDirectory;
    }
}