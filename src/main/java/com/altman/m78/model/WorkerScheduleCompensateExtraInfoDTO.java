package com.altman.m78.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerScheduleCompensateExtraInfoDTO implements Serializable {

    private static final long serialVersionUID     = -7132640896542727414L;

    private String            orderNo;

    private String            tpPolicyNo;

    private String            tpApplyNo;

    private String            compelTpApplyNo;

    private String            regenerateEPolicy;

    /**
     *  需要发送邮件的人(add by xiangzhiwei)
     *  add by Forgot 2020-05-11 任务类型为99-重新补偿特定任务时，此字段只要非空，就认为需要重新发邮件
     */
    private String            toEmails;

    // 状态（0：中间状态 1：调用单证生成pdf接口异常，2:发送邮件失败）
    private String            status;

    // 完税资料上传地址
    private String[]          taxPayedUplaodPaths;

    // 电子批单上传地址
    private String            endorseUplaodPath;

    // 机构编码
    private String            departmentCode;

    // 是否操作成功，0失败，1成功
    private String            success              = "0";

    // 批单信息
//    private EndorseNeedDTO    endorseInfo;

    // 发送批改退费邮件标志，0失败，1成功
    private String            endorseFailEmailFlag = "0";

    /**
     * 投保地
     */
    private String            insurePlaceCode;

    private String            endorsementPolicyStatus;

    //归档时的50个保单集合
    private List<String>      policyNos;

    //归档时的50个批单集合
    private List<String>      endorseNos;

    private Map<String,Object> endorseMap = new HashMap<String,Object>();
    
    // ------------电子发票扩展信息------------------
    // 保单号(唯一标识)
    private String              policyNo;

    // 发票类型    0电子发票 1纸张发票  注：电子发票只能开具普通发票  Y
    private Integer             invoiceType;

    // 1:承保,7:批增，8批减，9：批退
    private String              businessType;

    // 发票号码
    private String              invoiceNo;

    // 发票代码
    private String              invoiceCode;

    // 金额
    private String              invoiceAmount;

    // 发票地址
    private String              invoiceUrl;

    // 发票的状态
    private String              invoiceStatus;

    // 批改订单号
    private String              endorseOrderNo;
    
    // 批改电子发票收件人地址
    private String              receiverEMail;
    
    /**
     *  重跑定时任务标志及类型，为重跑某些特定任务时新增，此标志有值时重跑定时任务时，跳过某些保单数据校验
     *  **正常情况请勿使用**
     */
    private String              rerunCompensateType;
    
    private String              vehicleUsageTypeCode;
    
    public String getRerunCompensateType() {
		return rerunCompensateType;
	}

	public void setRerunCompensateType(String rerunCompensateType) {
		this.rerunCompensateType = rerunCompensateType;
	}

	public Map<String, Object> getEndorseMap() {
        return endorseMap;
    }

    public void setEndorseMap(Map<String, Object> endorseMap) {
        this.endorseMap = endorseMap;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTpPolicyNo() {
        return tpPolicyNo;
    }

    public void setTpPolicyNo(String tpPolicyNo) {
        this.tpPolicyNo = tpPolicyNo;
    }

    public String getTpApplyNo() {
        return tpApplyNo;
    }

    public void setTpApplyNo(String tpApplyNo) {
        this.tpApplyNo = tpApplyNo;
    }

    public String getCompelTpApplyNo() {
        return compelTpApplyNo;
    }

    public void setCompelTpApplyNo(String compelTpApplyNo) {
        this.compelTpApplyNo = compelTpApplyNo;
    }

    public String getRegenerateEPolicy() {
        return regenerateEPolicy;
    }

    public void setRegenerateEPolicy(String regenerateEPolicy) {
        this.regenerateEPolicy = regenerateEPolicy;
    }

    public String getToEmails() {
        return toEmails;
    }

    public void setToEmails(String toEmails) {
        this.toEmails = toEmails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getTaxPayedUplaodPaths() {
        return taxPayedUplaodPaths;
    }

    public void setTaxPayedUplaodPaths(String[] taxPayedUplaodPaths) {
        this.taxPayedUplaodPaths = taxPayedUplaodPaths;
    }

    public String getEndorseUplaodPath() {
        return endorseUplaodPath;
    }

    public void setEndorseUplaodPath(String endorseUplaodPath) {
        this.endorseUplaodPath = endorseUplaodPath;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

//    public EndorseNeedDTO getEndorseInfo() {
//        return endorseInfo;
//    }
//
//    public void setEndorseInfo(EndorseNeedDTO endorseInfo) {
//        this.endorseInfo = endorseInfo;
//    }

    public String getEndorseFailEmailFlag() {
        return endorseFailEmailFlag;
    }

    public void setEndorseFailEmailFlag(String endorseFailEmailFlag) {
        this.endorseFailEmailFlag = endorseFailEmailFlag;
    }

    public String getEndorsementPolicyStatus() {
        return endorsementPolicyStatus;
    }

    public void setEndorsementPolicyStatus(String endorsementPolicyStatus) {
        this.endorsementPolicyStatus = endorsementPolicyStatus;
    }

    public List<String> getPolicyNos() {
        return policyNos;
    }

    public void setPolicyNos(List<String> policyNos) {
        this.policyNos = policyNos;
    }

    public String getInsurePlaceCode() {
        return insurePlaceCode;
    }

    public void setInsurePlaceCode(String insurePlaceCode) {
        this.insurePlaceCode = insurePlaceCode;
    }

    public List<String> getEndorseNos() {
        return endorseNos;
    }

    public void setEndorseNos(List<String> endorseNos) {
        this.endorseNos = endorseNos;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
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

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getEndorseOrderNo() {
        return endorseOrderNo;
    }

    public void setEndorseOrderNo(String endorseOrderNo) {
        this.endorseOrderNo = endorseOrderNo;
    }

    public String getReceiverEMail() {
        return receiverEMail;
    }

    public void setReceiverEMail(String receiverEMail) {
        this.receiverEMail = receiverEMail;
    }

	public String getVehicleUsageTypeCode() {
		return vehicleUsageTypeCode;
	}

	public void setVehicleUsageTypeCode(String vehicleUsageTypeCode) {
		this.vehicleUsageTypeCode = vehicleUsageTypeCode;
	}

}
