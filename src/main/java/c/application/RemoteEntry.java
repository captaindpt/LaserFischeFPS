/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package c.application;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.repository.api.RepositoryApiClient;
import com.laserfiche.repository.api.RepositoryApiClientImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

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

    public RemoteEntry(String repoId, int entryId) {
        this.repoId = repoId;
        this.entryId = entryId;
        this.name = name;
        this.length = length;
        this.isDirectory = isDirectory;
    }
    public static String DownloadFile(String repoId, int entryId){
    String servicePrincipalKey = "9_YVh_11HPvRIrThlsE7";
        String accessKeyBase64 = "ewoJImN1c3RvbWVySWQiOiAiMTQwMTM1OTIzOCIsCgkiY2xpZW50SWQiOiAiYzI3NWE0NTktNTg5My00M2JmLTk4NTktNzVjM2NjN2Q0NGIyIiwKCSJkb21haW4iOiAibGFzZXJmaWNoZS5jYSIsCgkiandrIjogewoJCSJrdHkiOiAiRUMiLAoJCSJjcnYiOiAiUC0yNTYiLAoJCSJ1c2UiOiAic2lnIiwKCQkia2lkIjogIjdfcW0wVE1wRl9PeGl3TF90V2Z4ZUZiYVZmRTg5d3RsVEtHNUpQb1FSU0kiLAoJCSJ4IjogIkNnVUpKN2Zzcmx0MEM0R3JGWHFIbDRhVm9NeU9vdG5Ud1JtOXBXeDExSlkiLAoJCSJ5IjogInBESlZfNzZWZ1AyU0d5Y2RmRXFKX3J5alpTZ1Z5THljZkdFaDcyV2ZmVUUiLAoJCSJkIjogIkF5UXM5eGZvLTBIS0J2bElnUTltZ09sOWo3cXBXMHN4UC1xU3kxV2V0Y1UiLAoJCSJpYXQiOiAxNjc3Mjk3NDUwCgl9Cn0=";
        repoId = "r-0001d410ba56";
        AccessKey accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);
    RepositoryApiClient client = RepositoryApiClientImpl.createFromAccessKey(
                servicePrincipalKey, accessKey);  
    // Download a list of entries from Repo
      com.laserfiche.repository.api.clients.impl.model.Entry entry = client.getEntriesClient()
                .getEntry(repoId, entryId, null).join();
        final String FILE_NAME = entry.getName() + ".txt";
        Consumer<InputStream> consumer = inputStream -> {
            File exportedFile = new File(FILE_NAME);
            try (FileOutputStream outputStream = new FileOutputStream(exportedFile)) {
                byte[] buffer = new byte[1024];
                while (true) {
                    int length = inputStream.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    outputStream.write(buffer, 0, length);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        client.getEntriesClient()
                .exportDocument(repoId, entryId, null, consumer)
                .join();
        Path path = Paths.get(FILE_NAME);
           return path.toAbsolutePath().toString();  
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
    
    public String getRepoId() {
        return repoId;
    }

    public boolean isDirectory() {
        return isDirectory;
    }
    
    public String getRepoId(){
        return repoId;
    }
}