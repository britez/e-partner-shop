
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
    "site_id",
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
    private Integer id;
    @JsonProperty("site_id")
    private String siteId;
    @JsonProperty("date_created")
    private String dateCreated;
    @JsonProperty("date_approved")
    private String dateApproved;
    @JsonProperty("money_release_date")
    private String moneyReleaseDate;
    @JsonProperty("last_modified")
    private String lastModified;
    @JsonProperty("payer")
    private Payer payer;
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("external_reference")
    private String externalReference;
    @JsonProperty("merchant_order_id")
    private Integer merchantOrderId;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("currency_id")
    private String currencyId;
    @JsonProperty("transaction_amount")
    private Integer transactionAmount;
    @JsonProperty("net_received_amount")
    private Double netReceivedAmount;
    @JsonProperty("total_paid_amount")
    private Integer totalPaidAmount;
    @JsonProperty("shipping_cost")
    private Integer shippingCost;
    @JsonProperty("coupon_amount")
    private Integer couponAmount;
    @JsonProperty("coupon_fee")
    private Integer couponFee;
    @JsonProperty("finance_fee")
    private Integer financeFee;
    @JsonProperty("discount_fee")
    private Integer discountFee;
    @JsonProperty("coupon_id")
    private Object couponId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("status_detail")
    private String statusDetail;
    @JsonProperty("installments")
    private Integer installments;
    @JsonProperty("issuer_id")
    private Integer issuerId;
    @JsonProperty("installment_amount")
    private Integer installmentAmount;
    @JsonProperty("deferred_period")
    private Object deferredPeriod;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("marketplace")
    private String marketplace;
    @JsonProperty("operation_type")
    private String operationType;
    @JsonProperty("transaction_order_id")
    private String transactionOrderId;
    @JsonProperty("statement_descriptor")
    private String statementDescriptor;
    @JsonProperty("cardholder")
    private Cardholder cardholder;
    @JsonProperty("authorization_code")
    private String authorizationCode;
    @JsonProperty("marketplace_fee")
    private Integer marketplaceFee;
    @JsonProperty("deduction_schema")
    private Object deductionSchema;
    @JsonProperty("refunds")
    private List<Object> refunds = null;
    @JsonProperty("amount_refunded")
    private Integer amountRefunded;
    @JsonProperty("last_modified_by_admin")
    private Object lastModifiedByAdmin;
    @JsonProperty("concept_id")
    private Object conceptId;
    @JsonProperty("concept_amount")
    private Integer conceptAmount;
    @JsonProperty("internal_metadata")
    private InternalMetadata internalMetadata;
    @JsonProperty("collector")
    private Collector collector;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("site_id")
    public String getSiteId() {
        return siteId;
    }

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
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("order_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("external_reference")
    public String getExternalReference() {
        return externalReference;
    }

    @JsonProperty("external_reference")
    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    @JsonProperty("merchant_order_id")
    public Integer getMerchantOrderId() {
        return merchantOrderId;
    }

    @JsonProperty("merchant_order_id")
    public void setMerchantOrderId(Integer merchantOrderId) {
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
    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    @JsonProperty("transaction_amount")
    public void setTransactionAmount(Integer transactionAmount) {
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
    public Integer getTotalPaidAmount() {
        return totalPaidAmount;
    }

    @JsonProperty("total_paid_amount")
    public void setTotalPaidAmount(Integer totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    @JsonProperty("shipping_cost")
    public Integer getShippingCost() {
        return shippingCost;
    }

    @JsonProperty("shipping_cost")
    public void setShippingCost(Integer shippingCost) {
        this.shippingCost = shippingCost;
    }

    @JsonProperty("coupon_amount")
    public Integer getCouponAmount() {
        return couponAmount;
    }

    @JsonProperty("coupon_amount")
    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    @JsonProperty("coupon_fee")
    public Integer getCouponFee() {
        return couponFee;
    }

    @JsonProperty("coupon_fee")
    public void setCouponFee(Integer couponFee) {
        this.couponFee = couponFee;
    }

    @JsonProperty("finance_fee")
    public Integer getFinanceFee() {
        return financeFee;
    }

    @JsonProperty("finance_fee")
    public void setFinanceFee(Integer financeFee) {
        this.financeFee = financeFee;
    }

    @JsonProperty("discount_fee")
    public Integer getDiscountFee() {
        return discountFee;
    }

    @JsonProperty("discount_fee")
    public void setDiscountFee(Integer discountFee) {
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
    public Integer getIssuerId() {
        return issuerId;
    }

    @JsonProperty("issuer_id")
    public void setIssuerId(Integer issuerId) {
        this.issuerId = issuerId;
    }

    @JsonProperty("installment_amount")
    public Integer getInstallmentAmount() {
        return installmentAmount;
    }

    @JsonProperty("installment_amount")
    public void setInstallmentAmount(Integer installmentAmount) {
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
    public String getTransactionOrderId() {
        return transactionOrderId;
    }

    @JsonProperty("transaction_order_id")
    public void setTransactionOrderId(String transactionOrderId) {
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
    public Integer getMarketplaceFee() {
        return marketplaceFee;
    }

    @JsonProperty("marketplace_fee")
    public void setMarketplaceFee(Integer marketplaceFee) {
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
    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    @JsonProperty("amount_refunded")
    public void setAmountRefunded(Integer amountRefunded) {
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
    public Integer getConceptAmount() {
        return conceptAmount;
    }

    @JsonProperty("concept_amount")
    public void setConceptAmount(Integer conceptAmount) {
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
