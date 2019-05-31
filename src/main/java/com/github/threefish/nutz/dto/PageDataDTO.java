package com.github.threefish.nutz.dto;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/20</p>
 */
public class PageDataDTO<T> {

    /**
     * 返回数据总数
     */
    private Long count;
    /**
     * 返回数据
     */
    private T data;

    public PageDataDTO(Long count, T data) {
        this.count = count;
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public <T> T getData() {
        return (T) data;
    }

}
