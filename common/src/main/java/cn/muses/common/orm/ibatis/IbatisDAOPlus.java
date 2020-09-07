package cn.muses.common.orm.ibatis;

import java.util.List;

import javax.annotation.PostConstruct;

import cn.muses.common.orm.common.TypeConverter;
import cn.muses.common.orm.page.PageReq;
import cn.muses.common.orm.page.PageRes;
import org.springframework.core.convert.converter.ConverterRegistry;

/**
 * IbatisDAO加强版，主要是默认添加入参与出参为DTO
 *
 * @author Jervis
 *
 * @param <Entity>
 * @param <Criteria>
 * @param <DTO>
 */
public abstract class IbatisDAOPlus<Entity, Criteria, DTO> extends IbatisDAO<Entity, Criteria> {
	protected TypeConverter<Entity, DTO> typeConverter;

	@Override
	@PostConstruct
	public void postConstruct() {
		super.postConstruct();
		this.addConverter((ConverterRegistry) ormContext.getConversionService());
		typeConverter = new TypeConverter<Entity, DTO>(this.getClass(), ormContext.getConversionService());
	}

	protected abstract void addConverter(ConverterRegistry registry);

	public int deleteByPrimaryKeyPlus(DTO dto) {
		int rows = super.deleteByPrimaryKey(typeConverter.toEntity(dto));
		return rows;
	}

	public void insertPlus(DTO dto) {
		super.insert(typeConverter.toEntity(dto));
	}

	public void insertSelectivePlus(DTO dto) {
		super.insertSelective(typeConverter.toEntity(dto));
	}

	public List<DTO> selectByCriteriaPlus(Criteria criteria) {
		List<Entity> list = super.selectByCriteria(criteria);
		return typeConverter.toDTOList(list);
	}

	public PageRes<DTO> selectByCriteriaWithPageReqPlus(Criteria criteria, PageReq pageReq) {
		PageRes<Entity> p1 = super.selectByCriteriaWithPageReq(criteria, pageReq);
		PageRes<DTO> p2 = new PageRes<DTO>(pageReq);
		p2.setTotalCount(p1.getTotalCount());
		p2.setData(typeConverter.toDTOList(p1.getData()));
		return p2;
	}

	public List<DTO> selectByCriteriaWithRowboundsPlus(Criteria criteria, int skipResults, int maxResults) {
		List<Entity> rows = super.selectByCriteriaWithRowbounds(criteria, skipResults, maxResults);
		return typeConverter.toDTOList(rows);
	}

	public List<DTO> selectByCriteriaWithBLOBsPlus(Criteria criteria) {
		List<Entity> list = super.selectByCriteriaWithBLOBs(criteria);
		return typeConverter.toDTOList(list);
	}

	public PageRes<DTO> selectByCriteriaWithBLOBsWithPageReqPlus(Criteria criteria, PageReq pageReq) {
		PageRes<Entity> p1 = super.selectByCriteriaWithBLOBsWithPageReq(criteria, pageReq);
		PageRes<DTO> p2 = new PageRes<DTO>(pageReq);
		p2.setTotalCount(p1.getTotalCount());
		p2.setData(typeConverter.toDTOList(p1.getData()));
		return p2;
	}

	public List<DTO> selectByCriteriaWithBLOBsWithRowboundsPlus(Criteria criteria, int skipResults, int maxResults) {
		List<Entity> rows = super.selectByCriteriaWithBLOBsWithRowbounds(criteria, skipResults, maxResults);
		return typeConverter.toDTOList(rows);
	}

	public DTO selectByPrimaryKeyPlus(DTO dto) {
		Entity entity = super.selectByPrimaryKey(typeConverter.toEntity(dto));
		return typeConverter.toDTO(entity);
	}

	public int updateByCriteriaPlus(DTO dto, Criteria criteria) {
		return super.updateByCriteria(typeConverter.toEntity(dto), criteria);
	}

	public int updateByCriteriaSelectivePlus(DTO dto, Criteria criteria) {
		return super.updateByCriteriaSelective(typeConverter.toEntity(dto), criteria);
	}

	public int updateByCriteriaWithBLOBsPlus(DTO dto, Criteria criteria) {
		return super.updateByCriteriaWithBLOBs(typeConverter.toEntity(dto), criteria);
	}

	public int updateByPrimaryKeyPlus(DTO dto) {
		return super.updateByPrimaryKey(typeConverter.toEntity(dto));
	}

	public int updateByPrimaryKeySelectivePlus(DTO dto) {
		return super.updateByPrimaryKeySelective(typeConverter.toEntity(dto));
	}

	public int updateByPrimaryKeyWithBLOBsPlus(DTO dto) {
		return super.updateByPrimaryKeyWithBLOBs(typeConverter.toEntity(dto));
	}
}
