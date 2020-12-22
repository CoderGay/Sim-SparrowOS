package filemanager.file;

import filemanager.FileCatalog;

import java.io.Serializable;

/**
 * Create By  @林俊杰
 * 2020/12/17 15:49
 *
 * @version 1.0
 */
public abstract class Document implements Serializable {
    private int size;

    FileCatalog fileCatalog;


    /**
     *getter & setter
     * */

    public FileCatalog getFileCatalog() {
        return fileCatalog;
    }

    public void setFileCatalog(FileCatalog fileCatalog) {
        this.fileCatalog = fileCatalog;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
