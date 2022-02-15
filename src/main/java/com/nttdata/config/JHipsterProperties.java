package com.nttdata.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "jhipster")
public interface JHipsterProperties {
    Security security();

    Mail mail();

    interface Security {
        Authentication authentication();

        interface Authentication {
            Jwt jwt();

            interface Jwt {
                String issuer();

                long tokenValidityInSeconds();

                long tokenValidityInSecondsForRememberMe();

                PrivateKey privateKey();

                interface PrivateKey {
                    String location();
                }
            }
        }
    }

    interface Mail {
        String baseUrl();
    }
}
