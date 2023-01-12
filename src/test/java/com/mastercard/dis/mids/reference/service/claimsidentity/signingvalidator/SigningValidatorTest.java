/*
 Copyright (c) 2023 Mastercard

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

package com.mastercard.dis.mids.reference.service.claimsidentity.signingvalidator;

import com.mastercard.dis.mids.reference.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class SigningValidatorTest {

    private static final String VALID_JWS = "eyJ4NXQjUzI1NiI6Ilg1OWF0QTRIMHlxNWx6VnlMUnl2Ym9oNmJrZHRMZHRQT002ZlR0OEFZMUkiLCJ4NWMiOlsiTUlJRVJ6Q0NBeStnQXdJQkFnSUlEek1MV200c09jQXdEUVlKS29aSWh2Y05BUUVMQlFBd2dZVXhDekFKQmdOVkJBWVRBa0pGTVJ3d0dnWURWUVFLRXhOTllYTjBaWEpEWVhKa0lGZHZjbXgzYVdSbE1TUXdJZ1lEVlFRTEV4dEhiRzlpWVd3Z1NXNW1iM0p0WVhScGIyNGdVMlZqZFhKcGRIa3hNakF3QmdOVkJBTVRLVTFoYzNSbGNrTmhjbVFnU1ZSR0lFMWxjM05oWjJWeklGTnBaMjVwYm1jZ1UzVmlJRU5CSUVjeU1CNFhEVEl4TURVd05qSXlNVGMxTmxvWERUSTFNREl4TURFMk1qazBPVm93Z2FReEN6QUpCZ05WQkFZVEFrSkZNUkV3RHdZRFZRUUhFd2hYWVhSbGNteHZiekV1TUN3R0ExVUVDaE1sVFdGemRHVnlRMkZ5WkNCSmJuUmxjbTVoZEdsdmJtRnNJRWx1WTI5eWNHOXlZWFJsWkRFWk1CY0dBMVVFQ3hNUVJHbG5hWFJoYkNCSlpHVnVkR2wwZVRFM01EVUdBMVVFQXhNdVpHbHpMVzFwWkhNdGMyVnlkbWxqWlhNdGFuZHpMWE5wWjI0dGRHVnpkQzV0WVhOMFpYSmpZWEprTG1OdmJUQ0NBU0l3RFFZSktvWklodmNOQVFFQkJRQURnZ0VQQURDQ0FRb0NnZ0VCQUx0RmZ0cFhHT1NYT1U2bXpEXC81YXJBTzhUNnphVENwQ0NtSkd2ZEZDYVhFNUhpczd5bmZ0N1dnNDlsR1dnTXdqN3pFdytLQmdCc2grOENxZ1VyUGhNa0hVaWhCcG9YbE9cL005emRUQ09XZEt1cjFOZFlGeFdzV3pHZVprcCt2Q2VtXC9lalJraUNza2kyeExCbHJsY1F3UnhPVDNIRUlsdnZtTlMwMmJ5SU5sbjJReWJaSHBwMkxpZDhkYlFVREVZU1hoeEk5cFdLcEFDZExBU3FUajhIK2VpK0xuMXBITTZtbVZNdCtueVFoMTR1K2FcLzdVa3ZlVzN3Zis4c0hFZmp6ZFVMcEFkaENMbGNnQWc5bVZuaDFPQ2lyYlN3WHVxXC9uWUs1TnA3UXR0dVV0bUhnSU1iV0llNFRyMVkybmlcL3dQRkQzek9cLzZJZjh0aXg2RDY4emNcLzM4Q0F3RUFBYU9CbVRDQmxqQTVCZ05WSFJFRU1qQXdnaTVrYVhNdGJXbGtjeTF6WlhKMmFXTmxjeTFxZDNNdGMybG5iaTEwWlhOMExtMWhjM1JsY21OaGNtUXVZMjl0TUE0R0ExVWREd0VCXC93UUVBd0lBZ0RBSkJnTlZIUk1FQWpBQU1CMEdBMVVkRGdRV0JCUjllS0xOVXYzSUVJenBydWFrTmp5M1RvdmVJREFmQmdOVkhTTUVHREFXZ0JSYlBFQlNEKzVQZ091YmF0U0cyWXRnM0g5WVZUQU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFVZXkzZmNJN05HUTlMVlNhUHY4UGsyTldsWnV0eDdNRHJGbVROK0ZZSUpZVGRRMWlLS2RyM3BUSjdocUR1NFBkcmsrT3VDUnVEODhZNDZCMDNRNUt6TUo4MDRVbU1sUG1yVll3RzUyZ29WTURWc2RIenR3cFwvUURaWW1GSE51b3d2TU5VcHozQjk4dFp6VVwva1lYWXF6a2hkS1ZzbGE0anc4RnBySXlTY01cL2l6aEVsWEp3dDI5SXd3YnBMT0NKaGNmZVFmOXZCdlh0VTcrQ3orZFBHUFZTMXVPdUNkWkI2NWdxV2dFb1FxczdwUDdtXC9VYTFqejFhTFdISmpXYStwV3duWWVUM1ZlR1pSM3JNOFUxMldiaXc5Uk53b1R2UzlUd2wybllybzI4YUpsZnRWb0RGMnM0T3djWEplSlBTMDN5aDhzd2pta0RqOUhoUmdwakI3aUJBPT0iXSwiYWxnIjoiUlMyNTYifQ.eyJjbGFpbXNBdHRyaWJ1dGVzIjp7ImFkZHJlc3MiOnsibGFzdFZlcmlmaWVkRGF0ZSI6IjIwMjItMTEtMjVUMTM6NTk6MTguNTk0WiIsImNsYWltcyI6eyJhZGRyZXNzIjoiMjIwMCBNQVNURVJDQVJEIEJMVkQsIE8gRkFMTE9OLCBNTywgNjMzNjgsIFVTQSJ9LCJhc3N1cmFuY2VMZXZlbCI6MSwiZGF0YU1hdGNoIjoiRlVMTCJ9fSwiaWQiOiI3NzIwZDVkMTc4N2QxN2E5MGU5MzVhMTRhMzYwNDE5NTI4ZTNiNWQ2MWJiZjM0Y2ZlMjFkN2M5ZWE5YzU2ZjUwIn0.LTOJSUzSviMPDlMFLz5TYh04kFzFH2Ecsb8Vh1yFBDuZjR076EuZbwzKMhSuAIBsiR8nlSDRSzIKnwobb0FLI-E3p1psslYyCbGU_1K0Ithn9zvTEExFMs5P0szgNm-j1lbWZkGR8bo6gMvaKFQ4-qoXp4cdYY30D7zLXYPkK2I2gxxJcWicCNHvl0blfPQpch9e3Af4SAdXBr41FvuwfeGBAfctsNw_1B1YoIsU-dxhDWvbJ_wOs_QvUd7dstqK_BMiTSWnuwDEJWtg_EcrHk2jy94iSqquco1eAiqUaxMeBKWiWocDPcxLjEw75YIvc1iSRKUjQCKFit01bTfqSw";
    private static final String INVALID_JWS = "invalid_jws";

    @InjectMocks
    private SigningValidator signingValidator;

    @Test
    void signingValidator_ValidJWS_ShouldReturnTrue() {
        assertTrue(signingValidator.verify(VALID_JWS));
    }

    @Test
    void signingValidator_InvalidJWS_ShouldThrowException() {
        try {
            signingValidator.verify(INVALID_JWS);
            fail();
        } catch (Exception e) {
            assertEquals(ServiceException.class, e.getClass());
            assertTrue(e.getMessage().contains("[Couldn't verify signature]"));
        }
    }
}