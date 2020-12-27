package filemanager.file;

import filemanager.FileCatalog;

import javax.print.Doc;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/12/18 17:53
 *
 * @version 1.0
 */
public class SparrowDirectory extends Document implements Serializable {
    private List<Document> data = new ArrayList<>();

    public List<Document> getData() {
        return data;
    }

    public void setData(List<Document> data) {
        this.data = data;
    }

    public void addDocument(Document sparrowFile){
        this.data.add(sparrowFile);
    }
}
