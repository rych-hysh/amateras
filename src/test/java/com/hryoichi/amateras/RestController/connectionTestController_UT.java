package com.hryoichi.amateras.RestController;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class connectionTestController_UT {

    @Test
    public void 正＿アプリの接続を確認できる(){
        String expected = "connected.";
        connectionTestController ctc = new connectionTestController();
        assertThat(ctc.connectionTest()).isEqualTo(expected);
    }
}
