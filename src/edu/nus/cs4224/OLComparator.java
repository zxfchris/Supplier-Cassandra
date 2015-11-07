package edu.nus.cs4224;

import java.util.Comparator;

import edu.nus.cs4224.d8.OrderLine;

public class OLComparator implements Comparator<Object>{
	@Override
	public int compare(Object o1, Object o2) {
		OrderLine ol1 = (OrderLine)o1;
		OrderLine ol2 = (OrderLine)o2;
		if (ol1.getOl_quantity()!=null && ol2.getOl_quantity()!=null) {
			if (ol1.getOl_quantity().compareTo(ol2.getOl_quantity()) == 1) {
				return -1;
			} else if (ol1.getOl_quantity().compareTo(ol2.getOl_quantity()) == -1) {
				return 1;
			}
		}
		return 0;
	}

}
