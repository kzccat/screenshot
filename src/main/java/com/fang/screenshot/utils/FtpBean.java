package com.fang.screenshot.utils;

import lombok.Data;

import java.io.InputStream;


public class FtpBean {
    /**
     * 部分信息如果传空的话，默认赋值，根据业务需求修改
     */
    //获取ip地址
    private String address;
    //端口号
    private String port;
    //用户名
    private String username;
    //密码
    private String password;
    //文件名称
    private String fileName;
    //基本路
    private String basepath;
    //文件输入流
    private InputStream inputStream;
    //保存文件方式  默认：1-覆盖；2-文件名称后面+(递增数据)
    private Integer saveFileType;

    public String getAddress() {
        return address == null ? "10.32.128.153":address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPort() {
        return port == null ? "33321":port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getUsername() {
        return username == null ? "js_test":username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password == null ?"fie93k32":password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFileName() {
        return fileName == null ?"未命名":fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getBasepath() {
        return basepath == null ?"/common_m/pc_public/proaiexplain/render3d":basepath;
    }
    public void setBasepath(String basepath) {
        this.basepath = basepath;
    }
    public InputStream getInputStream() {
        return inputStream;
    }
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    public Integer getSaveFileType() {
        return saveFileType != 1 ? 2:saveFileType;
    }
    public void setSaveFileType(Integer saveFileType) {
        this.saveFileType = saveFileType;
    }
}
