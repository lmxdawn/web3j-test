package com.example.web3jtest.web3j;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigInteger;

@Component
@Slf4j
public class Web3jService {

    @Resource
    private Web3j web3j;

    @Resource
    private Web3jFilterService web3jFilterService;

    @Resource
    private Web3jTransactionService web3jTransactionService;

    /**
     * 启动 web3j server
     */
    @PostConstruct
    public void start() throws Exception {

        log.info("Connected to Ethereum client version: "
                + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        log.info("Connected to Ethereum version: "
                + web3j.netVersion().send().getNetVersion());

        // 监听交易
        web3jFilterService.startTransactionListen();

        String password = "123";
        String filePath = "D:\\Geth\\data\\keystore";
        // 创建钱包文件
        // String fileName = WalletUtils.generateNewWalletFile(
        //         password,
        //         new File(filePath));
        //
        // log.info("Create Wallet File： " + fileName);

        // We then need to load our Ethereum wallet file
        // FIXME: Generate a new wallet file using the web3j command line tools https://docs.web3j.io/command_line.html


        //  读取钱包文件
        // String walletFilePath = filePath + "\\" + fileName;
        String walletFilePath = filePath + "\\UTC--2021-07-20T00-48-18.412371200Z--de46b693e94b9186fb2721d110acad6a772c2de5";
        Credentials credentials =
                WalletUtils.loadCredentials(
                        password,
                        walletFilePath);
        log.info("Credentials loaded 0x" + credentials.getEcKeyPair().getPrivateKey().toString(16));

        String toAddress = "0x376e9f2563b8c8946009894b38b3aa15ce545394";

        // 裸交易
        BigInteger gasPrice = BigInteger.valueOf(1000);
        BigInteger gasLimit = BigInteger.valueOf(21000);
        BigInteger value = Convert.toWei("1", Convert.Unit.ETHER).toBigInteger();
        String data = "";

        // 通过私钥转账
        String privateKey = "0x42c6618b306bc09cd08da7c89e3e802c9c91eef33fc24f7b2b4c6bfe51306138";
        EthSendTransaction ethSendTransaction = web3jTransactionService.transfer(credentials.getAddress(), toAddress, value, privateKey, gasPrice, gasLimit, data);
        log.info("transactionHash: " + ethSendTransaction.getTransactionHash());

        // 通过keystore文件转账
        // EthSendTransaction ethSendTransaction2 = web3jTransactionService.transferByKeystoreFile(walletFilePath, password, toAddress, value, gasPrice, gasLimit, data);
        // log.info("transactionHash: " + ethSendTransaction2.getTransactionHash());

        // 获取余额


        // // Now lets deploy a smart contract
        // log.info("Deploying smart contract");
        // ContractGasProvider contractGasProvider = new DefaultGasProvider();
        // Greeter contract = Greeter.deploy(
        //         web3j,
        //         credentials,
        //         contractGasProvider,
        //         "test"
        // ).send();
        //
        // String contractAddress = contract.getContractAddress();
        // log.info("Smart contract deployed to address " + contractAddress);
        // log.info("View contract at https://rinkeby.etherscan.io/address/" + contractAddress);
        //
        // log.info("Value stored in remote smart contract: " + contract.greet().send());
        //
        // // Lets modify the value in our smart contract
        // TransactionReceipt transactionReceipt = contract.newGreeting("Well hello again").send();
        //
        // log.info("New value stored in remote smart contract: " + contract.greet().send());
        //
        // // Events enable us to log specific events happening during the execution of our smart
        // // contract to the blockchain. Index events cannot be logged in their entirety.
        // // For Strings and arrays, the hash of values is provided, not the original value.
        // // For further information, refer to https://docs.web3j.io/filters.html#filters-and-events
        // for (Greeter.ModifiedEventResponse event : contract.getModifiedEvents(transactionReceipt)) {
        //     log.info("Modify event fired, previous value: " + event.oldGreeting
        //             + ", new value: " + event.newGreeting);
        //     log.info("Indexed event previous value: " + Numeric.toHexString(event.oldGreetingIdx)
        //             + ", new value: " + Numeric.toHexString(event.newGreetingIdx));
        // }

    }

}
