package com.example.demo.vo;

import com.example.demo.constant.HonorType;
import com.example.demo.constant.IDCertType;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HonorDataHttpParam implements Serializable {
    private static final long serialVersionUID = 7840871006909295799L;
    private String systemKey;
    private HonorType honorType;
    private Map<String, Object> params;
    private String idCertNo;
    private IDCertType certType;
    private boolean isRequestNew;
    private String uuid;

    public String getSystemKey() {
        return this.systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public HonorType getHonorType() {
        return this.honorType;
    }

    public void setHonorType(HonorType honorType) {
        this.honorType = honorType;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getIdCertNo() {
        return this.idCertNo;
    }

    public void setIdCertNo(String idCertNo) {
        this.idCertNo = idCertNo;
    }

    public IDCertType getCertType() {
        return this.certType;
    }

    public void setCertType(IDCertType certType) {
        this.certType = certType;
    }

    public boolean getIsRequestNew() {
        return this.isRequestNew;
    }

    public void setIsRequestNew(boolean isRequestNew) {
        this.isRequestNew = isRequestNew;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HonorDataHttpParam() {
    }

    public HonorDataHttpParam(String systemKey, HonorType honorType, Map<String, Object> params, String idCertNo, IDCertType certType, Boolean isRequestNew, String uuid) {
        this.systemKey = systemKey;
        this.honorType = honorType;
        this.params = params;
        this.idCertNo = idCertNo;
        this.certType = certType;
        this.isRequestNew = isRequestNew.booleanValue();
        this.uuid = uuid;
    }

    public String toString() {
        return "{\"systemKey\":\"" + this.systemKey + "\"" + ",\"honorType\":\"" + this.honorType.name() + "\"" + ",\"params\":" + parseMapToJson(this.params) + ",\"idCertNo\":\"" + this.idCertNo + "\"" + ",\"uuid\":\"" + this.uuid + "\"" + ",\"certType\":\"" + this.certType.name() + "\"" + ",\"isRequestNew\":" + this.isRequestNew + "}";
    }

    private static String parseMapToJson(Map<String, Object> map) {
        if (map != null && map.size() != 0) {
            StringBuilder builder = new StringBuilder("{");
            Set<String> keys = map.keySet();
            Iterator var3 = keys.iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                builder.append("\"").append(key).append("\":\"").append(map.get(key)).append("\",");
            }

            if (builder.length() > 1) {
                builder.deleteCharAt(builder.length() - 1);
            }

            builder.append("}");
            return builder.toString();
        } else {
            return "{}";
        }
    }
}
