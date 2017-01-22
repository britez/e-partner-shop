
package com.epartner.mercadopago.ipn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "date_created",
    "date_approved",
    "money_release_date",
    "last_modified",
    "payer",
    "order_id",
    "external_reference",
    "merchant_order_id",
    "reason",
    "currency_id",
    "transaction_amount",
    "net_received_amount",
    "total_paid_amount",
    "shipping_cost",
    "coupon_amount",
    "coupon_fee",
    "finance_fee",
    "discount_fee",
    "coupon_id",
    "status",
    "status_detail",
    "installments",
    "issuer_id",
    "installment_amount",
    "deferred_period",
    "payment_type",
    "marketplace",
    "operation_type",
    "transaction_order_id",
    "statement_descriptor",
    "cardholder",
    "authorization_code",
    "marketplace_fee",
    "deduction_schema",
    "refunds",
    "amount_refunded",
    "last_modified_by_admin",
    "concept_id",
    "concept_amount",
    "internal_metadata",
    "collector"
})
public class Collection {

    @JsonProperty("id")
    @JsonIgnore
    private Long id;
    @JsonProperty("site_id")
    @JsonIgnore
    private String siteId;
    @JsonIgnore
    @JsonProperty("date_created")
    private String dateCreated;
    @JsonIgnore
    @JsonProperty("date_approved")
    private String dateApproved;
    @JsonIgnore
    @JsonProperty("money_release_date")
    private String moneyReleaseDate;
    @JsonIgnore
    @JsonProperty("last_modified")
    private String lastModified;
    @JsonIgnore
    @JsonProperty("payer")
    private Payer payer;
    @JsonIgnore
    @JsonProperty("order_id")
    private Long orderId;
    private String external_reference;
    @JsonIgnore
    @JsonProperty("merchant_order_id")
    private Long merchantOrderId;
    @JsonIgnore
    @JsonProperty("reason")
    private String reason;
    @JsonIgnore
    @JsonProperty("currency_id")
    private String currencyId;
    @JsonIgnore
    @JsonProperty("transaction_amount")
    private Double transactionAmount;
    @JsonIgnore
    @JsonProperty("net_received_amount")
    private Double netReceivedAmount;
    @JsonIgnore
    @JsonProperty("total_paid_amount")
    private Double totalPaidAmount;
    @JsonIgnore
    @JsonProperty("shipping_cost")
    private Double shippingCost;
    @JsonIgnore
    @JsonProperty("coupon_amount")
    private Double couponAmount;
    @JsonIgnore
    @JsonProperty("coupon_fee")
    private Double couponFee;
    @JsonIgnore
    @JsonProperty("finance_fee")
    private Double financeFee;
    @JsonIgnore
    @JsonProperty("discount_fee")
    private Double discountFee;
    @JsonIgnore
    @JsonProperty("coupon_id")
    private Object couponId;
    @JsonIgnore
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    @JsonProperty("status_detail")
    private String statusDetail;
    @JsonIgnore
    @JsonProperty("installments")
    private Integer installments;
    @JsonIgnore
    @JsonProperty("issuer_id")
    private Long issuerId;
    @JsonIgnore
    @JsonProperty("installment_amount")
    private Double installmentAmount;
    @JsonIgnore
    @JsonProperty("deferred_period")
    private Object deferredPeriod;
    @JsonIgnore
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonIgnore
    @JsonProperty("marketplace")
    private String marketplace;
    @JsonIgnore
    @JsonProperty("operation_type")
    private String operationType;
    @JsonIgnore
    @JsonProperty("transaction_order_id")
    private Long transactionOrderId;
    @JsonIgnore
    @JsonProperty("statement_descriptor")
    private String statementDescriptor;
    @JsonIgnore
    @JsonProperty("cardholder")
    private Cardholder cardholder;
    @JsonIgnore
    @JsonProperty("authorization_code")
    private String authorizationCode;
    @JsonIgnore
    @JsonProperty("marketplace_fee")
    private Double marketplaceFee;
    @JsonIgnore
    @JsonProperty("deduction_schema")
    private Object deductionSchema;
    @JsonIgnore
    @JsonProperty("refunds")
    private List<Object> refunds = null;
    @JsonIgnore
    @JsonProperty("amount_refunded")
    private Double amountRefunded;
    @JsonIgnore
    @JsonProperty("last_modified_by_admin")
    private Object lastModifiedByAdmin;
    @JsonIgnore
    @JsonProperty("concept_id")
    private Object conceptId;
    @JsonIgnore
    @JsonProperty("concept_amount")
    private Double conceptAmount;
    @JsonIgnore
    @JsonProperty("internal_metadata")
    private InternalMetadata internalMetadata;
    @JsonIgnore
    @JsonProperty("collector")
    private Collector collector;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @JsonProperty("site_id")
    public String getSiteId() {
        return siteId;
    }

