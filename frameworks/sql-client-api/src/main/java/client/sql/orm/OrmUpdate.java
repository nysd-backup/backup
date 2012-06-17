package client.sql.orm;

import java.util.List;

import client.sql.orm.strategy.InternalOrmQuery;




/**
 * The ORM Updater.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class OrmUpdate<T> {
	
	/** the InternalOrmUpdate */
	private final InternalOrmQuery internalQuery;
	
	/** the condition */
	private OrmUpdateParameter<T> condition = null;
	
	/**
	 * @param entityClass the entity class
	 */
	OrmUpdate(Class<T> entityClass,InternalOrmQuery internalQuery){
		this.condition = new OrmUpdateParameter<T>(entityClass);
		this.internalQuery = internalQuery;
	}

	/**
	 * Adds the JPA hint.
	 * 
	 * @param <T> the type
	 * @param arg0 the key of the hint
	 * @param arg1 the hint value
	 * @return self
	 */
	public OrmUpdate<T> setHint(String arg0 , Object arg1){
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
	public <V> OrmUpdate<T> eq(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, WhereOperand.Equal);
	}
	
	/**
	 * Adds '='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmUpdate<T> eqFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), WhereOperand.Equal);
	}

	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmUpdate<T> gt(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, WhereOperand.GreaterThan);
	}
	
	/**
	 * Adds '>'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmUpdate<T> gtFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), WhereOperand.GreaterThan);
	}
	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmUpdate<T> lt(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, WhereOperand.LessThan);
	}
	
	/**
	 * Adds '<'.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmUpdate<T> ltFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), WhereOperand.LessThan);
	}
	
	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmUpdate<T> gtEq(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, WhereOperand.GreaterEqual);
	}
	
	/**
	 * Adds '>='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmUpdate<T> gtEqFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), WhereOperand.GreaterEqual);
	}
	
	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmUpdate<T> ltEq(Metadata<T, V> column, V value){
		return setOperand(column.name(), value, WhereOperand.LessEqual);
	}
	
	/**
	 * Adds '<='.
	 * 
	 * @param <V> the type
	 * @param column the column to add to
	 * @param value the value to be added
	 * @return self
	 */
	public <V> OrmUpdate<T> ltEqFix(Metadata<T, V> column, String value){
		return setOperand(column.name(), new FixString(value), WhereOperand.LessEqual);
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
	public <V> OrmUpdate<T> between(Metadata<T, V> column, V from , V to){
		condition.getConditions().add(new WhereCondition(column.name(),condition.getConditions().size()+1,WhereOperand.Between,from,to));
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
	public <V> OrmUpdate<T> contains(Metadata<T, V> column, List<V> value){
		condition.getConditions().add(new WhereCondition(column.name(),condition.getConditions().size()+1,WhereOperand.IN,value));
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
	public <V> OrmUpdate<T> set(Metadata<T, V> column, V value){
		condition.getCurrentValues().put(column.name(), value);
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
	public <V> OrmUpdate<T> setFix(Metadata<T, V> column, String value){
		condition.getCurrentValues().put(column.name(), new FixString(value));
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
	 * @param column the column 
	 * @param value the value 
	 * @param operand the operand
	 * @return
	 */
	private OrmUpdate<T> setOperand(String column, Object value,WhereOperand operand) {
		condition.getConditions().add(new WhereCondition(column,condition.getConditions().size()+1,operand,value));
		return this;
	}
}
