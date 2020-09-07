package cn.muses.common.orm.mybatis.easylist.list.base;

/**
 * SortType <br/>
 * 从TM组件中抽取出来
 *
 * @see com.focustech.tm.components.easylist.ISortable
 */
public enum SortType {
    ASC("asc"),
    DESC("desc");
    private final String sort;

    private SortType(String sort) {
        this.sort = sort;
    }

    public static SortType getType(String sort) {
        SortType result = ASC;

        if (DESC.sort.equals(sort)) {
            result = DESC;
        }

        return result;
    }

    @Override
    public String toString() {
        return sort;
    }

    public SortType reverse() {
        return ASC == this ? DESC : ASC;
    }
}
