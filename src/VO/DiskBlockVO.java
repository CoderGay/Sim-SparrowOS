package VO;

import equipment.DiskBlock;

/**
 * Create By  @林俊杰
 * 2020/12/26 20:21
 *
 * @version 1.0
 */
public class DiskBlockVO {
    private int num;
    private String isAvailable;
    private String occupiedSize;
    private int nextIndex;

    public DiskBlockVO(int num, boolean isAvailable, int occupiedSize,int nextIndex) {
        this.num = num;
        setIsAvailable(isAvailable);
        setOccupiedSize(occupiedSize);
        this.nextIndex = nextIndex;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {

        if (isAvailable){
            this.isAvailable = "可用";
        }else {
            this.isAvailable = "不可用";
        }

    }

    public String getOccupiedSize() {
        return occupiedSize;
    }

    public void setOccupiedSize(int occupiedSize) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf(occupiedSize)+"B");
        this.occupiedSize =stringBuilder.toString();
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }
}
