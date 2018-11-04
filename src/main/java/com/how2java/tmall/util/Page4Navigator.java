/**
 * 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
 * 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
 * 供购买者学习，请勿私自传播，否则自行承担相关法律责任
 */

package com.how2java.tmall.util;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class Page4Navigator<T> {
    Page<T> pageFromJPA;
    int navigatePages;

    int totalPages;

    int number;

    long totalElements;

    int size;

    int numberOfElements;

    List<T> content;

    boolean isHasContent;

    boolean first;

    boolean last;

    boolean isHasNext;

    boolean isHasPrevious;

    int[] navigatepageNums;

    public Page4Navigator() {
        //这个空的分页是为了 Redis 从 json格式转换为 Page4Navigator 对象而专门提供的
    }

    public Page4Navigator(Page<T> pageFromJPA, int navigatePages) {
        this.pageFromJPA = pageFromJPA;
        this.navigatePages = navigatePages;

        totalPages = pageFromJPA.getTotalPages();

        number = pageFromJPA.getNumber();

        totalElements = pageFromJPA.getTotalElements();

        size = pageFromJPA.getSize();

        numberOfElements = pageFromJPA.getNumberOfElements();

        content = pageFromJPA.getContent();

        isHasContent = pageFromJPA.hasContent();

        first = pageFromJPA.isFirst();

        last = pageFromJPA.isLast();

        isHasNext = pageFromJPA.hasNext();

        isHasPrevious = pageFromJPA.hasPrevious();

        calcNavigatepageNums();

    }

    private void calcNavigatepageNums() {


        //当总页数小于或等于导航页码数时
        if (totalPages <= navigatePages) {
            navigatepageNums = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                navigatepageNums[i] = i + 1;
            }
            return;
        }

        //当总页数大于导航页码数时
        navigatepageNums = new int[navigatePages];
        int startNum = number - navigatePages / 2;
        int endNum = number + navigatePages / 2;

        if (startNum < 1) {
            startNum = 1;
            //(最前navigatePages页
            for (int i = 0; i < navigatePages; i++) {
                navigatepageNums[i] = startNum++;
            }
        } else if (endNum > totalPages) {
            endNum = totalPages;
            //最后navigatePages页
            for (int i = navigatePages - 1; i >= 0; i--) {
                navigatepageNums[i] = endNum--;
            }
        } else {
            //所有中间页
            for (int i = 0; i < navigatePages; i++) {
                navigatepageNums[i] = startNum++;
            }
        }


    }


}

/**
 * 模仿天猫整站 springboot 教程 为 how2j.cn 版权所有
 * 本教程仅用于学习使用，切勿用于非法用途，由此引起一切后果与本站无关
 * 供购买者学习，请勿私自传播，否则自行承担相关法律责任
 */
