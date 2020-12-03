package enums;

/**
 * @author WenZhikun
 * @data 2020-11-02 21:37
 */
public enum ModeEnum {
    ALLOCATE_SYS_BLOCK(0,"系统区中申请内存空间"),

    ALLOCATE_USER_BLOCK(1,"用户区中申请内存空间"),

    ONLY_READ(101,"以只读方式打开"),

    READ(110,"以读的方式打开"),//个人感觉：这个操作模式有点多余，跟只读没什么区别 @林俊杰

    WRITE(220,"以写的方式打开"),
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
