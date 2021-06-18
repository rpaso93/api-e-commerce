package uces.edu.ar.shoppingCart.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uces.edu.ar.shoppingCart.model.Cart;
import uces.edu.ar.shoppingCart.model.Report;
import uces.edu.ar.shoppingCart.model.dto.CartDTO;
import uces.edu.ar.shoppingCart.model.dto.ReportDTO;
import uces.edu.ar.shoppingCart.repository.CartRepository;
import uces.edu.ar.shoppingCart.repository.ProductsRepository;
import uces.edu.ar.shoppingCart.repository.ReportRepository;
import uces.edu.ar.shoppingCart.service.BatchService;
import uces.edu.ar.shoppingCart.utils.CartUtils;
import uces.edu.ar.shoppingCart.utils.BatchUtils;

@Service
public class BatchServiceImpl implements BatchService {

	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private ProductsRepository productRepo;
	@Autowired
	private ReportRepository reportRepo;

	@Override
	public List<CartDTO> getProcessedCarts() {

		List<Cart> processedCarts = cartRepo.findAllByStatus("PROCESSED");
		BatchUtils.checkCartsLenght(processedCarts);
		List<CartDTO> processedCartsDTO = CartUtils.cartsToCartsDTO(processedCarts);

		return processedCartsDTO;
	}

	@Override
	public List<CartDTO> getProcessedCarts(String from, String to) {
		BatchUtils.testDates(from, to);
		List<Cart> processedCarts = findCartsBetween(from, to);
		BatchUtils.checkCartsLenght(processedCarts);
		List<CartDTO> processedCartsDTO = CartUtils.cartsToCartsDTO(processedCarts);

		return processedCartsDTO;
	}

	@Override
	public List<CartDTO> getProcessedCartsFrom(String from) {
		BatchUtils.testFrom(from);
		List<Cart> processedCarts = findCartsAfter(from);
		BatchUtils.checkCartsLenght(processedCarts);
		List<CartDTO> processedCartsDTO = CartUtils.cartsToCartsDTO(processedCarts);

		return processedCartsDTO;
	}

	@Override
	public List<CartDTO> getProcessedCartsTo(String to) {
		BatchUtils.testTo(to);
		List<Cart> processedCarts = findCartsBefore(to);
		BatchUtils.checkCartsLenght(processedCarts);
		List<CartDTO> processedCartsDTO = CartUtils.cartsToCartsDTO(processedCarts);

		return processedCartsDTO;
	}

	@Override
	public ReportDTO processCarts() {
		List<Cart> readyCarts = cartRepo.findAllByStatusOrderByCheckoutDateAsc("READY");

		BatchUtils.checkReadyCarts(readyCarts);
		Report reporte = generateReport(readyCarts);
		reportRepo.save(reporte);
		ReportDTO reporteDto = BatchUtils.reportToDto(reporte);
		return reporteDto;
	}

	private Report generateReport(List<Cart> readyCarts) {
		Report newReport = new Report();
		readyCarts.forEach(rCart -> {
			List<Boolean> results = new ArrayList<>(rCart.getPedidos().size());

			rCart.getPedidos().parallelStream().forEach(pedido -> {
				if (pedido.getQuantity() <= pedido.getProduct().getStock()) {
					results.add(true);
				} else {
					results.add(false);
					newReport.addProducts(pedido.getProduct());
				}
			});
			setCartStatus(newReport, rCart, results);
			newReport.setProcessedDateTime(LocalDateTime.now());
		});
		return newReport;
	}

	private void setCartStatus(Report newReport, Cart rCart, List<Boolean> results) {
		if (results.indexOf(false) == -1) {
			rCart.getPedidos().parallelStream().forEach(pedido -> {
				pedido.getProduct().subtractToStock(pedido.getQuantity());
				productRepo.save(pedido.getProduct());
			});
			rCart.setStatus("PROCESSED");
			newReport.addToProfit(rCart.getTotal());
			newReport.addCartsProcessed();
			cartRepo.save(rCart);
		} else {
			rCart.setStatus("FAILED");
			newReport.addCartsFailed();
			cartRepo.save(rCart);
		}
	}

	private List<Cart> findCartsBefore(String to) {
		return cartRepo.findAllByStatusAndCheckoutDateBefore("PROCESSED", LocalDateTime.parse(to));
	}

	private List<Cart> findCartsAfter(String from) {
		return cartRepo.findAllByStatusAndCheckoutDateAfter("PROCESSED", LocalDateTime.parse(from));
	}

	private List<Cart> findCartsBetween(String from, String to) {
		return cartRepo.findAllByStatusAndCheckoutDateBetween("PROCESSED", LocalDateTime.parse(from),
				LocalDateTime.parse(to));
	}
}
