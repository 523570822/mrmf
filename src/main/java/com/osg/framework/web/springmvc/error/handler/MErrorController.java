package com.osg.framework.web.springmvc.error.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.osg.entity.error.MError;
import com.osg.entity.error.MErrorCode;
import com.osg.framework.util.UUIDUtil;
import com.osg.framework.web.exception.MAuthorizedException;
import com.osg.framework.web.exception.MBusinessException;
import com.osg.framework.web.exception.MSystemException;
import com.osg.framework.web.i18n.MessageUtil;

/**
 * 异常处理
 */
@ControllerAdvice
public class MErrorController extends ResponseEntityExceptionHandler {

	private static Logger logger = Logger.getLogger(MErrorController.class);

	/**
	 * 处理授权类异常
	 * 
	 * @param req
	 * @param resp
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MAuthorizedException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public MError handleAuthorizedException(HttpServletRequest req,
			HttpServletResponse resp, MAuthorizedException ex) {
		MError e = new MError();
		e.setErrorCode(ex.getErrorCode());
		e.setErrorMessage(ex.getMessage());
		return e;
	}

	/**
	 * 处理请求参数错误导致的业务异常
	 * 
	 * @param req
	 * @param resp
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MBusinessException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MError handleIllegalParamException(HttpServletRequest req,
			HttpServletResponse resp, MBusinessException ex) {
		return assembleBusinessError(ex);
	}

	/**
	 * 处理系统异常(严重)
	 * 
	 * @param req
	 * @param resp
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MSystemException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public MError handleSystemException(HttpServletRequest req,
			HttpServletResponse resp, MSystemException ex) {
		return assembleSystemError(ex);
	}

	/**
	 * 处理Spring mvc标准异常-不支持的http请求方法
	 */
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e2002.code());
		e.setErrorMessage(MessageUtil.getMessageWithArg(
				"error.business.illegalParam.invalidMethod.spring",
				new Object[] { ex.getMethod(),
						Arrays.toString(ex.getSupportedMethods()) }));
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * 处理Spring mvc标准异常，缺少必要参数
	 */
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e2005.code());
		e.setErrorMessage(MessageUtil.getMessageWithArg(
				"error.business.illegalParam.missed.spring",
				new Object[] { ex.getParameterType(), ex.getParameterName() }));
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * 处理Spring mvc标准异常 - 请求参数类型错误
	 */
	protected ResponseEntity<Object> handleTypeMismatch(
			TypeMismatchException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e2001.code());
		e.setErrorMessage(MessageUtil.getMessageWithArg(
				"error.business.illegalParam.type.spring",
				new Object[] { ex.getValue(), ex.getRequiredType() }));
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));
			e.setErrorStack(sw.toString());
			logger.debug("TypeMismatchException", ex);
		}
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/*------ 以下异常在当前业务框架下暂不会出现 -----*/
	/**
	 * 处理Spring mvc标准异常
	 */
	protected ResponseEntity<Object> handleNoSuchRequestHandlingMethod(
			NoSuchRequestHandlingMethodException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e2003.code());
		e.setErrorMessage("Request api not found");
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.NOT_FOUND, request);
	}

	/**
	 * 处理Spring mvc标准异常-不支持的MediaType
	 */
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e2004.code());
		e.setErrorMessage("Media type is not supported");
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * 处理Spring mvc标准异常-不支持的MediaType
	 */
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
			HttpMediaTypeNotAcceptableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e2004.code());
		e.setErrorMessage("Media type is not supported");
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * 处理Spring mvc标准异常
	 */
	protected ResponseEntity<Object> handleBindException(BindException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e2005.code());
		e.setErrorMessage(ex.getMessage());
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));
			e.setErrorStack(sw.toString());
			logger.debug("BindException", ex);
		}

		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * 处理Spring mvc标准异常
	 */
	protected ResponseEntity<Object> handleConversionNotSupported(
			ConversionNotSupportedException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e2001.code());
		e.setErrorMessage(ex.getMessage());
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));
			e.setErrorStack(sw.toString());
			logger.debug("ConversionNotSupportedException", ex);
		}
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * 处理Spring mvc标准异常
	 */
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e2005.code());
		e.setErrorMessage(ex.getMessage());
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));
			e.setErrorStack(sw.toString());
			logger.debug("HttpMessageNotReadableException", ex);
		}
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * 处理Spring mvc标准异常
	 */
	protected ResponseEntity<Object> handleHttpMessageNotWritable(
			HttpMessageNotWritableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e3004.code());
		e.setErrorMessage(ex.getMessage());
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));
			e.setErrorStack(sw.toString());
			logger.debug("HttpMessageNotWritableException", ex);
		}
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	/**
	 * 处理Spring mvc标准异常
	 */
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e2001.code());
		e.setErrorMessage(ex.getMessage());
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));
			e.setErrorStack(sw.toString());
			logger.debug("MethodArgumentNotValidException", ex);
		}
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * 处理Spring mvc标准异常
	 */
	protected ResponseEntity<Object> handleMissingServletRequestPart(
			MissingServletRequestPartException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		MError e = new MError();
		e.setErrorCode(MErrorCode.e2005.code());
		e.setErrorMessage(ex.getMessage());
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}

	/**
	 * 处理Spring mvc标准异常
	 */
	protected ResponseEntity<Object> handleServletRequestBindingException(
			ServletRequestBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		MError e = new MError();
		e.setErrorCode(MErrorCode.e3004.code());
		e.setErrorMessage(ex.getMessage());
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));
			e.setErrorStack(sw.toString());
			logger.debug("ServletRequestBindingException", ex);
		}
		return handleExceptionInternal(ex, e, new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	/**
	 * 处理其他异常
	 * 
	 * @param req
	 * @param resp
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public MError handleOtherException(HttpServletRequest req,
			HttpServletResponse resp, Exception ex) {
		String uuid = UUIDUtil.getUUID();
		MError e = new MError();
		e.setErrorCode(MErrorCode.e3004.code());
		e.setErrorMessage(ex.getMessage());
		e.setExceptionId(uuid);
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));
			e.setErrorStack(sw.toString());
		}
		String se = String.format("[%s]-[%s]:", uuid, e.getErrorCode());
		logger.fatal(se, ex);
		return e;
	}

	private MError assembleBusinessError(MBusinessException ex) {
		MError e = new MError();
		e.setErrorCode(ex.getErrorCode());
		e.setErrorMessage(ex.getMessage());
		e.setExceptionId(ex.getExceptionId());
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));
			e.setErrorStack(sw.toString());
		}
		String se = String.format("[%s]-[%s]:", e.getExceptionId(),
				e.getErrorCode());
		logger.error(se, ex);
		return e;
	}

	private MError assembleSystemError(MSystemException ex) {
		MError e = new MError();
		e.setErrorCode((ex.getErrorCode() == null)?MErrorCode.e3004.code():ex.getErrorCode());
		e.setErrorMessage(ex.getMessage());
		e.setExceptionId(ex.getExceptionId());
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw, true));
			e.setErrorStack(sw.toString());
		}
		String se = String.format("[%s]-[%s]:", e.getExceptionId(),
				e.getErrorCode());
		logger.fatal(se, ex);
		return e;
	}
}
