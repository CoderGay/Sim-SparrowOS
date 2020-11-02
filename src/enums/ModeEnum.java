package enums;

/**
 * @author WenZhikun
 * @data 2020-11-02 21:37
 */
public enum ModeEnum {
    ALLOCATE_SYS_BLOCK(0,"系统区中申请内存空间"),

    ALLOCATE_USER_BLOCK(1,"用户区中申请内存空间"),
    ;
    private Integer code;

    private String message;

    ModeEnum(Integer code, String message) {
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
