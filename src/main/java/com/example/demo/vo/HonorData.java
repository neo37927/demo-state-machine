package com.example.demo.vo;

import com.example.demo.constant.ResultConstant;

import java.io.Serializable;
import java.util.Date;

public class HonorData implements Serializable {
    private static final long serialVersionUID = -2406500477755243505L;
    private Date createTime;
    private boolean isRequestNew;
    private String next;
    private String channel;
    private boolean isSuccess;
    private Object data;
    private String code;
    private String errMsg;
    private String uuid;
    private String honorUrl;
    private String filePath;
    public static String HAS_COSTED_KEY = "hasCosted";
    private boolean hasCosted;

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isRequestNew() {
        return this.isRequestNew;
    }

    public void setRequestNew(boolean requestNew) {
        this.isRequestNew = requestNew;
    }

    public String getNext() {
        return this.next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHonorUrl() {
        return this.honorUrl;
    }

    public void setHonorUrl(String honorUrl) {
        this.honorUrl = honorUrl;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isHasCosted() {
        return this.hasCosted;
    }

    public void setHasCosted(boolean hasCosted) {
        this.hasCosted = hasCosted;
    }

    public HonorData() {
    }

    public HonorData(String code, String errMsg, String channel, boolean isRequestNew) {
        this.code = code;
        this.errMsg = errMsg;
        this.channel = channel;
        this.createTime = new Date();
        this.isSuccess = Boolean.FALSE.booleanValue();
        this.isRequestNew = isRequestNew;
        this.data = "";
        this.uuid = "";
        this.honorUrl = "";
        this.filePath = "";
    }

    public HonorData(String channel, Object data, Boolean isSuccess) {
        this.code = ResultConstant.SUCCESS.getCode();
        this.errMsg = "";
        this.channel = channel;
        this.createTime = new Date();
        this.isSuccess = isSuccess.booleanValue();
        this.isRequestNew = Boolean.TRUE.booleanValue();
        this.data = data;
        this.uuid = "";
        this.honorUrl = "";
        this.filePath = "";
    }

    public HonorData(Date createTime, boolean isRequestNew, String channel, boolean isSuccess, Object data, String code, String errMsg, String uuid, String honorUrl, String filePath, boolean hasCosted) {
        this.createTime = createTime;
        this.isRequestNew = isRequestNew;
        this.channel = channel;
        this.isSuccess = isSuccess;
        this.data = data;
        this.code = code;
        this.errMsg = errMsg;
        this.uuid = uuid;
        this.honorUrl = honorUrl;
        this.filePath = filePath;
        this.hasCosted = hasCosted;
    }
}
