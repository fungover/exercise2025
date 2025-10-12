package org.example.pet;

import jakarta.validation.constraints.Size;
import org.example.validation.ValidAmount;

public class PlayRequest {
	@ValidAmount
	private int amount;

	public PlayRequest() {}

	public PlayRequest(@ValidAmount int amount) { this.amount = amount; }

	public int getAmount() {
		return amount;
	}

	public void setAmount(@ValidAmount int amount) {
		this.amount = amount;
	}
}
