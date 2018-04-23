
package com.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * TOP K�ļ����㷨
 * TOP K��һ�������һ����������Ҫ����ȫ����������ĳЩ����Ҳ����ҪTOP K�����������ԭ�ⲻ����ֱ��ʹ�ø������򷽷���Ȼ����ȥȡǰK�����϶��ǲ����Ż���
 * @author wuwenhai
 * @since JDK1.6
 * @history 2017-7-12 wuwenhai �½�
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
	 * @param array ����
	 * @param L ��߽�
	 * @param R �ұ߽�
	 * @param K Ҫȡ��top k
	 * @return
	 * ����Kֵ���٣����ܽϿ�������ֻ�ǲ����Kλ��ֵ����������Ҳ������Top K���ļ���
	 * ���Ҫ�õ�Top K���ļ���Ҳ�ܼ򵥣�����һ������Ѵ��ڵ��ڵ�Kλ���ҳ�������
	 */
	public static int findTopByFastSort(int[] array,int L,int R,int K){
		if (L > R || K < 1)// �����������Ƿ�Ϸ�
			return -1;
		if (L == R)// ���L����R˵�����ҵ���ֱ�ӷ���
			return array[R];
		int temp = fastSort(array, L, R);// ����һ�ο��ţ������±�
		if (K + L == temp + 1)// ���k+L���ڷ��ص��±�ӣ���L��һ���ӣ���ʼ��
			return array[temp];// ��ֱ�ӷ���
		if (K + L < temp + 1)// ���k+LС�ڷ��ص��±�ӣ�
			return findTopByFastSort(array, L, temp - 1, K);// ��temp����߲��ҵ�k����
		else // ������temp���ұ߲��ֲ��ҵ�k-(temp-L+1)����������ұߵĵ�k-(temp-L+1)����������������ĵ�k����
			return findTopByFastSort(array, temp + 1, R, K - (temp - L + 1));
	}
	
	/**
	 * �˸��Ŀ��ţ�ֻ����һ���������
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
		//����������������������ţ���completedFastSort��ʾ
		//fastSort(array,L,i-1);
		//fastSort(array,i+1,R);
		return i;
	}
	
	/**
	 * ������Ŀ��ţ���������������ȫ����
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
	 * �÷���ͨ�����ɹ̶��Ĵ�СΪK������洢����K��ֵ�����Ӷ�ԼΪN*K*log(K)���ʺ�K��Сʱʹ��
	 * �÷������ص�top�Ѿ����������򣬿��ܸ�����ʵ�ʵ�����
	 * @param array
	 * @param K
	 * @return
	 */
	public static int[] findTopByFixedArrays(int[] array,int K){
		int[] top=new int[K];
		//��ʼ��
		for(int i=0;i<K;i++){
			top[i]=array[i];
		}
		//��top��������
		completedFastSort(top,0,K-1);
		//����array�����array���д���top��������С��(�����һλ��top[K-1])�����滻������������top����
		for(int i=K;i<SIZE;i++){
			if(array[i]>top[K-1]){
				top[K-1]=array[i];
				completedFastSort(top,0,K-1);
			}
		}
		return top;
	}
	
	/**
	 * ��ð������ķ�ʽѡ��Top��K��ֵ
	 * KֵС��ʱ���ܿ���Խ��Խ������������Ѿ�����һ��������
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
