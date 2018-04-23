
package com.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * TOP K的几种算法
 * TOP K与一般的排序不一样，并不需要数组全部排序，甚至某些需求也不需要TOP K排序，所以如果原封不动地直接使用各种排序方法，然后再去取前K个，肯定是不够优化的
 * @author wuwenhai
 * @since JDK1.6
 * @history 2017-7-12 wuwenhai 新建
 */
public class TopK {

	public static int[] array;
	public static final int SIZE=1000000;
	public static final int K=10;
	
	public static void main(String[] args) {
		init();
		long start,end,TopK;
		
		start =System.currentTimeMillis();
		TopK=findTopByFastSort(array,0,array.length-1,K);
		end =System.currentTimeMillis();
		System.out.println("findTopByFastSort spend time: "+(end-start));
		System.out.println("TopK: "+TopK);
		
		start =System.currentTimeMillis();
		TopK=findTopByFixedArrays(array,K)[K-1];
		end =System.currentTimeMillis();
		System.out.println("findTopByFixedArrays spend time: "+(end-start));
		System.out.println("TopK: "+TopK);
		//System.out.println(Arrays.toString(findTopByFixedArrays(array,K)));
		
		start =System.currentTimeMillis();
		TopK=findTopByBubbling(array,K)[K-1];
		end =System.currentTimeMillis();
		System.out.println("findTopByBubbling spend time: "+(end-start));
		System.out.println("TopK: "+TopK);
		//System.out.println(Arrays.toString(findTopByBubbling(array,K)));
		
	}
	
	public static void init(){
		array=new int[SIZE];
		Random random=new Random();
		for(int i=0;i<SIZE;i++){
			array[i]=random.nextInt(1000*1000*1000);
		}
	}
	
	/**
	 * 
	 * @param array 数组
	 * @param L 左边界
	 * @param R 右边界
	 * @param K 要取的top k
	 * @return
	 * 不管K值多少，都能较快查出，但只是查出第K位的值，并不排序也不返回Top K是哪几个
	 * 如果要得到Top K是哪几个也很简单，遍历一遍数组把大于等于第K位的找出来即可
	 */
	public static int findTopByFastSort(int[] array,int L,int R,int K){
		if (L > R || K < 1)// 检查输入参数是否合法
			return -1;
		if (L == R)// 如果L等于R说明已找到，直接返回
			return array[R];
		int temp = fastSort(array, L, R);// 进行一次快排，返回下标
		if (K + L == temp + 1)// 如果k+L等于返回的下标加１（L不一定从０开始）
			return array[temp];// 则直接返回
		if (K + L < temp + 1)// 如果k+L小于返回的下标加１
			return findTopByFastSort(array, L, temp - 1, K);// 在temp的左边查找第k大数
		else // 否则，在temp的右边部分查找第k-(temp-L+1)大数。这里，右边的第k-(temp-L+1)大数就是整个数组的第k大数
			return findTopByFastSort(array, temp + 1, R, K - (temp - L + 1));
	}
	
	/**
	 * 阉割版的快排，只进行一次排序操作
	 */
	public static int fastSort(int[] array,int L,int R){
		if(L>=R) return -1;
		int i=L;
		int j=R;
		int temp=array[i];
		while(i<j){
			while(i<j&&array[j]<=temp) j--;
			if(i<j){
				array[i]=array[j];
				i++;
			}
			while(i<j&&array[i]>=temp) i++;
			if(i<j){
				array[j]=array[i];
				j--;
			}
		}
		array[i]=temp;
		//将以下两句打开则是完整快排，如completedFastSort所示
		//fastSort(array,L,i-1);
		//fastSort(array,i+1,R);
		return i;
	}
	
	/**
	 * 完整版的快排，真正进行数组完全排序
	 */
	public static int completedFastSort(int[] array,int L,int R){
		if(L>=R) return -1;
		int i=L;
		int j=R;
		int temp=array[i];
		while(i<j){
			while(i<j&&array[j]<=temp) j--;
			if(i<j){
				array[i]=array[j];
				i++;
			}
			while(i<j&&array[i]>=temp) i++;
			if(i<j){
				array[j]=array[i];
				j--;
			}
		}
		array[i]=temp;
		completedFastSort(array,L,i-1);
		completedFastSort(array,i+1,R);
		return i;
	}
	
	/**
	 * 该方法通过生成固定的大小为K的数组存储最大的K个值，复杂度约为N*K*log(K)，适合K较小时使用
	 * 该方法返回的top已经做好了排序，可能更符合实际的需求
	 * @param array
	 * @param K
	 * @return
	 */
	public static int[] findTopByFixedArrays(int[] array,int K){
		int[] top=new int[K];
		//初始化
		for(int i=0;i<K;i++){
			top[i]=array[i];
		}
		//对top数组排序
		completedFastSort(top,0,K-1);
		//遍历array，如果array中有大于top数组中最小的(即最后一位，top[K-1])，则替换，并重新排序top数组
		for(int i=K;i<SIZE;i++){
			if(array[i]>top[K-1]){
				top[K-1]=array[i];
				completedFastSort(top,0,K-1);
			}
		}
		return top;
	}
	
	/**
	 * 用冒泡排序的方式选出Top的K个值
	 * K值小的时候还能看，越大越差，跟上面两种已经不是一个量级了
	 * @param array
	 * @param K
	 * @return
	 */
	public static int[] findTopByBubbling(int[] array,int K){
		int[] top=new int[K];
		int temp;
		for(int k=0;k<K;k++){
			for(int i=0;i<SIZE-k-1;i++){
				if(array[i]<array[i+1]){
					temp=array[i];
					array[i]=array[i+1];
					array[i+1]=temp;
				}
			}
		}
		for(int i=0;i<K;i++){
			top[i]=array[i];
		}
		return top;
	}
}
