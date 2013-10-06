/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package alpha.domain.spring.example.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import alpha.domain.spring.example.dto.CheckBoxModel;
import alpha.domain.spring.example.dto.OrderDtlDto;
import alpha.domain.spring.example.dto.OrderDto;

/**
 * function.
 *
 * @author yoshida-n
 * @version	1.0
 */
@Controller
@RequestMapping("/order/")
@SessionAttributes("orderDto")
public class OrderController {
	
	@RequestMapping(value="redirect", method=RequestMethod.GET)
	public String redirect(){	
		return "index";
	}

	/**
	 * 初期処理
	 * @param orderNo 注文番号
	 * @return
	 */
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index(Model model,SessionStatus status){
		OrderDto dto = new OrderDto();
		dto.setOrderNo(1L);
		dto.setCustomerCd("cstmrCd");
		dto.getVersionMap().put(1L, "one");
		dto.getVersionMap().put(2L, "two");
		dto.getVersionMap().put(3L, "three");
		model.addAttribute("orderDto",dto);
		return "redirect:redirect.do";
	}
	
	/**
	 * 検索する
	 * @param orderNo 注文番号
	 * @return
	 */
	@RequestMapping(value="order", method=RequestMethod.POST ,params="search")
	public String search(@Valid OrderDto orderDto, BindingResult result,RedirectAttributes attr){
		if(result.hasErrors()){
			//リダイレクトしてエラーを表示する場合にはFlashスコープに設定する必要がある
			attr.addFlashAttribute("org.springframework.validation.BindingResult.orderDto", result);
			return "redirect:redirect.do";
		}
		orderDto.setViewStatus("1");	
		
		List<OrderDtlDto> list = new ArrayList<OrderDtlDto>();
		OrderDtlDto d1 = new OrderDtlDto();
		d1.setItemCode("item1");
		d1.setCount(2L);
		CheckBoxModel one = new CheckBoxModel();
		one.setItems("one");
		CheckBoxModel two = new CheckBoxModel();
		two.setItems("two");
		d1.getCheckBoxes().add(one);
		d1.getCheckBoxes().add(two);
		
		OrderDtlDto d2 = new OrderDtlDto();
		d2.setItemCode("item1");
		d2.setCount(2L);
		CheckBoxModel done = new CheckBoxModel();
		done.setItems("one");
		CheckBoxModel dtwo = new CheckBoxModel();
		dtwo.setItems("two");
		d2.getCheckBoxes().add(done);
		d2.getCheckBoxes().add(dtwo);
		
		OrderDtlDto d3 = new OrderDtlDto();
		d3.setItemCode("item1");
		d3.setCount(2L);
		CheckBoxModel ddone = new CheckBoxModel();
		ddone.setItems("one");
		CheckBoxModel ddtwo = new CheckBoxModel();
		ddtwo.setItems("two");
		d3.getCheckBoxes().add(ddone);
		d3.getCheckBoxes().add(ddtwo);
		
		list.add(d1);
		list.add(d2);
		list.add(d3);
		orderDto.setDetailDto(list);
		return "redirect:redirect.do";
	}
	
	/**
	 * 更新する
	 * @param dto DTO
	 * @param result 結果
	 * @return
	 */
	@RequestMapping(value="order", method=RequestMethod.POST ,params="update")
	public String create(@Valid OrderDto dto, BindingResult result,SessionStatus status,RedirectAttributes attr){		
		//完了としてセッションから破棄する
		if(result.hasErrors()){
			//リダイレクトしてエラーを表示する場合にはFlashスコープに設定する必要がある
			attr.addFlashAttribute("org.springframework.validation.BindingResult.orderDto", result);
			return "redirect:redirect.do";
		}else{
			status.setComplete();
			return "redirect:index.do";
		}
	
	}
	
	/**
	 * 出力する
	 * @param dto DTO
	 * @param result 結果
	 * @return
	 */
	@RequestMapping(value="order", method=RequestMethod.POST ,params="output")
	public String output(@Valid OrderDto dto, BindingResult result){
		return "redirect:redirect.do";
	}
}
