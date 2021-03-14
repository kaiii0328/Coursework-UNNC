package test;
import java.math.BigDecimal;
import java.util.Date;

public class BoCTransaction {
	private String transactionName;
	private BigDecimal transactionValue;
	private int transactionCategory;
	private Date transactionTime;

	public BoCTransaction() {
		/**
		 * create a new Date 
		 * create a new BigDecimal("0.00")
		 * last modified by @author Zhihao Li
		 */
		transactionName = "[Pending Transaction]";
		transactionValue = new BigDecimal("0.00");
		transactionCategory = 0;
		transactionTime = new Date();
	}

	public BoCTransaction(String tName, BigDecimal tValue, int tCat) {
		/**
		 * limit the length of tName
		 * limit the value of tValue
		 * last modified by @author Zhihao Li
		 */
		if (!(tName ==null) && (tName.length()>25)) {
			tName=tName.substring(0,25);
		}
		transactionName = tName;
		if (tValue.compareTo(new BigDecimal("0.00")) == 1)
			transactionValue = tValue;
		else
			transactionValue = new BigDecimal("0.01");
		transactionCategory = tCat;
		transactionTime = new Date();
	}

	public String transactionName() {
		return transactionName;
	}

	public BigDecimal transactionValue() {
		return transactionValue;
	}

	public int transactionCategory() {
		return transactionCategory;
	}

	public Date transactionTime() {
		return transactionTime;
	}

	public void setTransactionName(String tName) throws Exception {
		/**
		*changed the limit of tName and original transactionName
		*last modified by @author Zhihao Li 
		*/
		if (transactionName == null ||transactionName.equals("[Pending Transaction]")) {
			if (!(tName ==null)) {
				if (tName.length()>25) {
					tName=tName.substring(0,25);
				}
			}
			transactionName = tName;
		}
		else 
			throw new Exception("Name should only be set once.");
	}

	public void setTransactionValue(BigDecimal tValue) throws Exception {
		/**
		*add limit to "set once" situation, only when transactionValue is not null
		*add print line for complain if there is already a value
		*last modified by @author Wangkai JIN, 05/06/2020
		**/
		if (!transactionValue.equals(new BigDecimal("0.00"))) {
			throw new Exception("The value has already been set and can only be set once!");
		}
		else
			if (tValue.compareTo(new BigDecimal("0.00")) == 1) {
				// 1 means bigger, -1 means smaller, 0 means same
				transactionValue = tValue;
		}
	}

	public void setTransactionCategory(int tCat) {
		if (tCat >= 0) {
			transactionCategory = tCat;
		}
	}

	public void setTransactionTime(Date tTime) {
		if (tTime != null) {
			transactionTime = tTime;
		}
	}

	public Boolean isComplete() {
		/**
		*This function is created to test the transactionName and transactionValue
		*return true only when transactionName and transactionValue
		*are both set, otherwise, return false
		*last modified by @author Zhihao Li 
		*/
		if (transactionValue == null || transactionName == null || transactionName == "[Pending Transaction]")
			return false;
		else
			return true;
	}
	
	@Override
	public String toString() {
		/**
		 *add Date object to the end
		 *last modified by @author Zhihao Li
		 **/
		return transactionName + " - ¥" + transactionValue.toString() + " " + transactionTime;
	}

}
