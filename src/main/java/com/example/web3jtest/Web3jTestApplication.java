package com.example.web3jtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

@SpringBootApplication
@Slf4j
public class Web3jTestApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Web3jTestApplication.class, args);
        // new Web3jTestApplication().run();
    }

    private void run() throws Exception {
        // We start by creating a new web3j instance to connect to remote nodes on the network.
        // Note: if using web3j Android, use Web3jFactory.build(...
        // http://localhost:8545/
        Web3j web3j = Web3j.build(new HttpService());  // FIXME: Enter your Infura token here;
        log.info("Connected to Ethereum client version: "
                + web3j.web3ClientVersion().send().getWeb3ClientVersion());

//        // We then need to load our Ethereum wallet file
//        // FIXME: Generate a new wallet file using the web3j command line tools https://docs.web3j.io/command_line.html
//        Credentials credentials =
//                WalletUtils.loadCredentials(
//                        "<password>",
//                        "/path/to/<walletfile>");
//        log.info("Credentials loaded");
//
//        // FIXME: Request some Ether for the Rinkeby test network at https://www.rinkeby.io/#faucet
//        log.info("Sending 1 Wei ("
//                + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
//        TransactionReceipt transferReceipt = Transfer.sendFunds(
//                web3j, credentials,
//                "0x19e03255f667bdfd50a32722df860b1eeaf4d635",  // you can put any address here
//                BigDecimal.ONE, Convert.Unit.WEI)  // 1 wei = 10^-18 Ether
//                .send();
//        log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
//                + transferReceipt.getTransactionHash());
    }

}
