package com.anaguchijunya.web;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.anaguchijunya.domain.Customer;
import com.anaguchijunya.service.CustomerService;

@Controller
@RequestMapping("customers")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@ModelAttribute
	CustomerForm setupForm() {
		return new CustomerForm();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	String list(Model model) {
		List<Customer> customers = customerService.findAll();
		model.addAttribute("customers", customers);
		return "customers/list";
	}
	
	@RequestMapping(value="create", method = RequestMethod.POST)
	String create(@Validated CustomerForm form, BindingResult result, Model model) {
		
		// 入力チェックでのエラーを確認
		if (result.hasErrors()) {
			return list(model);
		}
		
		Customer customer = new Customer();
		BeanUtils.copyProperties(form, customer);
		customerService.create(customer);
		
		return "redirect:/customers";
	}
	
	/**
	 * 編集ボタン押下時の処理
	 * @param id
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "edit", params = "form", method = RequestMethod.GET)
	String editForm(@RequestParam Integer id, CustomerForm form) {
		Customer customer = customerService.findOne(id);
		BeanUtils.copyProperties(customer, form);
		return "customers/edit";
	}
	
	/**
	 * 顧客の更新処理
	 * @param id
	 * @param form
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	String edit(@RequestParam Integer id, @Validated CustomerForm form, BindingResult result) {
		// 入力エラーがある場合は編集画面に戻る
		if (result.hasErrors()) {
			return editForm(id, form);
		}
		
		Customer customer = new Customer();
		BeanUtils.copyProperties(form, customer);
		customer.setId(id);
		customerService.update(customer);
		return "redirect:/customers";
	}
	
	/**
	 * Topへ戻る
	 * @return
	 */
	@RequestMapping(value = "edit", params = "goToTop")
	String goToTop() {
		return "redirect:/customers";
	}
}