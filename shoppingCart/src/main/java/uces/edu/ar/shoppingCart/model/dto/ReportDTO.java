package uces.edu.ar.shoppingCart.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReportDTO {
	private Long id;
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private LocalDateTime processedDateTime;
	private BigDecimal profit;
	private Integer totalCartsFailed;
	private Integer totalCartsProcessed;
	private Set<ProductDTO> withoutStockProducts = new HashSet<>();
	
	public ReportDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getProcessedDateTime() {
		return processedDateTime;
	}

	public void setProcessedDateTime(LocalDateTime processedDateTime) {
		this.processedDateTime = processedDateTime;
	}

	public BigDecimal getProfit() {
		return this.profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public Integer getTotalCartsFailed() {
		return totalCartsFailed;
	}

	public void setTotalCartsFailed(Integer totalCartsFailed) {
		this.totalCartsFailed = totalCartsFailed;
	}

	public Integer getTotalCartsProcessed() {
		return totalCartsProcessed;
	}

	public void setTotalCartsProcessed(Integer totalCartsProcessed) {
		this.totalCartsProcessed = totalCartsProcessed;
	}

	public Set<ProductDTO> getWithoutStockProducts() {
		return withoutStockProducts;
	}

	public void setWithoutStockProducts(Set<ProductDTO> withoutStockProducts) {
		this.withoutStockProducts = withoutStockProducts;
	}	
}
