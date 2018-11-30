/**
 * Author Jervis
 * XYZ Reserved
 * Created on 2016年6月16日 上午10:16:02
 */
package com.muses.common.orm.sqlmap.sqlsession;

import java.io.IOException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解决Spring结合MyBatis使用动态代理接口方式时, 如果Mapper配置文件和Mapper接口不匹配情况下, 打印异常日志, 便于问题定位
 * @author Jervis
 *
 */
public class SqlSessionFactoryBean extends org.mybatis.spring.SqlSessionFactoryBean {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        try {
            return super.buildSqlSessionFactory();
        } catch (IOException e) {
            log.error("初始化SqlSessionFactory失败", e);
            throw e;
        }

    }

}
