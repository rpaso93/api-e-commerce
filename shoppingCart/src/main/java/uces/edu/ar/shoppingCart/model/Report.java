package uces.edu.ar.shoppingCart.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Report {
	private @Id @GeneratedValue Long id;
	private LocalDateTime processedDateTime;
	private BigDecimal profit = new BigDecimal(0);
	private Integer totalCartsFailed = 0;
	private Integer totalCartsProcessed = 0;
	@OneToMany
	private Set<Product> Products = new HashSet<>();
	
	public Report() {
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
		return profit;
	}
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	public void addToProfit(BigDecimal cartValue) {
		this.profit = this.profit.add(cartValue);
	}
	public Integer getTotalCartsFailed() {
		return totalCartsFailed;
	}
	public void setTotalCartsFailed(Integer totalCartsFailed) {
		this.totalCartsFailed = totalCartsFailed;
	}
	public void addCartsFailed() {
		this.totalCartsFailed++;
	}
	
	public Integer getTotalCartsProcessed() {
		return totalCartsProcessed;
	}
	public void setTotalCartsProcessed(Integer totalCartsProcessed) {
		this.totalCartsProcessed = totalCartsProcessed;
	}
	public void addCartsProcessed() {
		this.totalCartsProcessed++;
	}
	public Set<Product> getProducts() {
		return Products;
	}
	public void setProducts(Set<Product> products) {
		Products = products;
	}
	public void addProducts(Product product) {
		Products.add(product);
	}
}
