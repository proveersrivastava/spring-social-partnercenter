package org.springframework.social.partnercenter.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.partnercenter.PartnerCenter;
import org.springframework.social.partnercenter.api.uri.SecurityRegion;
import org.springframework.social.partnercenter.connect.admin.PartnerCenterAdminConnection;
import org.springframework.social.partnercenter.security.PartnerCenterServiceProvider;

public class PartnerCenterConnectionFactory extends BasePartnerCenterConnectionFactory {

	public PartnerCenterConnectionFactory(String applicationId, String applicationSecret, String clientId, String tenant){
		this(PartnerCenter.PROVIDER_ID, new PartnerCenterServiceProvider(applicationId, applicationSecret, clientId, tenant), new PartnerCenterApiAdapter());
	}

	public PartnerCenterConnectionFactory(String applicationId, String applicationSecret, String clientId, String tenant, SecurityRegion region){
		this(PartnerCenter.PROVIDER_ID, new PartnerCenterServiceProvider(applicationId, applicationSecret, clientId, tenant, region), new PartnerCenterApiAdapter());
	}

	public PartnerCenterConnectionFactory(String applicationId, String applicationSecret, String clientId, String tenant, String authority, String resourceUrl, String partnerServiceApiRoot){
		this(PartnerCenter.PROVIDER_ID, new PartnerCenterServiceProvider(applicationId, applicationSecret, clientId, tenant, authority, resourceUrl, partnerServiceApiRoot), new PartnerCenterApiAdapter());
	}

	public PartnerCenterConnectionFactory(String applicationId, String applicationSecret, String clientId, String tenant, String apiVersion, SecurityRegion region){
		this(PartnerCenter.PROVIDER_ID, new PartnerCenterServiceProvider(applicationId, applicationSecret, clientId, tenant, apiVersion, region), new PartnerCenterApiAdapter());
	}

	public PartnerCenterConnectionFactory(String applicationId, String applicationSecret, String clientId, String tenant, String apiVersion, String authority, String resourceUrl, String partnerServiceApiRoot){
		this(PartnerCenter.PROVIDER_ID, new PartnerCenterServiceProvider(applicationId, applicationSecret, clientId, tenant, apiVersion, authority, resourceUrl, partnerServiceApiRoot), new PartnerCenterApiAdapter());
	}

	private PartnerCenterConnectionFactory(String providerId, PartnerCenterServiceProvider serviceProvider, ApiAdapter<PartnerCenter> apiAdapter) {
		super(providerId, serviceProvider, apiAdapter);
	}

	public boolean canConnect(){
		try {
			createConnection();
			return true;
		} catch (Exception e){
			return false;
		}
	}

	public boolean canConnect(String username, String password){
		try {
			createConnection(username, password);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public PartnerCenterConnection createConnection(){
		AccessGrant accessGrant = getAuthOperations().exchangeForAccess();
		return (PartnerCenterConnection) createConnection(accessGrant);
	}

	public PartnerCenterAdminConnection createConnection(String username, String password){
		AccessGrant accessGrant = getAuthOperations().exchangeCredentialsForAccess(username, password, new OAuth2Parameters());
		return new PartnerCenterAdminConnection(getProviderId(), extractProviderUserId(accessGrant), username, password, accessGrant.getAccessToken(),
				accessGrant.getExpireTime(), getPartnerCenterServiceProvider(), getApiAdapter());
	}

	@Override
	public Connection<PartnerCenter> createConnection(AccessGrant accessGrant) {
		return super.createConnection(accessGrant);
	}

	@Override
	public Connection<PartnerCenter> createConnection(ConnectionData data) {
		return super.createConnection(data);
	}
}
