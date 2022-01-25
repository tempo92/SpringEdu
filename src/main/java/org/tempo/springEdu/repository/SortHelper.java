package org.tempo.springEdu.repository;

import org.springframework.data.domain.Sort;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortHelper {
    public static Sort createSort(List<String> sortList) {
        if (sortList == null)
            return  null;

        Pattern pattern = Pattern.compile("(.+?):([01]+?)");
        var orderList = new ArrayList<Sort.Order>();
        for (var sortItem : sortList) {
            Matcher matcher = pattern.matcher(sortItem);
            if (matcher.find()) {
                orderList.add(
                        new Sort.Order(convertSortDirection(matcher.group(2)),
                        matcher.group(1)));
            } else {
                throw new ValidationException(
                        String.format("Sort info (%s) is invalid", sortItem));
            }
        }
        return Sort.by(orderList);
    }

    private static Sort.Direction convertSortDirection(String direction) {
        switch (direction) {
            case "0":
                return Sort.Direction.ASC;
            case "1":
                return Sort.Direction.DESC;
            default:
                throw new ValidationException("Sort direction must be '0' or '1'");
        }
    }
}