    @JsonIgnore
    @JsonProperty("site_id")
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @JsonProperty("date_created")
    public String getDateCreated() {
        return dateCreated;
    }

    @JsonProperty("date_created")
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @JsonProperty("date_approved")
    public String getDateApproved() {
        return dateApproved;
    }

    @JsonProperty("date_approved")
    public void setDateApproved(String dateApproved) {
        this.dateApproved = dateApproved;
    }

    @JsonProperty("money_release_date")
    public String getMoneyReleaseDate() {
        return moneyReleaseDate;
    }

    @JsonProperty("money_release_date")
    public void setMoneyReleaseDate(String moneyReleaseDate) {
        this.moneyReleaseDate = moneyReleaseDate;
    }

    @JsonProperty("last_modified")
    public String getLastModified() {
        return lastModified;
    }

    @JsonProperty("last_modified")
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @JsonProperty("payer")
    public Payer getPayer() {
        return payer;
    }

    @JsonProperty("payer")
    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    @JsonProperty("order_id")
    public Long getOrderId() {
        return orderId;
    }

    @JsonProperty("order_id")
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getExternal_reference() {
        return external_reference;
    }

    public void setExternal_reference(String external_reference) {
        this.external_reference = external_reference;
    }

    @JsonProperty("merchant_order_id")
    public Long getMerchantOrderId() {
        return merchantOrderId;
    }

