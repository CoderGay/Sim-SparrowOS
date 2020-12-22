package filemanager.file;

import filemanager.FileCatalog;

import java.io.Serializable;

/**
 * Create By  @林俊杰
 * 2020/12/17 18:54
 *
 * @version 1.0
 */
public class SparrowFile extends Document implements Serializable {

    String data;//流式文件,所有文件数据都是字符流

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
