/*
 Copyright (c) 2021 Mastercard

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.mastercard.dis.mids.reference.service.sas;

import com.mastercard.dis.mids.reference.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultSasAccessTokenService implements SasAccessTokenService {

    private final SasAccessTokenApi sasAccessTokenApi;
    private final ExceptionUtil exceptionUtil;

    @Autowired
    public DefaultSasAccessTokenService(ApiClient apiClient, ExceptionUtil exceptionUtil) {
        sasAccessTokenApi = new SasAccessTokenApi(apiClient);
        sasAccessTokenApi.getApiClient().addDefaultHeader("Content-Type",  "application/x-www-form-urlencoded");
        this.exceptionUtil = exceptionUtil;
    }

    @Override
    public SasAccessTokenResponseDTO sasAccessTokenResponse(SasAccessTokenRequestDTO sasAccessTokenRequestDTO) {
        try {
            return sasAccessTokenApi.createSasAccessToken(sasAccessTokenRequestDTO);
        } catch (ApiException apiException) {
            throw exceptionUtil.logAndConvertToServiceException(apiException);
        }
    }
}