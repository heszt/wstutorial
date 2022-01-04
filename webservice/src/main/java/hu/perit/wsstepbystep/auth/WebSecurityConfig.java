/*
 * Copyright 2020-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.perit.wsstepbystep.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import hu.perit.spvitamin.spring.security.auth.SimpleHttpSecurityBuilder;
import hu.perit.wsstepbystep.rest.api.BookApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * #know-how:simple-httpsecurity-builder
 *
 * @author Peter Nagy
 */

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig
{
    /*
     * ============== Order(1) =========================================================================================
     */
    @Configuration
    @Order(1)
    @RequiredArgsConstructor
    public static class Order1 extends WebSecurityConfigurerAdapter
    {

        //private static final Logger log = org.slf4j.LoggerFactory.getLogger(Order1.class);
        /**
         * This is a global configuration, will be applied to all oder configurer adapters
         *
         * @param auth
         * @throws Exception
         */
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
        {

            	PasswordEncoder passwordEncoder = getApplicationContext().getBean(PasswordEncoder.class);
            
            	//Admin user
                auth.inMemoryAuthentication() //
                		.withUser("admin") //
                        .password(passwordEncoder.encode("admin")) //
                        .authorities("ROLE_" + Role.ADMIN.name(), "ROLE_" + Role.PUBLIC.name());
                
                //public user
                auth.inMemoryAuthentication() //
                .withUser("user") //
                .password(passwordEncoder.encode("user")) //
                .authorities("ROLE_" + Role.PUBLIC.name());
                
                
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
            SimpleHttpSecurityBuilder.newInstance(http) //
                    .scope(BookApi.BASE_URL_BOOKS + "/**") //
                    .basicAuth(Role.ADMIN.name());

        }
    }
}
