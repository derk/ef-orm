package jef.database.query;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jef.database.Condition;
import jef.database.Condition.Operator;
import jef.database.DataObject;
import jef.database.DebugUtil;
import jef.database.Field;
import jef.database.IConditionField;
import jef.database.IQueryableEntity;
import jef.database.meta.ITableMetadata;
import jef.database.meta.Reference;
import jef.database.wrapper.populator.Transformer;

/**
 * 不可更改的Query对象实现，用于内部处理
 * @author jiyi
 *
 * @param <T>
 */
public final class ReadOnlyQuery<T extends IQueryableEntity> extends AbstractQuery<T> {
	static private final Map<ITableMetadata, Query<?>> cacheOfQuery=new java.util.IdentityHashMap<ITableMetadata, Query<?>>(32);
	public static Query<?> getEmptyQuery(ITableMetadata cls) {
		Query<?> q=cacheOfQuery.get(cls);
		if(q!=null)return q;
		return putEmptyQuery(cls);
	}

	@SuppressWarnings("rawtypes")
	private static synchronized Query<?> putEmptyQuery(ITableMetadata cls) {
		Query<?> q=cacheOfQuery.get(cls);
		if(q!=null)return q;
		try {
			DataObject e=(DataObject)cls.newInstance();
			@SuppressWarnings("unchecked")
			ReadOnlyQuery<?> rq=new ReadOnlyQuery(e,cls);
			DebugUtil.bindQuery(e, rq);
			cacheOfQuery.put(cls, rq);
			return rq;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public ReadOnlyQuery(T ins,ITableMetadata clz){
		this.type=clz;
		this.instance=ins;
	}

	@SuppressWarnings("unchecked")
	public List<Condition> getConditions() {
		return Collections.EMPTY_LIST;
	}

	public EntityMappingProvider getSelectItems() {
		throw new UnsupportedOperationException();
	}

	public void setSelectItems(Selects select) {
		throw new UnsupportedOperationException();
	}

	public SqlContext prepare() {
		SqlContext context = new SqlContext("t", this);
		return context;
	}

	public void clearQuery() {
	}

	@SuppressWarnings("unchecked")
	public Collection<OrderField> getOrderBy() {
		return Collections.EMPTY_LIST;
	}

	public void setOrderBy(boolean asc, Field... orderby) {
		throw new UnsupportedOperationException();
	}

	public void addOrderBy(boolean asc, Field... orderby) {
		throw new UnsupportedOperationException();
	}

	public void setAttribute(String key, Object value) {
		throw new UnsupportedOperationException();
	}

	public Object getAttribute(String key) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getAttributes() {
		return Collections.EMPTY_MAP;
	}

	public void setMaxResult(int size) {
	}

	public void setFetchSize(int size) {
	}

	public void setQueryTimeout(int timout) {
	}

	public Query<T> addExtendQuery(Query<?> querys) {
		throw new UnsupportedOperationException();
	}
	public Query<T> setAllRecordsCondition() {
		throw new UnsupportedOperationException();
	}

	public Query<T> addCondition(Field field, Object value) {
		throw new UnsupportedOperationException();
	}

	public Query<T> addCondition(Field field, Operator oper, Object value) {
		throw new UnsupportedOperationException();
	}

	public Query<T> addCondition(IConditionField field) {
		throw new UnsupportedOperationException();
	}

	public Query<T> addCondition(Condition condition) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	public Map<Reference, List<Condition>> getFilterCondition() {
		return Collections.EMPTY_MAP;
	}

	public Query<?>[] getOtherQueryProvider() {
		return EMPTY_Q;
	}

	public void setCustomTableName(String name) {
		throw new UnsupportedOperationException();
	}

	public Query<T> addCascadeCondition(Condition cond) {
		throw new UnsupportedOperationException();
	}

	public Query<T> addCascadeCondition(String refName, Condition... conds) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return type.getName()+"[Empty]";
	}

	public boolean isCascadeViaOuterJoin() {
		return true;
	}

	public void setCascadeViaOuterJoin(boolean cascadeViaOuterJoin) {
	}


	public void setCascade(boolean cascade) {
		if(t==null){
			t=new Transformer(type);
		}
		t.setLoadVsMany(cascade);
		t.setLoadVsOne(cascade);
	}

	public Transformer getResultTransformer() {
		if (t == null) {
			t = new Transformer(type);
		}
		return t;
	}
	
	@Override
	public boolean isAll() {
		return true;
	}
}
