package enums;

/**
 * Create By  @林俊杰
 * 2020/10/26 21:19
 *
 * @version 1.0
 */
public enum SizeEnum {

    PCB_SIZE(16,"PCB占用空间16B"),

    RESIDENT_MONITOR(32,"常驻监控程序占用空间32B"),

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
