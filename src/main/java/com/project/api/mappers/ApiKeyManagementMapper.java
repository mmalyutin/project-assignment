package com.project.api.mappers;

import com.project.api.entities.ApiKey;
import com.project.api.mappers.base.BaseManagementMapper;
import com.project.api.requests.management.apikey.CreateApiKeyManagementRequest;
import com.project.api.requests.management.apikey.PatchApiKeyManagementRequest;
import com.project.api.requests.management.apikey.UpdateApiKeyManagementRequest;
import com.project.api.responses.management.ApikeyManagementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiKeyManagementMapper
    extends BaseManagementMapper<
            ApiKey,
            CreateApiKeyManagementRequest,
            UpdateApiKeyManagementRequest,
            PatchApiKeyManagementRequest,
            ApikeyManagementResponse> {}
