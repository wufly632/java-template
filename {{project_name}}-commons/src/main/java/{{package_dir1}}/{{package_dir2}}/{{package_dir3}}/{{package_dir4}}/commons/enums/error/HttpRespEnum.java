package {{package_name}}.commons.webapi.enums.error;

import lombok.Getter;


@Getter
public enum HttpRespEnum {
    SUCCESS(200, true, "SUCCESS"),
    FAILED(300, false, "FAILED"),
    
    ;
    private long code;
    private boolean success;
    private String message;

    HttpRespEnum(long code, boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String message() {
        return this.message;
    }

    public long code() {
        return this.code;
    }

    public static HttpRespEnum getErrorCodeEnumByCode(Long code) {
        HttpRespEnum[] var1 = values();
        int var2 = var1.length;
        if (code == null) {
            return null;
        }

        for (int var3 = 0; var3 < var2; ++var3) {
            HttpRespEnum param = var1[var3];
            if (param.code() == code) {
                return param;
            }
        }

        return null;
    }
}
