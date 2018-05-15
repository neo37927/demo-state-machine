package com.example.demo.constant;

public enum ResultConstant {
    SUCCESS("0", Boolean.TRUE.booleanValue(), "success");

    private String code;
    private boolean isSuccess;
    private String message;

    private ResultConstant(String code, String message) {
        this.code = code;
        this.isSuccess = Boolean.FALSE.booleanValue();
        this.message = message;
    }

    private ResultConstant(String code, boolean isSuccess, String message) {
        this.code = code;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    public static ResultConstant getHonorDataCode(String message) {
        ResultConstant[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResultConstant code = var1[var3];
            if (code.getMessage().equals(message)) {
                return code;
            }
        }

        return null;
    }

    public static ResultConstant getResultByCode(String code) {
        ResultConstant[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResultConstant result = var1[var3];
            if (result.getCode().equals(code)) {
                return result;
            }
        }

        return null;
    }

    public static ResultConstant getResultStartWithMsg(String message) {
        ResultConstant[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResultConstant result = var1[var3];
            if (message.startsWith(result.getMessage())) {
                return result;
            }
        }

        return null;
    }
}

