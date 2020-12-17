package filemanager.fileserver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/12/17 18:26
 *
 * @version 1.0
 */
class CreateFileTest {

    @Test
    void operation() {
        CreateFile createFile  = new CreateFile();
        String str  = "create /aa/bb/c.exe";
        createFile.operation(str);
    }
}