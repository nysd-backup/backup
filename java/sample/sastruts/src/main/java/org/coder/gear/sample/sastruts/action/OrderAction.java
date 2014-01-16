/**
 * 
 */
package org.coder.gear.sample.sastruts.action;

import javax.annotation.Resource;

import org.coder.gear.sample.sastruts.entity.Order;
import org.coder.gear.sample.sastruts.entity.OrderDetail;
import org.coder.gear.sample.sastruts.entity.Stock;
import org.coder.gear.sample.sastruts.form.OrderForm;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.jdbc.where.SimpleWhere;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * DDDのapplication層と一体にさせる方がよい。
 * 
 * @author yoshida-n
 *
 */
public class OrderAction {
	
	/** Repository(S2の世界ではServiceとRepositoryが一体になってServiceと呼んでいるようなのでService)に委譲した方がよい . */
	@Resource
	private JdbcManager jdbcManager;
	
	@Resource
	@ActionForm 
	private OrderForm orderForm;
	
	/**
	 * @return
	 */
	@Execute(validator=false)
	public String index() {
		orderForm.order = new Order();
		orderForm.order.no = 1L;
		orderForm.order.orderDetails.add(new OrderDetail());
		return "index.jsp";
	}
	
	/**
	 * @return
	 */
	@Execute(validator=false)
	public String orderOperation() {
		//在庫引き当て
		long i= 0;
		for(OrderDetail e : orderForm.order.orderDetails){
			Stock stock = jdbcManager.from(Stock.class).where(new SimpleWhere().eq("itemNo",e.itemNo)).getSingleResult();
//			if(stock != null && stock.canReserve(e.count)){
//				stock.reserve(e.count);
//			}else {
//				throw new IllegalStateException();
//			}	
			e.orderNo = orderForm.order.no;
			e.detailNo = i++;		
		}
		Order order =jdbcManager.from(Order.class).innerJoin("orderDetails").id(orderForm.order.no).getSingleResult();
		if( order != null){
			jdbcManager.deleteBatch(order.orderDetails).execute();
			jdbcManager.delete(order).execute();
		}
		jdbcManager.insert(orderForm.order).execute();
		jdbcManager.insertBatch(orderForm.order.orderDetails).execute();
		
		return "index.jsp";
	}
}
