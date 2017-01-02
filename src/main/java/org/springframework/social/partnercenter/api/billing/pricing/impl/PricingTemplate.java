package org.springframework.social.partnercenter.api.billing.pricing.impl;

import org.springframework.social.partnercenter.PartnerCenter;
import org.springframework.social.partnercenter.api.AbstractTemplate;
import org.springframework.social.partnercenter.api.billing.pricing.AzureResourcePricing;
import org.springframework.social.partnercenter.api.billing.pricing.PricingOperations;
import org.springframework.social.partnercenter.http.client.RestResource;

public class PricingTemplate extends AbstractTemplate implements PricingOperations {
	private RestResource restResource;

	public PricingTemplate(RestResource restResource, boolean isAuthorized) {
		super(isAuthorized);
		this.restResource = restResource;
	}

	@Override
	public AzureResourcePricing getAzurePricing() {
		return restResource.request()
				.get(AzureResourcePricing.class);
	}

	@Override
	public AzureResourcePricing getAzurePricing(String currency, String region) {
		return restResource.request()
				.queryParam("currency", currency)
				.queryParam("region", region)
				.get(AzureResourcePricing.class);
	}

	@Override
	protected String getProviderId() {
		return PartnerCenter.PROVIDER_ID;
	}
}