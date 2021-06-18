package uces.edu.ar.shoppingCart.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PedidoPk implements Serializable {

	private static final long serialVersionUID = -7711136238713120027L;
	
	private Long cartId;
	private Long productId;
	
	public PedidoPk() {
		super();
	}
	
	public PedidoPk(Long cartId, Long productId) {
		super();
		this.cartId = cartId;
		this.productId = productId;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartId == null) ? 0 : cartId.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoPk other = (PedidoPk) obj;
		if (cartId == null) {
			if (other.cartId != null)
				return false;
		} else if (!cartId.equals(other.cartId))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}	
}
