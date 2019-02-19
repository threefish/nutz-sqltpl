package github.threefish.nutz.sqltpl;

import java.util.Map;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/2/19
 */
public interface ISqlTemplteEngine {
    /**
     * 解析模版
     *
     * @param source   源
     * @param bindData 绑定参数
     * @return
     */
    String render(String source, Map bindData);
}
