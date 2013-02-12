/**
 * Copyright 2011 the original author
 */
package alpha.domain.spring.example.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import alpha.domain.spring.example.dto.OrderDto;
import alpha.domain.spring.example.service.OrderService;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Controller
@RequestMapping("/order/")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@ModelAttribute("orderDto")
	public OrderDto newRequest(
			@RequestParam(required=false,value="user.id") String userId
			){
		OrderDto dto = new OrderDto();
		return dto;
	}
	
	/**
	 * リクエストパラメータをモデルに変換するたびに呼ばれる
	 * @param binder
	 */
	@InitBinder("orderDto")
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, "order.orderDt",new CustomDateEditor(new SimpleDateFormat("yyyyMMdd"),true));
	}
	
	/**
	 * 初期処理
	 * @param orderNo 注文番号
	 * @return
	 */
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index(OrderDto dto){		
		return "index";
	}
	
	/**
	 * 検索する
	 * @param orderNo 注文番号
	 * @return
	 */
	@RequestMapping(value="search", method=RequestMethod.POST)
	public String search(OrderDto dto, BindingResult result){
		OrderDto response = orderService.searchOrder(dto.getOrderNo());
		dto.setDetailDto(response.getDetailDto());
		return "index";
	}
	
	/**
	 * 更新する
	 * @param dto DTO
	 * @param result 結果
	 * @return
	 */
	@RequestMapping(value="create", method=RequestMethod.POST)
	public String create(OrderDto dto, BindingResult result){
		orderService.createOrder(dto);
		return "index";
	}
}
