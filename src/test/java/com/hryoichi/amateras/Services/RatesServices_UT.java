package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Configs;
import com.hryoichi.amateras.Entities.Rates;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RatesServices_UT {
  @Test
  void 正＿終値のリストを取得できる(){
    LocalDateTime firstDateTime = LocalDateTime.of(2023, 4, 20, 9, 0, 30);
    List<Rates> mockNormal = new ArrayList<>();
    for(int i = 0; i < 110; i++){
      mockNormal.add(new Rates(i, firstDateTime.plusMinutes(Configs.GLOBAL_ALPHA_VANTAGE_COLLECT_EACH_MINUTES * i), 120f + (float)i, 125 + (float) i));
    }
    List<Float> expect = new ArrayList<>(List.of(139f, 159f, 179f, 199f, 219f));
    RatesService ratesService = new RatesService();
    assertThat(ratesService.getCloses(12, mockNormal)).isEqualTo(expect);
  }

}
