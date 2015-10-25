package part1_2;
import java.util.Comparator;

public class Checking_pair implements Comparable<Checking_pair>
{
	int node_num;
	double dist;
	Checking_pair(int n, double d)
	{
		node_num = n;
		dist = d;
	}
	Checking_pair()
	{
		node_num = -1;
		dist = Double.MAX_VALUE;
	}
	
	public int compareTo(Checking_pair pair2) {
		if(this.dist>pair2.dist)
			return 1;
		else if(this.dist == pair2.dist)
			return 0;
		else return -1;
	}
}
