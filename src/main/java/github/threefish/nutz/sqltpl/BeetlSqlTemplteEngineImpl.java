package github.threefish.nutz.sqltpl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.nutz.ioc.loader.annotation.IocBean;

import java.io.IOException;
import java.util.Map;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2019/2/19
 */
@IocBean(create = "init")
public class BeetlSqlTemplteEngineImpl implements ISqlTemplteEngine {

    static GroupTemplate gt;

    public void init() {
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            cfg.setStatementStart("[#");
            cfg.setStatementEnd("#]");
            cfg.setHtmlTagSupport(false);
            gt = new GroupTemplate(new StringTemplateResourceLoader(), cfg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析模版
     *
     * @param templeText 模版内容
     * @param bindData   绑定参数
     * @return
     */
    @Override
    public String render(String templeText, Map bindData) throws BeetlException {
        Template template = gt.getTemplate(templeText);
        template.binding(bindData);
        return template.render();
    }
}
