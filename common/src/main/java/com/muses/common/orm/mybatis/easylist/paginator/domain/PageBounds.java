package com.muses.common.orm.mybatis.easylist.paginator.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.util.StringUtils;

/**
 * 分页查询对象
 *
 * @author badqiu
 * @author hunhun
 * @author miemiedev
 * @author Jervis
 */
public class PageBounds extends RowBounds implements Serializable {

    private static final long serialVersionUID = -6414350656252331011L;

    public final static int NO_PAGE = 1;
    /** 页号 */
    protected int page = NO_PAGE;
    /** 分页大小 */
    protected int limit = NO_ROW_LIMIT;
    /** 分页排序信息 */
    protected List<Order> orders = new ArrayList<Order>();
    /** 结果集是否包含TotalCount */
    protected boolean containsTotalCount;
    /** 异步查询总数 */
    protected Boolean asyncTotalCount;

    /**
     * 分页条件对象
     */
    private WhereCriteria whereClause;
    /** 已组装好的排序语句 */
    private String orderByClause;

    /**
     * 默认不排序、不分页、不求总数
     */
    public PageBounds() {
    }

    /**
     * Query TOP N, default containsTotalCount = false
     *
     * @param limit 每页大小
     * @see #PageBounds(int, int, boolean)
     */
    public PageBounds(int limit) {
        this(limit, NO_PAGE, false);
    }

    /**
     * 分页，默认containsTotalCount为true，即同时查询总数
     *
     * @param limit 每页大小
     * @param page 页码
     * @see #PageBounds(int, int, boolean)
     */
    public PageBounds(int limit, int page) {
        this(limit, page, true);
    }

    /**
     * 分页
     *
     * @param limit 每页大小
     * @param page 页码
     * @param containsTotalCount 是否查询总数
     */
    public PageBounds(int limit, int page, boolean containsTotalCount) {
        this.page = page;
        this.limit = limit;
        this.containsTotalCount = containsTotalCount;
    }

    public PageBounds(RowBounds rowBounds) {
        if (rowBounds instanceof PageBounds) {
            PageBounds pageBounds = (PageBounds) rowBounds;
            this.page = pageBounds.page;
            this.limit = pageBounds.limit;
            this.orders = pageBounds.orders;
            this.containsTotalCount = pageBounds.containsTotalCount;
            this.asyncTotalCount = pageBounds.asyncTotalCount;
            this.orderByClause = pageBounds.orderByClause;
            this.whereClause = pageBounds.whereClause;
        } else {
            this.page = (rowBounds.getOffset() / rowBounds.getLimit()) + 1;
            this.limit = rowBounds.getLimit();
        }

    }

    /**
     * 是否有有意义的Where条件
     *
     * @return
     */
    /**
     * @return
     */
    public boolean hasWhereClause() {
        return null != whereClause && null != whereClause.getOredCriteria() && !whereClause.getOredCriteria().isEmpty();
    }

    /**
     * 是否有排序条件
     *
     * @return
     */
    public boolean hasOrderByClause() {
        return StringUtils.hasText(orderByClause) || (null != orders && !orders.isEmpty());
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isContainsTotalCount() {
        return containsTotalCount;
    }

    public void setContainsTotalCount(boolean containsTotalCount) {
        this.containsTotalCount = containsTotalCount;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Boolean getAsyncTotalCount() {
        return asyncTotalCount;
    }

    public void setAsyncTotalCount(Boolean asyncTotalCount) {
        this.asyncTotalCount = asyncTotalCount;
    }

    /**
     * @return the whereClause
     */
    public WhereCriteria getWhereClause() {
        return whereClause;
    }

    /**
     * @param whereClause the whereClause to set
     */
    public void setWhereClause(WhereCriteria whereClause) {
        this.whereClause = whereClause;
    }

    /**
     * @return the orderByClause
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * @param orderByClause the orderByClause to set
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Override
    public int getOffset() {
        if (page >= 1) {
            return (page - 1) * limit;
        }
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageBounds{");
        sb.append("page=").append(page);
        sb.append(", limit=").append(limit);
        if (StringUtils.hasText(orderByClause)) {
            sb.append(",orderByClause=").append(orderByClause);
        } else {
            sb.append(", orders=").append(orders);
        }
        sb.append(", containsTotalCount=").append(containsTotalCount);
        sb.append(", asyncTotalCount=").append(asyncTotalCount);
        sb.append('}');
        return sb.toString();
    }

}
