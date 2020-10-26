package process;

import enums.SizeEnum;

/**
 * Create By  @林俊杰
 * 2020/10/26 21:32
 *
 * @version 1.0
 */
public class ResidentMonitor {

    private int size ;


    //TODO 调度算法需要实现;


    /**
    *
    * constructor
    * */
    public ResidentMonitor() {
        this.size = SizeEnum.PCB_SIZE.getCode();
    }

    /**
     * getter&setter
     * */
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
