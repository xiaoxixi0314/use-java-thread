package task;

import lombok.Getter;

@Getter
public enum ErrorEnum {

    SUCCESS("成功"),
    FAIL("失败"),
    ;

    private String errCode;
    private String errMsg;

    ErrorEnum(String errMsg) {
        this.errCode = this.name();
        this.errMsg = errMsg;
    }

}
