
package com.example.vjezba2;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class License implements Serializable
{

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("spdx_id")
    @Expose
    private String spdxId;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("node_id")
    @Expose
    private String nodeId;
    private final static long serialVersionUID = -5584439352058440996L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public License() {
    }

    /**
     * 
     * @param name
     * @param spdxId
     * @param nodeId
     * @param key
     * @param url
     */
    public License(String key, String name, String spdxId, Object url, String nodeId) {
        super();
        this.key = key;
        this.name = name;
        this.spdxId = spdxId;
        this.url = url;
        this.nodeId = nodeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpdxId() {
        return spdxId;
    }

    public void setSpdxId(String spdxId) {
        this.spdxId = spdxId;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

}
