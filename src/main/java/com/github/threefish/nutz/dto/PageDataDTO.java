package com.github.threefish.nutz.dto;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/20</p>
 */
public class PageDataDTO<T> {

    private Long count;

    private T data;

    public PageDataDTO(Long count, T data) {
        this.count = count;
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public T getData() {
        return data;
    }

}
