package com.muses.common.orm.sqlmap.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

import com.muses.common.orm.mybatis.MyBatisDAO;
import com.muses.common.orm.sqlmap.AbstractSqlmapAspect;
import com.muses.common.orm.sqlmap.sqlsession.MyBatisSqlSession;
import com.muses.common.orm.sqlmap.sqlsession.SqlSession;

/**
 *
 * @author Jervis
 * @date 14/12/18 018
 */
@Aspect
public class MyBatisSqlmapAspect extends AbstractSqlmapAspect {

	@Override
	protected SqlSession createSqlSession(ProceedingJoinPoint joinPoint) {
		SqlSession sqlSession = null;
		if (joinPoint.getTarget() instanceof MyBatisDAO) {
			MyBatisDAO mybatisDAO = (MyBatisDAO) joinPoint.getTarget();
			sqlSession = sqlSessions.get(mybatisDAO.getSqlSession());
			if (sqlSession == null) {
				sqlSession = new MyBatisSqlSession(mybatisDAO.getSqlSession());
				sqlSessions.putIfAbsent(mybatisDAO.getSqlSession(), sqlSession);
			}
		}
		return sqlSession;
	}
}
