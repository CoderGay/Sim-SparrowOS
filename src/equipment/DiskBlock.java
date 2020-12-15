package equipment;

/**
 * Create By  @林俊杰
 * 2020/12/15 19:58
 *
 * @version 1.0
 */
public class DiskBlock<T> {
    private T data;
    private boolean isAvailable;
    private int occupiedSize;

    public DiskBlock() {
        this.isAvailable = true;
    }

    public DiskBlock(T data) {
        this.data = data;
        this.isAvailable = true;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getOccupiedSize() {
        return occupiedSize;
    }

    public void setOccupiedSize(int occupiedSize) {
        this.occupiedSize = occupiedSize;
    }
}
