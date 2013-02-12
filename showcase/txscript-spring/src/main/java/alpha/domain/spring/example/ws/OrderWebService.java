/**
 * Copyright 2011 the original author
 */
package alpha.domain.spring.example.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.coder.alpha.framework.registry.ServiceLocator;

import alpha.domain.spring.example.dto.OrderDto;
import alpha.domain.spring.example.service.OrderService;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Path("/order")
@Produces(MediaType.TEXT_XML)
@Consumes(MediaType.TEXT_XML)
public class OrderWebService {

	/**
	 * 一覧検索
	 * @param dto
	 * @return
	 */
	@POST
	@Path("/search")
	public OrderDto searchOrder(OrderDto dto){
		OrderService service = ServiceLocator.getService("orderService");
		return service.searchOrder(dto.getOrderNo());
	}
	
	/**
	 * 登録/更新
	 * @param dto DTO
	 */
	@POST
	@Path("/create")
	public OrderDto createOrder(OrderDto dto){
		OrderService service = ServiceLocator.getService("orderService");
		service.createOrder(dto);
		return dto;
	}
}
