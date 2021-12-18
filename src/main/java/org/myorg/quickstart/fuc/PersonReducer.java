package org.myorg.quickstart.fuc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;
import org.myorg.quickstart.dto.Insurance;
import org.myorg.quickstart.dto.PersonGroup;
import org.myorg.quickstart.util.Datas;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tengyujia <tengyujia@kuaishou.com>
 * Created on 2021-12-16
 */
@Slf4j
public class PersonReducer implements GroupReduceFunction<Insurance, PersonGroup> {
    public static final PersonReducer INSTANCE = new PersonReducer();


    @Override
    public void reduce(Iterable<Insurance> values, Collector<PersonGroup> out) throws Exception {
        List<Insurance> insurances = Lists.newArrayList(values);
        PersonGroup group = new PersonGroup();
        group.setPersonId(Datas.getAny(insurances, Insurance::getPersonId));

        int insCount = insurances.size();
        group.setInsCount(insCount);

        List<BigDecimal> amountIncurred = Datas.mapToBigDecimal(insurances, Insurance::getAmountIncurred);
        group.setAmountIncurredSum(Datas.sum(amountIncurred));
        group.setAmountIncurredMax(Datas.max(amountIncurred));
        group.setAmountIncurredAvg(Datas.div(group.getAmountIncurredSum(), insCount));

        List<BigDecimal> amountOfThisApproval = Datas.mapToBigDecimal(insurances, Insurance::getAmountOfThisApproval);
        group.setAmountOfThisApprovalSum(Datas.sum(amountOfThisApproval));
        group.setAmountOfThisApprovalMax(Datas.max(amountOfThisApproval));
        group.setAmountOfThisApprovalAvg(Datas.div(group.getAmountOfThisApprovalSum(), insCount));
        group.setAmountOfThisApprovalPer(Datas.div(group.getAmountOfThisApprovalSum(), group.getAmountIncurredSum()));

        List<BigDecimal> subsidyApprovalAmount = Datas.mapToBigDecimal(insurances, Insurance::getSubsidyApprovalAmount);
        group.setSubsidyApprovalAmountSum(Datas.sum(subsidyApprovalAmount));
        group.setSubsidyApprovalAmountMax(Datas.max(subsidyApprovalAmount));
        group.setSubsidyApprovalAmountAvg(Datas.div(group.getSubsidyApprovalAmountSum(), group.getInsCount()));
        group.setSubsidyApprovalAmountPer(Datas.div(group.getSubsidyApprovalAmountSum(), group.getAmountIncurredSum()));


        List<BigDecimal> pureOutOfPocketAmount = Datas.mapToBigDecimal(insurances, Insurance::getPureOutOfPocketAmount);
        group.setPureOutOfPocketAmountSum(Datas.sum(pureOutOfPocketAmount));
        group.setPureOutOfPocketAmountMax(Datas.max(pureOutOfPocketAmount));
        group.setPureOutOfPocketAmountAvg(Datas.div(group.getPureOutOfPocketAmountSum(), group.getInsCount()));
        group.setPureOutOfPocketAmountPer(Datas.div(group.getPureOutOfPocketAmountSum(), group.getAmountIncurredSum()));

        List<BigDecimal> amountPaidByTheBasicMedicalInsurancePoolingFund =
                Datas.mapToBigDecimal(insurances, Insurance::getAmountPaidByTheBasicMedicalInsurancePoolingFund);
        group.setAmountPaidByTheBasicMedicalInsurancePoolingFundSum(
                Datas.sum(amountPaidByTheBasicMedicalInsurancePoolingFund));
        group.setAmountPaidByTheBasicMedicalInsurancePoolingFundMax(
                Datas.max(amountPaidByTheBasicMedicalInsurancePoolingFund));
        group.setAmountPaidByTheBasicMedicalInsurancePoolingFundAvg(
                Datas.div(group.getAmountPaidByTheBasicMedicalInsurancePoolingFundSum(), group.getInsCount()));
        group.setAmountPaidByTheBasicMedicalInsurancePoolingFundPer(
                Datas.div(group.getAmountPaidByTheBasicMedicalInsurancePoolingFundSum(), group.getAmountIncurredSum()));

        List<BigDecimal> theAmountPaidByTheMedicalAssistanceFundForCivilServants = Datas.mapToBigDecimal(insurances,
                Insurance::getTheAmountPaidByTheMedicalAssistanceFundForCivilServants);
        group.setTheAmountPaidByTheMedicalAssistanceFundForCivilServantsSum(
                Datas.sum(theAmountPaidByTheMedicalAssistanceFundForCivilServants));
        group.setTheAmountPaidByTheMedicalAssistanceFundForCivilServantsMax(
                Datas.max(theAmountPaidByTheMedicalAssistanceFundForCivilServants));
        group.setTheAmountPaidByTheMedicalAssistanceFundForCivilServantsAvg(
                Datas.div(group.getTheAmountPaidByTheMedicalAssistanceFundForCivilServantsSum(), group.getInsCount()));
        group.setTheAmountPaidByTheMedicalAssistanceFundForCivilServantsPer(
                Datas.div(group.getTheAmountPaidByTheMedicalAssistanceFundForCivilServantsSum(),
                        group.getAmountIncurredSum()));

        List<BigDecimal> basicMedicalInsurancePersonalAccountPaymentAmount =
                Datas.mapToBigDecimal(insurances, Insurance::getBasicMedicalInsurancePersonalAccountPaymentAmount);
        group.setBasicMedicalInsurancePersonalAccountPaymentAmountSum(
                Datas.sum(basicMedicalInsurancePersonalAccountPaymentAmount));
        group.setBasicMedicalInsurancePersonalAccountPaymentAmountMax(
                Datas.max(basicMedicalInsurancePersonalAccountPaymentAmount));
        group.setBasicMedicalInsurancePersonalAccountPaymentAmountAvg(
                Datas.div(group.getBasicMedicalInsurancePersonalAccountPaymentAmountSum(), group.getInsCount()));
        group.setBasicMedicalInsurancePersonalAccountPaymentAmountPer(
                Datas.div(group.getBasicMedicalInsurancePersonalAccountPaymentAmountSum(),
                        group.getAmountIncurredSum()));

        List<BigDecimal> nonAccountPaymentAmount =
                Datas.mapToBigDecimal(insurances, Insurance::getNonAccountPaymentAmount);
        group.setNonAccountPaymentAmountSum(Datas.sum(nonAccountPaymentAmount));
        group.setNonAccountPaymentAmountMax(Datas.max(nonAccountPaymentAmount));
        group.setNonAccountPaymentAmountAvg(Datas.div(group.getNonAccountPaymentAmountSum(), group.getInsCount()));
        group.setNonAccountPaymentAmountPer(
                Datas.div(group.getNonAccountPaymentAmountSum(), group.getAmountIncurredSum()));

        List<BigDecimal> amountOfSelfSufficiencyAboveTheThreshold =
                Datas.mapToBigDecimal(insurances, Insurance::getAmountOfSelfSufficiencyAboveTheThreshold);
        group.setAmountOfSelfSufficiencyAboveTheThresholdSum(Datas.sum(amountOfSelfSufficiencyAboveTheThreshold));
        group.setAmountOfSelfSufficiencyAboveTheThresholdMax(Datas.max(amountOfSelfSufficiencyAboveTheThreshold));
        group.setAmountOfSelfSufficiencyAboveTheThresholdAvg(
                Datas.div(group.getAmountOfSelfSufficiencyAboveTheThresholdSum(), group.getInsCount()));
        group.setAmountOfSelfSufficiencyAboveTheThresholdPer(
                Datas.div(group.getAmountOfSelfSufficiencyAboveTheThresholdSum(), group.getAmountIncurredSum()));

        List<BigDecimal> drugFeeDeclarationAmount =
                Datas.mapToBigDecimal(insurances, Insurance::getDrugFeeDeclarationAmount);
        group.setDrugFeeDeclarationAmountSum(Datas.sum(drugFeeDeclarationAmount));
        group.setDrugFeeDeclarationAmountMax(Datas.max(drugFeeDeclarationAmount));
        group.setDrugFeeDeclarationAmountAvg(Datas.div(group.getDrugFeeDeclarationAmountSum(), group.getInsCount()));
        group.setDrugFeeDeclarationAmountPer(
                Datas.div(group.getDrugFeeDeclarationAmountSum(), group.getAmountIncurredSum()));

        List<BigDecimal> trinocularEntry = Datas.mapToBigDecimal(insurances, Insurance::getTrinocularEntry);
        group.setTrinocularEntry(Datas.sum(trinocularEntry));

        List<BigDecimal> numberOfTrinoculars = Datas.mapToBigDecimal(insurances, Insurance::getNumberOfTrinoculars);
        group.setNumberOfTrinoculars(Datas.sum(numberOfTrinoculars));


        long visitHospitalCount = insurances.stream().map(Insurance::getHospitalId).distinct().count();
        group.setVisitHospitalCount(visitHospitalCount);

        List<LocalDate> dates = Datas.mapToDate(insurances, Insurance::getTransactionHour);
        group.setTreatTimeWindow(Period.between(Datas.earliest(dates), Datas.latest(dates)).getDays());

        group.setTreatFrequency(Datas.div(
                BigDecimal.valueOf(insCount), BigDecimal.valueOf(group.getTreatTimeWindow())));

        group.setClassTarget(Datas.getAny(insurances, Insurance::getClassTarget));

        out.collect(group);
    }

}
