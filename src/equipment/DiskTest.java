package equipment;

import equipment.Disk;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * Create By  @林俊杰
 * 2020/12/13 16:46
 *
 * @version 1.0
 */
class DiskTest {

    @Test
    void getDiskDocumentContent() throws IOException {
        Disk disk = Disk.getDisk();
        System.out.println(disk.getRoot().getCatalogName());
        System.out.println(disk.getAvailableBlocks());
        for (int a:disk.getFileAllocateTable()
             ) {
            System.out.print(a+" ");
        }
        //FileTool.writeObjectStreamFile(disk,"myTest.disk");
        Assert.assertNotEquals(disk,null);

    }
}