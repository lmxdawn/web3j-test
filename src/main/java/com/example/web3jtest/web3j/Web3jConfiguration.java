package com.example.web3jtest.web3j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.geth.Geth;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfiguration {
    /**
     * web3j 全局访问类，通过 Bean 注入，调用时用 @Resource 方式
     * @return
     */
    @Bean
    public Geth web3jObject() {
        return Geth.build(new HttpService("http://localhost:8545/"));  // FIXME: Enter your Infura token here;
    }
}
