
package us.higashiyama.george.Redeem;

import java.util.List;

public class playerItem {

	int itemID;
	int quantity;
	int enchantment;
	int enchantmentLevel;
	short data;
	public List<String> displayList;

	public playerItem(int newitemID, int newquantity, int newenchantment, int newenchantmentLevel, int newdata) {

		itemID = newitemID;
		quantity = newquantity;
		enchantment = newenchantment;
		enchantmentLevel = newenchantmentLevel;
		data = (short) newdata;

	}

	public playerItem(int newitemID, int newquantity, short newdata) {

		itemID = newitemID;
		quantity = newquantity;
		data = newdata;

	}

}
