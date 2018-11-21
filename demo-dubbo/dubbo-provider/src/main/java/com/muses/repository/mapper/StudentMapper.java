package com.muses.repository.mapper;

import com.muses.common.orm.mybatis.MyBatisRepository;
import com.muses.entity.Student;
import com.muses.entity.StudentCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

@MyBatisRepository
public interface StudentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    int countByCriteria(StudentCriteria criteria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    int deleteByCriteria(StudentCriteria criteria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    int deleteByPrimaryKey(Long studentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    int insert(Student record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    int insertSelective(Student record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    List<Student> selectByCriteriaWithRowbounds(StudentCriteria criteria, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    List<Student> selectByCriteria(StudentCriteria criteria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    Student selectByPrimaryKey(Long studentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    int updateByCriteriaSelective(@Param("record") Student record, @Param("example") StudentCriteria criteria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    int updateByCriteria(@Param("record") Student record, @Param("example") StudentCriteria criteria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    int updateByPrimaryKeySelective(Student record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table INS.STUDENT
     *
     * @mbggenerated Wed Nov 21 13:46:42 CST 2018
     */
    int updateByPrimaryKey(Student record);
}