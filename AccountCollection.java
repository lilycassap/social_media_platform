package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;


public class AccountCollection implements Serializable {
	// account list
	ArrayList<Account> accounts = new ArrayList<>();

	/**
	 * Check if passed handle belongs to another account
	 * @param handle account handle to be checked
	 * @return boolean value representing if the account handle already exists
	 */
	public boolean checkHandleExists(String handle) {
		for(Account acc: accounts) {
			if(acc.getStringHandle().equals(handle)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if passed handle meets the criteria for a valid handle:
	 * length between 1 and 30,
	 * no whitespaces
	 * @param handle account handle to be checked
	 * @return boolean value representing if the account handle already exists
	 */
	public boolean checkValidHandle(String handle) {
		if(handle.length() == 0 || handle.length() > 30 || handle.contains(" ")) {
			return false;
		}
		return true;
	}


	//removed throws IllegalHandleException, InvalidHandleException - check still works
	//removed handle from parameters as account already made then added to this list

	/**
	 * Adds the newly created account to the list of existing accounts
	 * @param account account's handle
	 * @return ID of account created
	 */
	public int addAccount(Account account)  {
		accounts.add(account);
		return account.getID();
	}

	/**
	 * Get number of accounts in system
	 * @return Number of accounts
	 */
	public int getNumOfAccounts() {
		return accounts.size();
	}


	/**
	 * Removes an account from the list of existing accounts (and removes posts by the account too - maybe through post collection)
	 * @param account account's handle
	 */
	public void removeAccount(Account account) {
		accounts.remove(account);
	}

	/**
	 * Finds the ID for an account given a handle
	 * @param handle account's handle
	 * @return account's id
	 */
	public int getIDFromHandle(String handle) {
		for(Account acc: accounts) {
			if(acc.getStringHandle().equals(handle)) {
				return acc.getID();
			}
		}
		// ID of -1 indicates that the account was not found
		return -1;
	}



}