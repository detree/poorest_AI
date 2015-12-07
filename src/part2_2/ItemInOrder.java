package part2_2;

import java.util.ArrayList;

public class ItemInOrder {
	ArrayList<Integer> item = null;
	ArrayList<Double> dist_val = null;
	int max_size=-1;
	public ItemInOrder(int size) {
		item = new ArrayList<Integer>();
		dist_val = new ArrayList<Double>();
		max_size = size;
		//add two sentinels.
		item.add(-1);
		dist_val.add(Double.NEGATIVE_INFINITY);
		item.add(-1);
		dist_val.add(Double.POSITIVE_INFINITY);
	}
	
	public void check_and_add(int train_num, double dist_in){
		int size_now = item.size();
		for(int i=1;i<size_now;i++){
			if(dist_in>=dist_val.get(i-1) && dist_in<=dist_val.get(i)){
				item.add(i, train_num);
				dist_val.add(i, dist_in);
				break;
			}
		}
		if(item.size()>max_size+2){//including two sentinels.
			item.remove(item.size()-2);
			dist_val.remove(dist_val.size()-2);
		}
	}
	
	public int get_item_num(int index){
		return item.get(index);
	}
}
