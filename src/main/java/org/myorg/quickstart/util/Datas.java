package org.myorg.quickstart.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tengyujia <tengyujia@buaa.edu.cn>
 * Created on 2021-12-16
 */
@Slf4j
public class Datas {


    public static BigDecimal toBigDecimal(String value) {
        if (StringUtils.isBlank(value)) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(value);
        } catch (Exception e) {
            log.error("trans failed,value:[{}]", value);
            return BigDecimal.ZERO;
        }
    }


    public static <T> T safe(Supplier<T> supplier, T defaultValue) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.error("run failed,default value:[{}]", defaultValue, e);
            return defaultValue;
        }
    }

    public static <T> BigDecimal max(List<T> list, Function<T, BigDecimal> map) {
        return list.stream().map(map)
                .reduce(BigDecimal.ZERO, BinaryOperator.maxBy(BigDecimal::compareTo));
    }

    public static <T> Integer sumInt(List<T> list, Function<T, Integer> map) {
        return list.stream().mapToInt(map::apply).sum();
    }

    public static <T> BigDecimal sum(List<T> list, Function<T, BigDecimal> map) {
        return list.stream().map(map)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal max(List<BigDecimal> s) {
        return s.stream().reduce(BigDecimal.ZERO, BinaryOperator.maxBy(BigDecimal::compareTo));
    }

    public static BigDecimal min(List<BigDecimal> s) {
        return s.stream().reduce(BigDecimal.ZERO, BinaryOperator.minBy(BigDecimal::compareTo));
    }

    public static BigDecimal sum(List<BigDecimal> s) {
        return s.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal avg(List<BigDecimal> s) {
        long count = s.size();
        if (count == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = sum(s);
        return sum.divide(BigDecimal.valueOf(count), 4, RoundingMode.HALF_UP);
    }

    public static <T> List<BigDecimal> mapToBigDecimal(List<T> list, Function<T, String> mapFuc) {
        return list.stream()
                .map(mapFuc)
                .filter(StringUtils::isNotBlank)
                .map(Datas::toBigDecimal)
                .collect(Collectors.toList());
    }

    public static <T, O> List<O> map(List<T> list, Function<T, O> mapFuc) {
        return list.stream()
                .map(mapFuc)
                .collect(Collectors.toList());
    }

    public static <T, O> O getAny(List<T> list, Function<T, O> mapFuc) {
        return list.stream()
                .map(mapFuc)
                .findAny().orElse(null);
    }

    /**
     * source / target
     */
    public static BigDecimal div(BigDecimal source, Integer target) {
        if (Objects.isNull(target)) {
            return BigDecimal.ZERO;
        }
        return div(source, BigDecimal.valueOf(target));
    }

    public static BigDecimal div(BigDecimal source, BigDecimal target) {
        if (Objects.isNull(target) || target.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return source.divide(target, 4, RoundingMode.HALF_UP);
    }

    public static <T> List<LocalDate> mapToDate(List<T> data, Function<T, String> getFunc) {
        return data.stream()
                .map(getFunc)
                .map(LocalDate::parse)
                .collect(Collectors.toList());
    }

    public static LocalDate earliest(List<LocalDate> dates) {
        return dates.stream().min(LocalDate::compareTo).orElse(null);
    }

    public static LocalDate latest(List<LocalDate> dates) {
        return dates.stream().max(LocalDate::compareTo).orElse(null);
    }
}
