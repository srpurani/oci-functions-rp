package com.example.fn;

import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.core.VirtualNetworkClient;
import com.oracle.bmc.core.model.Vcn;
import com.oracle.bmc.core.requests.GetVcnRequest;
import com.oracle.bmc.identity.IdentityClient;
import com.oracle.bmc.identity.model.User;
import com.oracle.bmc.identity.requests.GetUserRequest;

public class HelloFunction {
    // Tries to fetch user/vcn based on input parameter.
    // The request will fail if the function does not have necessary permissions.
    public String handleRequest(String input) {

        ResourcePrincipalAuthenticationDetailsProvider p = ResourcePrincipalAuthenticationDetailsProvider.builder().build();
        User user = null;
        Vcn vcn = null;
        String error = null;

        try {
            if (input != null && input.contains("vcn")) {
                VirtualNetworkClient virtualNetworkClient = VirtualNetworkClient.builder()
                        .build(p);
                vcn = virtualNetworkClient.getVcn(GetVcnRequest.builder()
                        .vcnId(input.replaceFirst("\n", ""))
                        .build()).getVcn();
            } else if (input != null && input.contains("user")) {
                IdentityClient identityClient = IdentityClient.builder()
                        .build(p);
                user = identityClient.getUser(GetUserRequest.builder()
                        .userId(input.replaceFirst("\n", ""))
                        .build()).getUser();
            } else {
                error = "Please pass a valid entity OCID VCN/USER";
            }
        } catch (Exception e) {
            error = "Failed to process input: " + input + " due to error: " + e.getMessage();
        }

        return user != null ? user.toString() : (vcn != null ? vcn.toString() : error);
    }
}