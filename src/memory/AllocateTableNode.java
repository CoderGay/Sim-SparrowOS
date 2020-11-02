package memory;

/**
 * @author WenZhikun
 * @data 2020-11-02 20:29
 */
public class AllocateTableNode {
    //开始地址
    private int startIndex;
    //块大小
    private int nodeLength;
    //结束地址
    private int endIndex;
    //内存块是否被占用
    private boolean allocated;

    public AllocateTableNode(int startIndex, int nodeLength) {
        this.startIndex = startIndex;
        this.nodeLength = nodeLength;
        this.endIndex = startIndex+nodeLength-1;
        this.allocated = false;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getNodeLength() {
        return nodeLength;
    }

    public void setNodeLength(int nodeLength) {
        this.nodeLength = nodeLength;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }
}
