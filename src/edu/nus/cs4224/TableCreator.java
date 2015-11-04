package edu.nus.cs4224;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import edu.nus.cs4224.d8.OrderLine;

public class TableCreator {

	  public void run() {

		String csvFile = "/Users/zhengxifeng/apache-cassandra-2.2.2/bin/order-line.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		Session session = Supplier.session;
		try {

			br = new BufferedReader(new FileReader(csvFile));
			int i = 0;
			while ((line = br.readLine()) != null ) {//&& i < 1

			    // use comma as separator
				String[] orderline = line.split(cvsSplitBy);
				/*OrderLine ol = new OrderLine();
				ol.setOl_w_id(Integer.parseInt(orderline[0]));
				ol.setOl_d_id(Integer.parseInt(orderline[1]));
				ol.setOl_o_id(Integer.parseInt(orderline[2]));
				ol.setOl_number(Integer.parseInt(orderline[3]));
				ol.setOl_i_id(Integer.parseInt(orderline[4]));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ParsePosition pos = new ParsePosition(0);
				Date date = formatter.parse(orderline[5], pos);
				ol.setOl_delivery_d(date);
				BigDecimal amount = new BigDecimal(orderline[6]);
				ol.setOl_amount(amount);
				ol.setOl_supply_w_id(Integer.parseInt(orderline[7]));
				BigDecimal quantity = new BigDecimal(orderline[8]);
				ol.setOl_quantity(quantity);
				ol.setOl_district_info(orderline[9]);*/
				
				if (orderline[5].equals("null") || orderline[5]==null)
				{
					Date currentTime = new Date();
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dateString = fm.format(currentTime);
					System.out.println(dateString);
					orderline[5] = dateString;
				}
				//System.out.println(orderline[5]);
				String insertQ = "insert into d8.order_line (OL_W_ID ,OL_D_ID ,OL_O_ID "
						+ ",OL_NUMBER ,OL_I_ID ,OL_DELIVERY_D ,OL_AMOUNT ,OL_SUPPLY_W_ID ,OL_QUANTITY ,OL_DIST_INFO)"
						+ "values(" + orderline[0]+"," + orderline[1]+"," +orderline[2]+"," +orderline[3]+","
						+ orderline[4]+ ",'" + orderline[5] +"',"+orderline[6] +","+orderline[7]+","
						+ orderline[8]+",'" +orderline[9]
						+ "')";
				System.out.println(i +": "+ insertQ);
				session.execute(insertQ);
				i++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	  }
}
