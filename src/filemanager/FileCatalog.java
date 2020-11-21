package filemanager;

import javax.print.DocFlavor;

/**
 * @author WenZhikun
 * @data 2020-11-21 10:31
 */
public class FileCatalog {
    //文件目录结构,占8B

    //目录名，3字节
    private String catalogName = null;

    //扩展名，1字节，目录扩展名为0，exe文件扩展名为1 ， txt文件扩展名为2
    private int extensionName;

    //目录属性/文件，1字节
    //高四位空闲，第五位为目录属性位，第六位为普通文件位，第七位为系统文件，第八位为只读文件
    //可以弄一个枚举
    private int[] fileAttribute = null;

    //起始盘块号(所属盘块号)，1字节
    private int startIndex;

    //文件长度，占2字节,目录的长度为0
    private int fileLength;

    //读文件指针所在块号，初始化为其实盘块号
    private int readPointBlock;
    //读文件指针所在块内地址，初始化为0
    private int readPointIndex;

    //写文件指针所在块号，初始为其实盘块号
    private int writePointBlock;
    //写文件指针所在块内地址，初始化为0
    private int writePointIndex;

    /**
     * set/get
     */
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public int getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(int extensionName) {
        this.extensionName = extensionName;
    }

    public int[] getFileAttribute() {
        return fileAttribute;
    }

    public void setFileAttribute(int[] fileAttribute) {
        this.fileAttribute = fileAttribute;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }

    public int getReadPointBlock() {
        return readPointBlock;
    }

    public void setReadPointBlock(int readPointBlock) {
        this.readPointBlock = readPointBlock;
    }

    public int getReadPointIndex() {
        return readPointIndex;
    }

    public void setReadPointIndex(int readPointIndex) {
        this.readPointIndex = readPointIndex;
    }

    public int getWritePointBlock() {
        return writePointBlock;
    }

    public void setWritePointBlock(int writePointBlock) {
        this.writePointBlock = writePointBlock;
    }

    public int getWritePointIndex() {
        return writePointIndex;
    }

    public void setWritePointIndex(int writePointIndex) {
        this.writePointIndex = writePointIndex;
    }
}
