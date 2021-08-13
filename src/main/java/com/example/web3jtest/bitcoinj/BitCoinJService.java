package com.example.web3jtest.bitcoinj;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.utils.BriefLogFormatter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BitCoinJService {


    /**
     * 启动 bitcoinj
     * @throws Exception
     */
    @PostConstruct
    public void start() throws Exception {
        BriefLogFormatter.init();
        String network = "testnet";
        if (network.isEmpty()) {
            System.err.println("Usage: address-to-send-back-to [regtest|testnet]");
            return;
        }
        // Figure out which network we should connect to. Each one gets its own set of files.
        NetworkParameters params;
        String filePrefix;
        if (network.equals("testnet")) {
            params = TestNet3Params.get();
            filePrefix = "forwarding-service-testnet";
        } else if (network.equals("regtest")) {
            params = RegTestParams.get();
            filePrefix = "forwarding-service-regtest";
        } else {
            params = MainNetParams.get();
            filePrefix = "forwarding-service";
        }

        String address = "";
        WalletAppKit kit = new WalletAppKit(params, new File("."), filePrefix);
        kit.startAsync();
        kit.awaitRunning();
    }

}
