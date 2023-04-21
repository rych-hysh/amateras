package com.hryoichi.amateras.Services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AnalyzeService {

  /**
   *
   * 単純移動平均と標準偏差のリストのハッシュマップを取得
   *
   * @param n 平均と標準偏差計算の区間
   * @param data source data
   * @return {
   *   "averages", List&lt;Float&gt; average
   *   "sigmas", List&lt;Float&gt; sigmas
   * }
   */
  public HashMap<String, List<Float>> calcAveragesAndSigmas(int n, List<Float> data){
    HashMap<String, List<Float>> resultHashMap = new HashMap<>();
    resultHashMap.put("averages", calcAverages(n, data));
    resultHashMap.put("sigmas", calcSigmas(n, data));
    return resultHashMap;
  }

  /**
   *
   * 単純移動平均のリストを取得
   *
   * @param windowWidth 平均計算の区間
   * @param data source data
   * @return calculated averages. First (windowWidth - 1) elements will be null.
   */
  public List<Float> calcAverages(int windowWidth, List<Float> data){
    List<Float> averages = new ArrayList<>();
    for(int i = windowWidth - 1; i < data.size(); i++){
      // subList not includes index of (i + 1)
      List<Float> target = data.subList(i-(windowWidth-1), i + 1);

      Float average = (float) target.stream().mapToDouble(rate -> rate).average().orElse(0);

      averages.add(average);
    }
    return averages;
  }

  /**
   *
   * 標準偏差のリストを取得
   *
   * @param windowWidth 標準偏差の区間
   * @param data source data
   * @return calculated sigmas. First (windowWidth - 1) elements will be null.
   */
  public List<Float> calcSigmas(int windowWidth, List<Float> data){
    List<Float> sigmas = new ArrayList<>();
    for(int i = windowWidth - 1; i < data.size(); i++){
      // subList not includes index of (i + 1)
      List<Float> target = data.subList(i-(windowWidth-1), i + 1);

      Float sigma = (float) Math.sqrt(( windowWidth * target.stream().map(rate -> Math.pow(rate, 2)).mapToDouble(powedRate -> powedRate).sum() - Math.pow(target.stream().mapToDouble(rate -> rate).sum(), 2))/(windowWidth * (windowWidth - 1)));

      sigmas.add(sigma);
    }
    return sigmas;
  }

  // TODO: test
  /**
   *
   * @param n duration
   * @param isStrong if true, arbitrary data are required rising
   * @param data source data
   * @param fallRateThreshold how sharply fall are allowed
   * @param fallCountRateThreshold how many times of fall are allowed
   * @return whether data is rising or not
   */
  public boolean isRising(int n, boolean isStrong, List<Float> data, float fallRateThreshold, float fallCountRateThreshold){
    int fallingCount = 0;
    for(int i = 1; i < data.size(); i++){
      if(data.get(i) < data.get(i - 1)){
        // isStrongなら一度でも減少したらfalse
        if(isStrong) return false;
        // isStrongでなくても最初の値と現在の値の差の8割をきるレートで減少した場合はfalse
        if(data.get(i-1) - data.get(i) > data.get(i - 1) - data.get(0) * fallRateThreshold) return false;
        fallingCount++;
      }
    }
    return (double) fallingCount/data.size() < fallCountRateThreshold;
  }
  public boolean isRising(int n, boolean isStrong, List<Float> data){ return isRising(n, isStrong, data, 0.8f, 0.8f);}

  /**
   *
   * @param n duration
   * @param isStrong if true, arbitrary data are required rising
   * @param data source data
   * @param fallRateThreshold how sharply fall are allowed
   * @param fallCountRateThreshold how many times of fall are allowed
   * @return whether data is rising or not
   */
  public boolean isFalling(int n, boolean isStrong, List<Float> data, float fallRateThreshold, float fallCountRateThreshold){
    int risingCount = 0;
    for(int i = 1; i < data.size(); i++){
      if(data.get(i) > data.get(i - 1)){
        // isStrongなら一度でも増加したらfalse
        if(isStrong) return false;
        // isStrongでなくても最初の値と現在の値の差の8割をきるレートで増加した場合はfalse
        if(data.get(i) - data.get(i-1) > data.get(i - 1) - data.get(0) * fallRateThreshold) return false;
        risingCount++;
      }
    }
    return (double) risingCount/data.size() < fallCountRateThreshold;
  }
  public boolean isFalling(int n, boolean isStrong, List<Float> data){ return isFalling(n, isStrong, data, 0.2f, 0.2f);}

  /**
   *
   * @param n duration
   * @param isStrong if true, arbitrary data are required rising
   * @param data source data
   * @param fallRateThreshold how sharply fall are allowed
   * @param fallCountRateThreshold how many times of fall are allowed
   * @return 1 for rising, -1 for falling, 0 for no trend
   */
  public int hasTrend(int n, boolean isStrong, List<Float> data, float fallRateThreshold, float fallCountRateThreshold){
    if(isRising(n, isStrong, data,fallRateThreshold, fallCountRateThreshold))return 1;
    if(isFalling(n, isStrong, data,fallRateThreshold, fallCountRateThreshold))return -1;
    return 0;
  }
  public int hasTrend(int n, boolean isStrong, List<Float> data){ return hasTrend(n, isStrong, data, 0.2f, 0.2f);}
}
