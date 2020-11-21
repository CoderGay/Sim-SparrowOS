package enums;

/**
 * Create By  @林俊杰
 * 2020/10/26 21:19
 *
 * Modify By @WenZhikun
 * 2020/11/21 10:00
 *
 * @version 1.1
 */
public enum SizeEnum {

    PCB_SIZE(16,"PCB占用空间16B"),

    RESIDENT_MONITOR_SIZE(32,"常驻监控程序占用空间32B"),

    SYS_MEMORY_SIZE(256,"内存系统区空间共有256B"),

    USER_MEMORY_SIZE(512,"内存用户区空间共有512B"),

    ALLOCATE_TABLE_MEMORY_SIZE(64,"内存分配表占用空间64B"),

    DISK_SIZE(256,"磁盘大小"),

    BLOCKS_SIZE(64,"盘块大小"),

    AVALIABLE_BLOCKS(0,"可用盘块标志"),

    END_BLOCKS_LABEL(-1,"文件结束标志"),


    ;


    private Integer code;

    private String message;

    SizeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
