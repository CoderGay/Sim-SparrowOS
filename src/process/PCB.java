package process;

import enums.BlockingReasonEnum;
import enums.ProcessStatusEnum;
import enums.SizeEnum;

import java.util.Random;

/**
 * Create By  @林俊杰
 * 2020/10/26 20:35
 *
 * @version 1.0
 */
public class PCB {
    private  int PID;

    //private int mainRegister;//主要寄存器;

    private int status;

    private int blockReason;

    private int time;	//剩余运行时间，以时间片为单位，当减至 0 时该进程终止

    private int needTime;   //整个进程需要的时间

    private double start;   //开始时间

    private double end;  //结束时间

    private int size;

    /**
     *
     * **/
    public PCB(int needTime) {
        this.needTime = needTime;

        PID = new Random().nextInt(10)+1;

        status = ProcessStatusEnum.NEW.getCode();

        blockReason = BlockingReasonEnum.NON_BLOCK.getCode();

        time = needTime;

        start  = System.currentTimeMillis();

        end = -1;

        size = SizeEnum.PCB_SIZE.getCode();

    }

    /**
    * getter & setter
    *
    * */

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(int blockReason) {
        this.blockReason = blockReason;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNeedTime() {
        return needTime;
    }

    public void setNeedTime(int needTime) {
        this.needTime = needTime;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}
