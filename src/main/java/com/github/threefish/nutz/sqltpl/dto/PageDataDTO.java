package com.github.threefish.nutz.sqltpl.dto;

import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/20</p>
 */
public class PageDataDTO<T> {

    /**
     * 返回数据总数
     */
    private final Long count;
    /**
     * 返回数据
     */
    private final List<T> data;

    public PageDataDTO(Long count, List<T> data) {
        this.count = count;
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "PageDataDTO{" +
                "count=" + count +
                ", data=" + data +
                '}';
    }
}
