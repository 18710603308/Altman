package com.altman.m78.model;

import java.io.Serializable;

/**
 * 获取电子发票接口响应对象
 *
 * @author wuqing
 */
public class StandardInvoiceRespDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8866447812506783086L;
    /**
     * 接口返回代码
     */
    private String            resultCode;

    /**
     * 错误描述
     */
    private String            resultMessage;
    /**
     * 已开具电子发票号码
     */
    private String            invoiceNo;

    /**
     * 已开具电子发票代码
     */
    private String            invoiceCode;

    /**
     * 已开具电子发票金额
     */
    private String            invoiceAmount;

    /**
     * 电子发票信息获取地址
     */
    private String            url;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "StandardInvoiceRespDTO{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", invoiceCode='" + invoiceCode + '\'' +
                ", invoiceAmount='" + invoiceAmount + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
