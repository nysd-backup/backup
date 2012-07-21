package client.sql.orm;

import java.util.List;

import javax.persistence.EntityManager;

import client.sql.orm.strategy.InternalOrmQuery;




/**
 * The ORM Updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class CriteriaModifyQuery<T> {
	
	/** the InternalOrmUpdate */
	private final InternalOrmQuery internalQuery;
	
	/** the condition */
	private CriteriaModifyQueryParameter<T> condition = null;
	
	/**
	 * @param entityClass the entity class
	 */
	CriteriaModifyQuery(Class<T> entityClass,InternalOrmQuery internalQuery,EntityManager em){
		this.condition = new CriteriaModifyQueryParameter<T>(entityClass);
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
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> eq(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, ComparingOperand.Equal);
	}
	
	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> eqFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), ComparingOperand.Equal);
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
		return setOperand(column.name(), value, ComparingOperand.GreaterThan);
	}
	
	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> gtFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), ComparingOperand.GreaterThan);
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
		return setOperand(column.name(), value, ComparingOperand.LessThan);
	}
	
	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> ltFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), ComparingOperand.LessThan);
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
		return setOperand(column.name(), value, ComparingOperand.GreaterEqual);
	}
	
	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> gtEqFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), ComparingOperand.GreaterEqual);
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
		return setOperand(column.name(), value, ComparingOperand.LessEqual);
	}
	
	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> ltEqFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), ComparingOperand.LessEqual);
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
		condition.getConditions().add(new ExtractionCriteria(column.name(),condition.getConditions().size()+1,ComparingOperand.Between,from,to));
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
		condition.getConditions().add(new ExtractionCriteria(column.name(),condition.getConditions().size()+1,ComparingOperand.IN,value));
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
	 * Adds value to update.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> CriteriaModifyQuery<T> setFix(Metadata<T, V> column, String value){
		set(column.name(), new FixString(value));
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
	public CriteriaModifyQuery<T> setOperand(String column, Object value,ComparingOperand operand) {
		condition.getConditions().add(new ExtractionCriteria(column,condition.getConditions().size()+1,operand,value));
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
