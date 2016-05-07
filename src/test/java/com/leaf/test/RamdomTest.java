package com.leaf.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class RamdomTest {
	
	public static void main(String[] arg){
		Random random = new Random();
		for(int i=0;i<100;i++){
			System.out.println("第"+i+"取值:");
			System.out.println(random.nextInt(2));
			System.out.println(random.nextLong());
		}
	}
	
	@Test
	public void test(){
		for(int i=0;i<5;i++){
			System.out.println("第"+(i+1)+"取值:");
			List<Long> aa = randomInstList(9l,5);
			for(Long a :aa){
				System.out.println(a);
			}
		}
	}

	/**
	 * 把一个整数分成多个整数（整数分红包,最小份为1）
	 * @param total 总数
	 * @param num 份数
	 * @return
	 */
	List<Long> randomInstList(Long total,Integer num){
		List<Long> randomInstList = new ArrayList<Long>();
		
		Random random = new Random();
		
		for(int i=1;i<num;i++ ){
			//减去最小值还可以取值的范围 ，num-i+1是因为后面取完随机数还需要加上最小值1*1
			Long scope = total- (1*(num-i+1));
			Integer randomInt;
			//int 最大值是 2147483647 ,随机出来也包括0，所以加上最小值1
			if(scope>=2147483646l){
				randomInt = random.nextInt(2147483646)+1;
			}else if(scope==0l){
				randomInt=1;
			}else{
				randomInt = random.nextInt(scope.intValue())+1;
			}
			randomInstList.add(randomInt.longValue());
			total = total-randomInt;
		}
		randomInstList.add(total);
		return randomInstList;
	}
}
