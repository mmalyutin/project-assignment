package com.project.api.controllers.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.api.BaseIntegrationTest;
import com.project.api.constants.AppHeaders;
import com.project.api.services.ApiKeyService;
import com.project.api.services.CompanyService;
import com.project.api.testutils.builders.CompanyBuilder;
import com.project.api.testutils.builders.ApiKeyBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AuthorizationCustomerControllerIT extends BaseIntegrationTest {

  private static final String URL = CustomerController.BASE_URL;

  @Autowired private ApiKeyService apiKeyService;
  @Autowired private CompanyService companyService;

  @BeforeAll
  void init() {}

  @Test
  void return_401_IfApikeyIsNotFound() throws Exception {
    this.mockMvc
        .perform(get(URL).header(AppHeaders.API_KEY_HEADER, random()))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void return_403_IfApikeyCompanyHasWrongRole() throws Exception {
    final var company = this.companyService.create(CompanyBuilder.company());
    final var apiKey = this.apiKeyService.create(ApiKeyBuilder.apiKey(company));
    this.mockMvc
        .perform(get(URL).header(AppHeaders.API_KEY_HEADER, apiKey.getKey()))
        .andExpect(status().isForbidden());

    final var management = this.companyService.create(CompanyBuilder.management());
    final var apiKeyManagement = this.apiKeyService.create(ApiKeyBuilder.apiKey(management));
    this.mockMvc
        .perform(get(URL).header(AppHeaders.API_KEY_HEADER, apiKeyManagement.getKey()))
        .andExpect(status().isForbidden());
  }

  @Test
  void return_401_IfApikeyIsDisabled() throws Exception {
    final var customer = this.companyService.create(CompanyBuilder.customer());
    final var apiKey = this.apiKeyService.create(ApiKeyBuilder.apiKey(customer));
    this.apiKeyService.delete(apiKey.getId());
    this.mockMvc
        .perform(get(URL).header(AppHeaders.API_KEY_HEADER, apiKey.getKey()))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void return_200() throws Exception {
    final var customer = this.companyService.create(CompanyBuilder.customer());
    final var apiKey = this.apiKeyService.create(ApiKeyBuilder.apiKey(customer));
    this.mockMvc
        .perform(get(URL).header(AppHeaders.API_KEY_HEADER, apiKey.getKey()))
        .andExpect(status().isOk());
  }
}