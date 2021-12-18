package org.myorg.quickstart.dto;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @author tengyujia <tengyujia@kuaishou.com>
 * Created on 2021-12-16
 */
@Data
public class PersonGroup {

    private String personId;

    private Integer insCount;

    private BigDecimal amountIncurredSum;

    private BigDecimal amountIncurredAvg;

    private BigDecimal amountIncurredMax;

    private BigDecimal amountOfThisApprovalSum;

    private BigDecimal amountOfThisApprovalAvg;

    private BigDecimal amountOfThisApprovalMax;

    private BigDecimal amountOfThisApprovalPer;

    private BigDecimal subsidyApprovalAmountSum;

    private BigDecimal subsidyApprovalAmountAvg;

    private BigDecimal subsidyApprovalAmountMax;

    private BigDecimal subsidyApprovalAmountPer;

    private BigDecimal pureOutOfPocketAmountSum;

    private BigDecimal pureOutOfPocketAmountAvg;

    private BigDecimal pureOutOfPocketAmountMax;

    private BigDecimal pureOutOfPocketAmountPer;

    private BigDecimal amountPaidByTheBasicMedicalInsurancePoolingFundSum;

    private BigDecimal amountPaidByTheBasicMedicalInsurancePoolingFundAvg;

    private BigDecimal amountPaidByTheBasicMedicalInsurancePoolingFundMax;

    private BigDecimal amountPaidByTheBasicMedicalInsurancePoolingFundPer;

    private BigDecimal theAmountPaidByTheMedicalAssistanceFundForCivilServantsSum;

    private BigDecimal theAmountPaidByTheMedicalAssistanceFundForCivilServantsAvg;

    private BigDecimal theAmountPaidByTheMedicalAssistanceFundForCivilServantsMax;

    private BigDecimal theAmountPaidByTheMedicalAssistanceFundForCivilServantsPer;

    private BigDecimal basicMedicalInsurancePersonalAccountPaymentAmountSum;

    private BigDecimal basicMedicalInsurancePersonalAccountPaymentAmountAvg;

    private BigDecimal basicMedicalInsurancePersonalAccountPaymentAmountMax;

    private BigDecimal basicMedicalInsurancePersonalAccountPaymentAmountPer;

    private BigDecimal nonAccountPaymentAmountSum;

    private BigDecimal nonAccountPaymentAmountAvg;

    private BigDecimal nonAccountPaymentAmountMax;

    private BigDecimal nonAccountPaymentAmountPer;

    private BigDecimal amountOfSelfSufficiencyAboveTheThresholdSum;

    private BigDecimal amountOfSelfSufficiencyAboveTheThresholdAvg;

    private BigDecimal amountOfSelfSufficiencyAboveTheThresholdMax;

    private BigDecimal amountOfSelfSufficiencyAboveTheThresholdPer;

    private BigDecimal drugFeeDeclarationAmountSum;

    private BigDecimal drugFeeDeclarationAmountAvg;

    private BigDecimal drugFeeDeclarationAmountMax;

    private BigDecimal drugFeeDeclarationAmountPer;

    private BigDecimal trinocularEntry;//三目条目

    private BigDecimal numberOfTrinoculars;//三目数量

    private Long visitHospitalCount;

    private Integer treatTimeWindow;

    private BigDecimal treatFrequency;//就医频率

    private String classTarget;

}
