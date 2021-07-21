package com.example.web3jtest.web3j;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Service;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Numeric;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * 交易相关类
 */
@Service
@Slf4j
public class Web3jTransactionService {

    @Resource
    private Web3j web3j;

    private static final int chainId = 1337;

    /**
     * 内部类
     */
    @Data
    static
    class EthWalletModel {
        private String address;
        private String privateKey;
        private String publicKey;

        public EthWalletModel(String address, String privateKey, String publicKey) {
            this.address = address;
            this.privateKey = privateKey;
            this.publicKey = publicKey;
        }
    }

    /**
     * 根据钱包文件和密码获取私钥
     *
     * @param walletFilePath 文件地址
     * @param password       密码
     * @return String
     * @throws CipherException
     * @throws IOException
     */
    private EthWalletModel getEthWalletModelByWalletFilePath(String walletFilePath, String password) throws CipherException, IOException {
        Credentials credentials =
                WalletUtils.loadCredentials(
                        password,
                        walletFilePath);
        String address = credentials.getAddress();
        String privateKey = "0x" + credentials.getEcKeyPair().getPrivateKey().toString(16);
        String publicKey = "0x" + credentials.getEcKeyPair().getPublicKey().toString(16);
        log.info("Credentials loaded" + privateKey);
        return new EthWalletModel(address, privateKey, publicKey);
    }

    /**
     * @param walletFilePath 文件地址
     * @param password       密码
     * @param to             转入地址
     * @param value          转账金额
     * @param gasPrice       油费
     * @param gasLimit       gas
     * @param data           数据
     * @return
     * @throws IOException
     * @throws CipherException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public EthSendTransaction transferByKeystoreFile(
            String walletFilePath,
            String password,
            String to,
            BigInteger value,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String data) throws IOException, CipherException, ExecutionException, InterruptedException {

        EthWalletModel ethWalletModel = getEthWalletModelByWalletFilePath(walletFilePath, password);
        String from = ethWalletModel.getAddress();
        String privateKey = ethWalletModel.getPrivateKey();
        return this.transfer(from, to, value, privateKey, gasPrice, gasLimit, data);
    }

    /**
     * 发起一笔交易（自定义参数）
     *
     * @param from       发起人钱包地址
     * @param to         转入的钱包地址
     * @param value      转账金额，单位是wei
     * @param privateKey 钱包私钥
     * @param gasPrice   转账费用
     * @param gasLimit   gas
     * @param data       备注的信息
     * @throws IOException
     * @throws CipherException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public EthSendTransaction transfer(
            String from,
            String to,
            BigInteger value,
            String privateKey,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String data) throws IOException, CipherException, ExecutionException, InterruptedException {


        //加载转账所需的凭证，用私钥
        Credentials credentials = Credentials.create(privateKey);
        //获取nonce，交易笔数
        BigInteger nonce = getNonce(from);
        //创建RawTransaction交易对象
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, value);
        //签名Transaction，这里要对交易做签名
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        String hexValue = Numeric.toHexString(signMessage);
        //发送交易
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        return ethSendTransaction;
    }


    /**
     * 获取nonce，交易笔数
     *
     * @param from String
     * @return String
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private BigInteger getNonce(String from) throws ExecutionException, InterruptedException {
        EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = transactionCount.getTransactionCount();
        log.info("transfer nonce : " + nonce);
        return nonce;
    }

}
