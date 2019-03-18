package com.github.threefish.nutz.sqltpl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;

import java.io.IOException;
import java.util.Map;

/**
 * @author 黄川 huchuc@vip.qq.com
 * <p>Date: 2019/2/19</p>
 */
@IocBean(create = "init")
public class BeetlSqlTemplteEngineImpl implements ISqlTemplteEngine {

    GroupTemplate gt;
    String statementStart = "<exp>";
    String statementEnd = "</exp>";

    public void setStatementStart(String statementStart) {
        this.statementStart = statementStart;
    }

    public void setStatementEnd(String statementEnd) {
        this.statementEnd = statementEnd;
    }

    public void init() {
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            cfg.setStatementStart(statementStart);
            cfg.setStatementEnd(statementEnd);
            cfg.setHtmlTagSupport(false);
            gt = new GroupTemplate(new StringTemplateResourceLoader(), cfg);
            gt.registerFunctionPackage("Strings", Strings.class);
            gt.registerFunctionPackage("Times", Times.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析模版
     *
     * @param templeText 模版内容
     * @param bindData   绑定参数
     * @return 渲染后的SQL
     */
    @Override
    public String render(String templeText, Map bindData) throws BeetlException {
        Template template = gt.getTemplate(templeText);
        template.binding(bindData);
        return template.render().trim();
    }
}
