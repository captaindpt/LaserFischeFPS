/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package c.application;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.repository.api.RepositoryApiClient;
import com.laserfiche.repository.api.RepositoryApiClientImpl;
import com.laserfiche.repository.api.clients.impl.model.ODataValueContextOfIListOfEntry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author 98910
 */
public class Application {

    public static void main(String[] args) {
       String servicePrincipalKey = "9_YVh_11HPvRIrThlsE7";
        String accessKeyBase64 = "ewoJImN1c3RvbWVySWQiOiAiMTQwMTM1OTIzOCIsCgkiY2xpZW50SWQiOiAiYzI3NWE0NTktNTg5My00M2JmLTk4NTktNzVjM2NjN2Q0NGIyIiwKCSJkb21haW4iOiAibGFzZXJmaWNoZS5jYSIsCgkiandrIjogewoJCSJrdHkiOiAiRUMiLAoJCSJjcnYiOiAiUC0yNTYiLAoJCSJ1c2UiOiAic2lnIiwKCQkia2lkIjogIjdfcW0wVE1wRl9PeGl3TF90V2Z4ZUZiYVZmRTg5d3RsVEtHNUpQb1FSU0kiLAoJCSJ4IjogIkNnVUpKN2Zzcmx0MEM0R3JGWHFIbDRhVm9NeU9vdG5Ud1JtOXBXeDExSlkiLAoJCSJ5IjogInBESlZfNzZWZ1AyU0d5Y2RmRXFKX3J5alpTZ1Z5THljZkdFaDcyV2ZmVUUiLAoJCSJkIjogIkF5UXM5eGZvLTBIS0J2bElnUTltZ09sOWo3cXBXMHN4UC1xU3kxV2V0Y1UiLAoJCSJpYXQiOiAxNjc3Mjk3NDUwCgl9Cn0=";
		String repositoryId = "r-0001d410ba56";
        AccessKey accessKey = AccessKey.createFromBase64EncodedAccessKey(accessKeyBase64);

        RepositoryApiClient client = RepositoryApiClientImpl.createFromAccessKey(
                servicePrincipalKey, accessKey);

        // Get information about the ROOT entry, i.e. entry with ID=1
        int rootEntryId = 4;
        com.laserfiche.repository.api.clients.impl.model.Entry entry = client.getEntriesClient()
                .getEntry(repositoryId, rootEntryId, null).join();

        System.out.println(
                String.format("Entry ID: %d, Name: %s, EntryType: %s, FullPath: %s",
                        entry.getId(), entry.getName(), entry.getEntryType(), entry.getFullPath()));

        // Get information about the child entries of the Root entry
        ODataValueContextOfIListOfEntry result = client
                .getEntriesClient()
                .getEntryListing(repositoryId, rootEntryId, true, null, null, null, null, null, "name", null, null, null).join();
        List<com.laserfiche.repository.api.clients.impl.model.Entry> entries = result.getValue();
        for (com.laserfiche.repository.api.clients.impl.model.Entry childEntry : entries) {
            System.out.println(
                    String.format("Child Entry ID: %d, Name: %s, EntryType: %s, FullPath: %s",
                            childEntry.getId(), childEntry.getName(), childEntry.getEntryType(), childEntry.getFullPath()));
        }

        // Download an entry 
        int entryIdToDownload = 17 ;
        final String FILE_NAME = "DownloadedFile.txt";
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
        
        //nameFilter
        //implement test instance **Not Functional** 
        int EntryID1 = 8;
        int EntryID2 = 9;
        int EntryID3 = 12;
        com.laserfiche.repository.api.clients.impl.model.Entry entry1 =  client.getEntriesClient()
                .getEntry(repositoryId, EntryID1, null).join();
        com.laserfiche.repository.api.clients.impl.model.Entry entry2 =  client.getEntriesClient()
                .getEntry(repositoryId, EntryID2, null).join();
        com.laserfiche.repository.api.clients.impl.model.Entry entry3 =  client.getEntriesClient()
                .getEntry(repositoryId, EntryID3, null).join();
        List<Entry> testEntries = new ArrayList<>();
       // testEntries.add(entry1);
       // testEntries.add(entry2);
       // testEntries.add(entry3);
       // List<Entry> filteredNames = FilterProcessingElement.nameFilter(testEntries, "200k");
       // for (Entry entry_: filteredNames){
       //     System.out.println(entry_.getName());
       // }                
       
        client.getEntriesClient()
                .exportDocument(repositoryId, entryIdToDownload, null, consumer)
                .join();

        client.close();
        
        
        
    }  
}
