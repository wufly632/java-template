package {{package_name}}.commons.webapi.error;


import {{package_name}}.commons.utils.MessagesFormatUtils;
import {{package_name}}.commons.webapi.enums.error.HttpRespEnum;

public class SuperRuntimeException extends RuntimeException {
    /** error code */
    private final long code;

    /**  error message */
    private String detailMessage;

    public SuperRuntimeException(long code, String message) {
        super(message);
        this.code = code;
        this.detailMessage = message;
    }

    public SuperRuntimeException(HttpRespEnum baseEnum, Object... messages) {
        this(baseEnum.code(), MessagesFormatUtils.format(baseEnum.message(), messages));
    }

    public SuperRuntimeException(HttpRespEnum baseEnum) {
        this(baseEnum.code(), baseEnum.message());
    }




    public long getCode() {
        return code;
    }



    public String getDetailMessage() {
        return detailMessage;
    }
}
