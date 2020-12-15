package enums;

/**
 * Create By  @林俊杰
 * 2020/10/26 20:49
 *
 * @version 1.0
 */
public enum BlockingReasonEnum {
    //待补充
    NON_BLOCK(0,"没有被阻塞"),
    ;


    private Integer code;

    private String message;

    BlockingReasonEnum(Integer code, String message) {
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
