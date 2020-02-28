package com.unimelb.saul.studySpringBootSwagger.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 功能：响应结果类
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "返回数据对象",description = "前后端交互协议数据类型")
public class JsonData implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 状态码
	 */
	@ApiModelProperty(value = "状态码")
	private Integer code;
	/**
	 * 数据
	 */
	@ApiModelProperty(value = "数据",name = "datadata",notes = "后端实体数据存储位置")
	private Object data;
	/**
	 * 描述
	 */
	@ApiModelProperty(name = "message")
	private String msg;

    /**
     *  成功
     *
     * @return 状态码，消息
     */
	public static JsonData buildSuccess() {
		return new JsonData(200, null, "ok");
	}

    /**
     * 成功，传入数据
     *
     * @return 状态码，消息，数据
     */
	public static JsonData buildSuccess(Object data) {
		return new JsonData(200, data, "ok");
	}

	/**
	 * 成功，传入数据,消息
	 *
	 * @return 状态码，消息，数据
	 */
	public static JsonData buildSuccess(Object data, String msg) {
		return new JsonData(200, data, "成功" + ", " + msg);
	}
    /**
     * 失败，传入描述信息,状态码
     *
     * @param msg 消息
     * @param code 状态码
     * @return 状态码，消息
     */
	public static JsonData buildError(Integer code, String msg) {
		return new JsonData(code, null, msg);
	}


	@Override
	public String toString() {
		return "JsonData [code=" + code + ", data=" + data + ", msg=" + msg
				+ "]";
	}

}
