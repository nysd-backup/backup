package alpha.query.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import alpha.query.criteria.strategy.DataMapper;



/**
 * The Query Object.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class CriteriaModifyQuery<T> {
	
	/** the InternalOrmUpdate */
	private final DataMapper internalQuery;
	
	/** the condition */
	private CriteriaModifyingConditions<T> condition = null;
	
	/**
	 * @param entityClass the entity class
	 */
	protected CriteriaModifyQuery(Class<T> entityClass,DataMapper internalQuery,EntityManager em){
		this.condition = new CriteriaModifyingConditions<T>(entityClass);
		this.internalQuery = internalQuery;
		this.condition.setEntityManager(em);
	}

	/**
	 * Adds the JPA hint.
	 * 
	 * @param <T> the type
	 * @param arg0 the key of the hint
	 * @param arg1 the hint value
	 * @return self
	 */
	public CriteriaModifyQuery<T> setHint(String arg0 , Object arg1){
		condition.getHints().put(arg0, arg1);
		return this;
	}
	
	/**
	 * Updates the data.
	 * 
	 * @return the updated count
	 */
	public int update(){
		return internalQuery.update(condition);
	}
	
	/**
	 * Adds 'is null'
	 * 
	 * @param column the column to add to
	 * @return self
	 */
	public CriteriaModifyQuery<T> isNull(Metadata<T, ?> column){
		return addCriteria(column.name(), ComparingOperand.IsNull,null);
	}
	
	/**
	 * Adds 'is not null'
	 * 
	 * @param column the column to add to
	 * @return self
	 */
	public CriteriaModifyQuery<T> isNotNull(Metadata<T, ?> column){
		return addCriteria(column.name(), ComparingOperand.IsNotNull,null);
	}
	
	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> eq(Metadata<T, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.Equal,value);
	}
	
	/**
	 * Adds '!='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> ne(Metadata<T, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.NotEqual,value);
	}

	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> gt(Metadata<T, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.GreaterThan,value);
	}

	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> lt(Metadata<T, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.LessThan,value);
	}

	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> gtEq(Metadata<T, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.GreaterEqual,value);
	}

	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> ltEq(Metadata<T, V> column, V value){
		return addCriteria(column.name(), ComparingOperand.LessEqual,value);
	}

	/**
	 * Adds 'between'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param from the from-value to be added
	 * @param to the to-value to be added 
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> between(Metadata<T, V> column, V from , V to){
		List<V> values = new ArrayList<V>();
		values.add(from);
		values.add(to);
		addCriteria(column.name(),ComparingOperand.Between,values);
		return this;
	}

	/**
	 * Adds 'IN' or 'CONTAINS'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> contains(Metadata<T, V> column, List<V> value){
		addCriteria(column.name(),ComparingOperand.IN,value);
		return this;
	}
	
	/**
	 * Adds 'LIKE'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> like(Metadata<T, V> column, V value){
		addCriteria(column.name(),ComparingOperand.LIKE,value);
		return this;
	}
	
	/**
	 * Adds value to update.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> set(Metadata<T, V> column, V value){
		set(column.name(), value);
		return this;
	}
	
	/**
	 * Deletes the data.
	 * 
	 * @return the updated count
	 */
	public int delete(){
		return internalQuery.delete(condition);
	}

	/**
	 * Low level API for String-based object.
	 * @param column the column 
	 * @param value the value 
	 * @param operand the operand
	 * @return self
	 */
	public CriteriaModifyQuery<T> addCriteria(String column,ComparingOperand operand,Object value) {
		condition.getConditions().add(new Criteria<Object>(column,condition.getConditions().size()+1,operand,value));
		return this;
	}
	

	/**
	 * Low level API for String-based object.
	 * @param column the column 
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> set(String column , Object value){
		condition.getCurrentValues().put(column, value);
		return this;
	}
}
