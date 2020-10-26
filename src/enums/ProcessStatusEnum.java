package enums;

/**
 * Create By  @林俊杰
 * 2020/10/26 20:43
 *
 * @version 1.0
 */
public enum ProcessStatusEnum {
    NEW(0,"新建态"),
    READY(1,"就绪态"),
    RUN(2,"运行态"),
    EXIT(3,"退出态"),
    BLOCK(4,"阻塞态")

    ;



    private Integer code;

    private String message;

    ProcessStatusEnum(Integer code, String message) {
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
