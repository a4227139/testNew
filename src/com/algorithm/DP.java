package com.algorithm;

public class DP {

	public static void main(String[] args) {
		int arr[] = new int[]{1, 2, 3};  
	    int m = arr.length;  
	    int n = 4;  
	    int count=count(arr, m, n);  
	    System.out.println(count);
	}
	
	static int count( int S[], int m, int n )  
	{  
	    int i, j, x, y;  
	  
	    // ͨ�����¶��ϵķ�ʽ���������Ҫn+1��  
	    // ������������n=0  
	    int[][] table=new int[n+1][m];  
	  
	    // ��ʼ��n=0����� 
	    for (i=0; i<m; i++)  
	        table[0][i] = 1;  
	  
	    for (i = 1; i < n+1; i++)  
	    {  
	        for (j = 0; j < m; j++)  
	        {  
	            // ���� S[j] �ķ�����  
	            x = (i-S[j] >= 0)? table[i - S[j]][j]: 0;  
	  
	            // ������ S[j] �ķ�����  
	            y = (j >= 1)? table[i][j-1]: 0;  
	  
	            table[i][j] = x + y;  
	        }  
	    }  
	    return table[n][m-1];  
	}
}