    @JsonProperty("merchant_order_id")
    public void setMerchantOrderId(Long merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    @JsonProperty("reason")
    public String getReason() {
        return reason;
    }

    @JsonProperty("reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

    @JsonProperty("currency_id")
    public String getCurrencyId() {
        return currencyId;
    }

    @JsonProperty("currency_id")
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    @JsonProperty("transaction_amount")
    public Double getTransactionAmount() {
        return transactionAmount;
    }

    @JsonProperty("transaction_amount")
    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @JsonProperty("net_received_amount")
    public Double getNetReceivedAmount() {
        return netReceivedAmount;
    }

    @JsonProperty("net_received_amount")
    public void setNetReceivedAmount(Double netReceivedAmount) {
        this.netReceivedAmount = netReceivedAmount;
    }

    @JsonProperty("total_paid_amount")
    public Double getTotalPaidAmount() {
        return totalPaidAmount;
    }

    @JsonProperty("total_paid_amount")
    public void setTotalPaidAmount(Double totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    @JsonProperty("shipping_cost")
    public Double getShippingCost() {
        return shippingCost;
    }

    @JsonProperty("shipping_cost")
    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    @JsonProperty("coupon_amount")
    public Double getCouponAmount() {
        return couponAmount;
    }

    @JsonProperty("coupon_amount")
    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    @JsonProperty("coupon_fee")
    public Double getCouponFee() {
        return couponFee;
    }

    @JsonProperty("coupon_fee")
    public void setCouponFee(Double couponFee) {
        this.couponFee = couponFee;
    }

    @JsonProperty("finance_fee")
    public Double getFinanceFee() {
        return financeFee;
    }

    @JsonProperty("finance_fee")
    public void setFinanceFee(Double financeFee) {
        this.financeFee = financeFee;
    }

    @JsonProperty("discount_fee")
    public Double getDiscountFee() {
        return discountFee;
    }

    @JsonProperty("discount_fee")
    public void setDiscountFee(Double discountFee) {
        this.discountFee = discountFee;
    }

    @JsonProperty("coupon_id")
    public Object getCouponId() {
        return couponId;
    }

    @JsonProperty("coupon_id")
    public void setCouponId(Object couponId) {
        this.couponId = couponId;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("status_detail")
    public String getStatusDetail() {
        return statusDetail;
    }

    @JsonProperty("status_detail")
    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    @JsonProperty("installments")
    public Integer getInstallments() {
        return installments;
    }

    @JsonProperty("installments")
    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    @JsonProperty("issuer_id")
    public Long getIssuerId() {
        return issuerId;
    }

    @JsonProperty("issuer_id")
    public void setIssuerId(Long issuerId) {
        this.issuerId = issuerId;
    }

    @JsonProperty("installment_amount")
    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    @JsonProperty("installment_amount")
    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    @JsonProperty("deferred_period")
    public Object getDeferredPeriod() {
        return deferredPeriod;
    }

    @JsonProperty("deferred_period")
    public void setDeferredPeriod(Object deferredPeriod) {
        this.deferredPeriod = deferredPeriod;
    }

    @JsonProperty("payment_type")
    public String getPaymentType() {
        return paymentType;
    }

    @JsonProperty("payment_type")
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @JsonProperty("marketplace")
    public String getMarketplace() {
        return marketplace;
    }

    @JsonProperty("marketplace")
    public void setMarketplace(String marketplace) {
        this.marketplace = marketplace;
    }

    @JsonProperty("operation_type")
    public String getOperationType() {
        return operationType;
    }

    @JsonProperty("operation_type")
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @JsonProperty("transaction_order_id")
    public Long getTransactionOrderId() {
        return transactionOrderId;
    }

    @JsonProperty("transaction_order_id")
    public void setTransactionOrderId(Long transactionOrderId) {
        this.transactionOrderId = transactionOrderId;
    }

    @JsonProperty("statement_descriptor")
    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    @JsonProperty("statement_descriptor")
    public void setStatementDescriptor(String statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
    }

    @JsonProperty("cardholder")
    public Cardholder getCardholder() {
        return cardholder;
    }

    @JsonProperty("cardholder")
    public void setCardholder(Cardholder cardholder) {
        this.cardholder = cardholder;
    }

    @JsonProperty("authorization_code")
    public String getAuthorizationCode() {
        return authorizationCode;
    }

    @JsonProperty("authorization_code")
    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    @JsonProperty("marketplace_fee")
    public Double getMarketplaceFee() {
        return marketplaceFee;
    }

    @JsonProperty("marketplace_fee")
    public void setMarketplaceFee(Double marketplaceFee) {
        this.marketplaceFee = marketplaceFee;
    }

    @JsonProperty("deduction_schema")
    public Object getDeductionSchema() {
        return deductionSchema;
    }

    @JsonProperty("deduction_schema")
    public void setDeductionSchema(Object deductionSchema) {
        this.deductionSchema = deductionSchema;
    }

    @JsonProperty("refunds")
    public List<Object> getRefunds() {
        return refunds;
    }

    @JsonProperty("refunds")
    public void setRefunds(List<Object> refunds) {
        this.refunds = refunds;
    }

    @JsonProperty("amount_refunded")
    public Double getAmountRefunded() {
        return amountRefunded;
    }

    @JsonProperty("amount_refunded")
    public void setAmountRefunded(Double amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    @JsonProperty("last_modified_by_admin")
    public Object getLastModifiedByAdmin() {
        return lastModifiedByAdmin;
    }

    @JsonProperty("last_modified_by_admin")
    public void setLastModifiedByAdmin(Object lastModifiedByAdmin) {
        this.lastModifiedByAdmin = lastModifiedByAdmin;
    }

    @JsonProperty("concept_id")
    public Object getConceptId() {
        return conceptId;
    }

    @JsonProperty("concept_id")
    public void setConceptId(Object conceptId) {
        this.conceptId = conceptId;
    }

    @JsonProperty("concept_amount")
    public Double getConceptAmount() {
        return conceptAmount;
    }

    @JsonProperty("concept_amount")
    public void setConceptAmount(Double conceptAmount) {
        this.conceptAmount = conceptAmount;
    }

    @JsonProperty("internal_metadata")
    public InternalMetadata getInternalMetadata() {
        return internalMetadata;
    }

    @JsonProperty("internal_metadata")
    public void setInternalMetadata(InternalMetadata internalMetadata) {
        this.internalMetadata = internalMetadata;
    }

    @JsonProperty("collector")
    public Collector getCollector() {
        return collector;
    }

    @JsonProperty("collector")
    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
