package sxm170231;
//

	import java.util.ArrayList;
	import java.util.List;
	import java.util.Scanner;
	import java.io.FileNotFoundException;
	import java.io.File;


	//Change name of file and class from "NetId" to your net id
	public class sxm170231Project6363 {
		static int VERBOSE = 0;
		static int[][] mp;
		static int[] s;
		static class Jewel {
			public int weight, profit, min, max, fine, cap;

			Jewel(int w, int p, int n, int x, int f, int c) {
				weight = w;
				profit = p;
				min = n;
				max = x;
				fine = f;
				cap = c;
			}

			public String toString() {
				return weight + " " + profit + " " + min + " " + max + " " + fine + " " + cap;
			}
		}

		static class Pair {
			public int p, n;

			Pair(int p, int n) {
				this.p = p;
				this.n = n;
			}

			public String toString() {
				return p + " " + n;
			}
		}

		public static Pair process(int G, Jewel[] items, int n) {
			mp = new int[n+1][G+1];
			int[][] temp = new int[n+1][G+1];
			for(int l=0;l<=G;l++) {
				temp[0][l]=1;
			}
			int count=0;
			int profit;
			int maxProfit = Integer.MIN_VALUE;
			for(int i = 1;i<=n;i++){
				for(int g=0;g<=G;g++){
					maxProfit=Integer.MIN_VALUE;
					for(int qnt=0; qnt<=items[i].max; qnt++){	
						if(g-(qnt*items[i].weight)<0) break;
						if (qnt < items[i].min){
							profit =qnt * items[i].profit + mp[i - 1][g - qnt * items[i].weight]- Math.min(items[i].cap, items[i].fine * (items[i].min - qnt));
							//since qunatity is less than min fine is subtracted	
						}
						else {
							profit =mp[i - 1][g - qnt * items[i].weight] + qnt * items[i].profit;
							//quantity of item greater than min so no fine
						}
						if (maxProfit < profit){
							maxProfit = profit;
							count=temp[i-1][g-qnt*items[i].weight];
						} 
						else if (maxProfit == profit) {
							count = count + temp[i-1][g-qnt*items[i].weight] ;
						}
					}
					mp[i][g]=maxProfit;
					temp[i][g]=count;
				}
			}
			return new Pair(maxProfit, count);
		}
		public static void enumerate(int s[],int i,int g,Jewel[] items){
			if(i==0){
				for(int j = 1; j < s.length; j++) System.out.print(s[j]+" ");
				System.out.println();
			}
			else{
				for(int qnt=0;Math.min(items[i].max - qnt, g-qnt*items[i].weight ) >=0;qnt++){	
					if(qnt< items[i].min){
						if(mp[i][g]== mp[i-1][g-qnt*items[i].weight] + qnt*items[i].profit - Math.min(items[i].cap, items[i].fine * (items[i].min - qnt))){
							s[i]=qnt;											
							enumerate(s,i-1,g-qnt*items[i].weight,items);}
					}
					else{
						if(mp[i][g]== mp[i-1][g-qnt*items[i].weight] + qnt*items[i].profit){
							s[i]=qnt;
							enumerate(s, i-1,g-qnt*items[i].weight,items);
						}
					}
				}
			}
		}
		public static void main(String[] args) throws FileNotFoundException {
			Scanner in;
			if (args.length == 0 || args[0].equals("-")) {
				in = new Scanner(System.in);
			} else {
				File inputFile = new File(args[0]);
				in = new Scanner(inputFile);
			}
			if (args.length > 1) {
				VERBOSE = Integer.parseInt(args[1]);
			}
			int G = in.nextInt();
			int n = in.nextInt();
			
			Jewel[] items = new Jewel[n + 1];
			for (int i = 0; i < n; i++) {
				int index = in.nextInt();
				int weight = in.nextInt();
				int profit = in.nextInt();
				int min = in.nextInt();
				int max = in.nextInt();
				int fine = in.nextInt();
				int cap = in.nextInt();
				items[index] = new Jewel(weight, profit, min, max, fine, cap);
				if (VERBOSE > 0) {
					System.out.println(index + " " + items[index]);
				}
			}
			s=new int[n+1];
			Pair answer = process(G, items, n);
			System.out.println("# Output");
			System.out.println(answer);
			if(VERBOSE>0) {
			System.out.println("# List of optimal solutions");
			enumerate(s,n,G,items);
			}


		}
	}