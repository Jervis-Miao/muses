package com.muses.common.orm.sqlmap.sqlsession;

import java.util.List;

public class MyBatisSqlSession implements SqlSession {
	private org.apache.ibatis.session.SqlSession	sqlSession;

	public MyBatisSqlSession(org.apache.ibatis.session.SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public <T> T selectOne(String statement, Object parameter) {
		return sqlSession.selectOne(statement, parameter);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter) {
		return sqlSession.selectList(statement, parameter);
	}

	@Override
	public int insert(String statement, Object parameter) {
		return sqlSession.insert(statement, parameter);
	}

	@Override
	public int update(String statement, Object parameter) {
		return sqlSession.update(statement, parameter);
	}

	@Override
	public int delete(String statement, Object parameter) {
		return sqlSession.delete(statement, parameter);
	}

}
