package uces.edu.ar.shoppingCart.service;

import uces.edu.ar.shoppingCart.model.dto.UserRecoveryDTO;

public interface EmailService {

	void sendRecoveryMessage(UserRecoveryDTO recovery);

}
