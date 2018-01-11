package com.mrmf.module.wx.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * 红包算法   每个人至少分多少钱的一个红包算法
 * @author yangshaodong
 */
public class WeixinRedPacket {
    /**
     * 金额的精度：最多保留两位小数
     */
    private static int scale = 2;
    /**
     * money 剩下的钱     numberOfPeople 人数
     * @return
     */
    public static BigDecimal[] generalPlay(final BigDecimal money,
            final int numberOfPeople) {
        BigDecimal divisor = new BigDecimal(100);
        int n = money.multiply(divisor).intValue();
        // 从1--n之间随机抽出numberOfPeople个数。其实这里就是一个抽样问题
        BigDecimal[] result = new BigDecimal[numberOfPeople];
        int m = numberOfPeople;
        int index = 0;
        if(m > n){
        	int temp = m;
        	m = n;
        	n = temp;
        	int j = 0;
        	for (int i = 0; i < n; i++, index++) {
        		long bigrand = bigRand();
        		if (bigrand % (n - i) < m) {
        			result[index] = new BigDecimal(j + 1).divide(divisor, scale,
        					BigDecimal.ROUND_HALF_UP);
        			m--;
        			j++;
        		} else {
        			result[index] = new BigDecimal("0.00");
        		}
        	}
        } else {
        	for (int i = 0; i < n; i++) {
        		long bigrand = bigRand();
        		if (bigrand % (n - i) < m) {
        			result[index++] = new BigDecimal(i + 1).divide(divisor, scale,
        					BigDecimal.ROUND_HALF_UP);
        			m--;
        		}
        	}
        }
        // 分区间处理
        for (int i = numberOfPeople - 1; i > 0; i--) {
        	if(result[i].compareTo(new BigDecimal("0.00")) == 0){
        		continue;
        	}        
        	BigDecimal tempMoney;
        	if (i == (numberOfPeople - 1)) {
        		tempMoney = money;
            } else {
            	tempMoney = result[i];
            }
        	BigDecimal nextMoney = null;
        	for(int j = i -1; j >= 0; j--){
        		if(result[j].compareTo(new BigDecimal("0.00")) == 0){
            		continue;
            	}  else {
            		nextMoney = result[j];
            		break;
            	}
        	}
        	nextMoney = nextMoney == null ? new BigDecimal("0.00") : nextMoney;
        	result[i] = tempMoney.subtract(nextMoney);
        }
        return result;
    }
 
    /**
     * 产生一个很大的随机整数
     * @return 一个很大的整数
     */
    private static long bigRand() {
        long bigrand = (long) (Math.random() * Integer.MAX_VALUE)
                + Integer.MAX_VALUE;
        return bigrand;
    }
 
    /**
     * 检查方法参数的有效性
     */
    private static void checkGeneralPlayValidParam(final BigDecimal money,
            int numberOfPeople) {
        // 确保人数大于等于1
        if (numberOfPeople < 1) {
            throw new RuntimeException("人数 " + numberOfPeople + " 应该大于0！");
        }
        // 确保每个人至少能分到0.01元
        if (money.compareTo(new BigDecimal("0.01").multiply(new BigDecimal(
                numberOfPeople))) < 0) {
            throw new RuntimeException("人数太多，钱不够分！");
        }
        // 确保money只有两位小数
        if (money.scale() > scale) {
            throw new RuntimeException("金额数据不对，最多保留两位小数！");
        }
    }
    /**
     * 接口名称：有最小值的算法<br>
	 * @auto thero
	 */
    public static List<BigDecimal> initMoney(BigDecimal money, BigDecimal minMoney, int numOfPeople){
    	//1、首先创建numOfPeople的数组B、并将最低值附进去
        checkGeneralPlayValidParam(money, numOfPeople);
    	List<BigDecimal> moneyList = new ArrayList<BigDecimal>();
    	BigDecimal minTotal = new BigDecimal("0");
    	for (int i = 0; i < numOfPeople; i++) {
			moneyList.add(minMoney);
			minTotal = minTotal.add(minMoney);
		}
    	if(minTotal.compareTo(money) == 1){
    		throw new RuntimeException("人数太多，钱不够分！");
    	}
    	BigDecimal remain = money.subtract(minTotal);
    	BigDecimal[] result = null;
    	//2、用总金额减去已分组的合得出剩余值A；
    	if (minTotal.compareTo(money)==0) {
			return moneyList;
		}else{
			//3、用区间分组的方法对A进行分组；
			result = generalPlay(remain,numOfPeople);
		}
    	//4、对B进行累计;
    	List<BigDecimal> resultList = new ArrayList<BigDecimal>();
    	for(int j = 0 ;j < moneyList.size() ; j++){
    		BigDecimal temp = moneyList.get(j);
    		temp = temp.add(result[j]);
    		resultList.add(temp);
    	}
    	//5、获得所有数组;
		return resultList;
    }
   
    public static void main(String[] args) {
    	BigDecimal money = new BigDecimal("0.05");
    	for(int i = 1;i<1000;i++) {
    		money = money.add(new BigDecimal("0.01"));
	    	BigDecimal minMoney = new BigDecimal("0.01"); // minMoney应该作为一个参数
	    	int numOfPeople = 6;
	    	List<BigDecimal> moneyList= initMoney(money, minMoney, numOfPeople);
	    	BigDecimal sum = new BigDecimal("0");
	    	for (BigDecimal bigDecimal : moneyList) {
				System.out.println("红包金额:" + bigDecimal);
	    		sum = sum.add(bigDecimal);
			} 
	    	System.out.println("sum:" +sum);
    	}
	}
}