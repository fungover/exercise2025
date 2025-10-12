package org.example.pet;

import jakarta.validation.constraints.Size;
import org.example.validation.ValidAmount;

public class FeedRequest {
	@ValidAmount
	public int amount;

	public FeedRequest() {
	}

	public FeedRequest(@ValidAmount int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(@ValidAmount int amount) {
		this.amount = amount;
	}
}
