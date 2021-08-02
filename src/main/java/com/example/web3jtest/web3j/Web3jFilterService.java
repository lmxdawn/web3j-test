package com.example.web3jtest.web3j;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.geth.Geth;

import javax.annotation.Resource;

/**
 * 过滤器
 */
@Service
@Slf4j
public class Web3jFilterService {

    @Resource
    private Geth web3j;

    /**
     * 监听每一笔交易
     */
    public void startTransactionListen() {
        web3j.transactionFlowable().subscribe(tx -> {
            String txHash = tx.getHash();
            String fromAddress = tx.getFrom();
            String toAddress = tx.getTo();
            log.info("*******************************一笔新交易*******************************");
            log.info("txHash：" + txHash);
            log.info("fromAddress：" + fromAddress);
            log.info("toAddress：" + toAddress);
            log.info("-------------------------------打印结束了-------------------------------");
        });
    }

}
