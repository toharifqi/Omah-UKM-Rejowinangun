package com.toharifqi.um.ukmq.model;

import java.util.HashMap;
import java.util.Map;

public class PaymentModel {
    String bankProvider, accountName, noRekening, nominalTransaction, buktiUrl, productInvestName
            , senderId, productInvestId, timeStamp, status, type, parentId;

    public PaymentModel() {
    }

    public PaymentModel(String bankProvider, String accountName, String noRekening, String nominalTransaction,
                        String buktiUrl, String productInvestName, String senderId, String productInvestId,
                        String timeStamp, String status, String type, String parentId) {
        this.bankProvider = bankProvider;
        this.accountName = accountName;
        this.noRekening = noRekening;
        this.nominalTransaction = nominalTransaction;
        this.buktiUrl = buktiUrl;
        this.productInvestName = productInvestName;
        this.senderId = senderId;
        this.productInvestId = productInvestId;
        this.timeStamp = timeStamp;
        this.status = status;
        this.type = type;
        this.parentId = parentId;
    }

    public String getBankProvider() {
        return bankProvider;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public String getNominalTransaction() {
        return nominalTransaction;
    }

    public String getBuktiUrl() {
        return buktiUrl;
    }

    public String getProductInvestName() {
        return productInvestName;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getProductInvestId() {
        return productInvestId;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getParentId() {
        return parentId;
    }

    public Map<String, Object> addProgress() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("bankProvider", bankProvider);
        result.put("accountName", accountName);
        result.put("noRekening", noRekening);
        result.put("nominalTransaction", nominalTransaction);
        result.put("buktiUrl", buktiUrl);
        result.put("productInvestName", productInvestName);
        result.put("senderId", senderId);
        result.put("productInvestId", productInvestId);
        result.put("timeStamp", timeStamp);
        result.put("status", status);
        result.put("type", type);
        result.put("parentId", parentId);
        return result;
    }

}
