package {{package_name}}.api.configuration;


import com.coraool.commons.aio.apis.response.BaseResponse;
import com.coraool.commons.aio.enums.error.BizException;
import com.coraool.commons.aio.enums.error.ErrorCodeEnum;
import {{package_name}}.commons.webapi.enums.error.HttpRespEnum;
import {{package_name}}.commons.webapi.error.SuperRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * @return
     */
    @ExceptionHandler(SuperRuntimeException.class)
    public BaseResponse superException(HttpServletRequest req, SuperRuntimeException e) {

        HttpRespEnum errorCodeEnum = HttpRespEnum.getErrorCodeEnumByCode(e.getCode());
        if (Objects.nonNull(errorCodeEnum)) {
            log.error(
                    "Url:{},Method:{},message:{}.Controller called error!Catch in ErrorHandler",
                    req.getRequestURI(),
                    req.getMethod(),
                    e.getDetailMessage(),
                    e);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode(e.getCode());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setSuccess(false);
            return baseResponse;
        }
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(e.getCode());
        baseResponse.setMessage(ErrorCodeEnum.SYS_ERROR.getDesc());
        baseResponse.setSuccess(false);
        return baseResponse;
    }

    @ExceptionHandler
    public BaseResponse superException(HttpServletRequest req, ValidationException e) {
        log.warn(
                "Url:{},Method:{} .Controller called error!Catch in ErrorHandler",
                req.getRequestURI(),
                req.getMethod(),
                e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ErrorCodeEnum.SYS_ERROR.getCode());
        baseResponse.setMessage(ErrorCodeEnum.SYS_ERROR.getDesc());
        baseResponse.setSuccess(false);
        return baseResponse;
    }

    @ExceptionHandler
    public BaseResponse methodArgumentNotValidExceptionHandler(
            HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error(
                "Url:{},Method:{} .Controller called error!Catch in ErrorHandler {}",
                req.getRequestURI(),
                req.getMethod(),
                e.getMessage());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(ErrorCodeEnum.SYS_ERROR.getCode());
        baseResponse.setMessage(ErrorCodeEnum.SYS_ERROR.getDesc());
        baseResponse.setSuccess(false);
        return baseResponse;
    }

    @ExceptionHandler
    public BaseResponse missingServletRequestParameterExceptionExceptionHandler(
            HttpServletRequest req, MissingServletRequestParameterException e) {
        log.warn(
                "Url:{},Method:{} .\nController called error!Catch in ErrorHandler",
                req.getRequestURI(),
                req.getMethod(),
                e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(HttpRespEnum.PARAM_VALID_ERROR.code());
        baseResponse.setMessage(e.getParameterName() + "不能为空");
        baseResponse.setSuccess(false);
        return baseResponse;
    }

    @ExceptionHandler(BizException.class)
    public BaseResponse bizException(HttpServletRequest req, BizException e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode((e.getErrorCode()));
        baseResponse.setSuccess(false);
        log.error(
            "Url:{},Method:{},message:{}.Controller called error!Catch in ErrorHandler",
            req.getRequestURI(),
            req.getMethod(),
            e.getMessage(),
            e);
        baseResponse.setMessage(e.getErrorMsg());
        return baseResponse;
    }


    @ExceptionHandler(Exception.class)
    @Order(1000)
    public BaseResponse exception(HttpServletRequest req, Exception e) {

        log.error(
                "Url:{},Method:{},message:{}.Controller called error!Catch in ErrorHandler",
                req.getRequestURI(),
                req.getMethod(),
                e.getMessage(),
                e);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(-1);
        baseResponse.setMessage(ErrorCodeEnum.SYS_ERROR.getDesc());
        baseResponse.setSuccess(false);
        return baseResponse;
    }

}
