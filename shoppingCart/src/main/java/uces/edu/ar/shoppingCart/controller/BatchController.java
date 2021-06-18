package uces.edu.ar.shoppingCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uces.edu.ar.shoppingCart.model.dto.CartDTO;
import uces.edu.ar.shoppingCart.model.dto.ReportDTO;
import uces.edu.ar.shoppingCart.service.BatchService;

@RestController
@Validated
public class BatchController {
	@Autowired
	private BatchService service;
	
	@GetMapping(value = "/batch/processCarts")
	public ResponseEntity<List<CartDTO>> getProcessedCarts() {
		return new ResponseEntity<>(service.getProcessedCarts(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/batch/processCarts", params = "from")
	public ResponseEntity<List<CartDTO>> getProcessedCartsFrom(
			@RequestParam(value = "from") String from) {
		return new ResponseEntity<>(service.getProcessedCartsFrom(from), HttpStatus.OK);
	}
	
	@GetMapping(value = "/batch/processCarts", params = "to")
	public ResponseEntity<List<CartDTO>> getProcessedCartsTo(
			@RequestParam(value = "to") String to) {
		return new ResponseEntity<>(service.getProcessedCartsTo(to), HttpStatus.OK);
	}
	
	@GetMapping(value = "/batch/processCarts", params = {"from","to"})
	public ResponseEntity<List<CartDTO>> getProcessedCarts(
			@RequestParam(value = "from") String from,
			@RequestParam(value = "to") String to) {
		return new ResponseEntity<>(service.getProcessedCarts(from, to), HttpStatus.OK);
	}
	
	@PostMapping(value = "/batch/processCarts")
	public ResponseEntity<ReportDTO> processCarts(){
		return new ResponseEntity<>(service.processCarts(), HttpStatus.CREATED);		
	}
	
}
