package filemanager;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
        Assert.assertNotEquals(disk,null);
    }
}