package rolodex;

import java.util.ArrayList;

/* Name: Ayush Misra
 * Pledge: I Pledge my honor that I have abided by the Stevens Honor System."
 * CS 284 Data Structures
 */


public class Rolodex {
	private Entry cursor;
	private final Entry[] index;

	//Constructs a Rolodex full of only separators A - Z.
	Rolodex() {
			char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
			
			index = new Entry[26];
			Entry prev = null;
			Entry curr;
			for(int i = 0; i < letters.length; i++) {
				curr = new Separator(prev, null, letters[i]);
				index[i] = curr;
				prev = curr;
			}
			index[0].prev = index[25];
			for(int i = letters.length - 2; i >= 0; i--) {
				index[i].next = index[i+1];
			}
			index[25].next = index[0];
		}
	
	//Returns true if the inputed name is in the rolodex. False if not.
	public Boolean contains(String name) {
		if (name == null) {
			throw new IllegalArgumentException("contains: name cannot be null");
		}
		char letter = name.toUpperCase().charAt(0);
		int ascii = (int) letter;
		int location = ascii - 65;
		Entry temp = index[location];
		if (location == 25) {
			while (temp != index[0]) {
				if (name.equals(temp.getName())) {
					return true;
				}
				temp = temp.next;
				}
			}
		else {
			while (temp != index[location + 1]) {
				if (name.equals(temp.getName())) {
					return true;
				}
				temp = temp.next;
				}
		}
		return false;
	}
	
			
	
	// Returns the Size of rolodex. Only cards are counted, not separators.
	public int size() {
		int counter = 0;
		for (int i = 0; i < 26; i++) {
			Entry temp = index[i];
			if (i == 25) {
				while (temp != index[0]) {
					if (temp.isSeparator() != true) {
						counter++;
					}
					temp = temp.next;
				}
			} else {
				while (temp != index[i+1]) {
					if (temp.isSeparator() != true) {
						counter++;
					}
					temp = temp.next;
			}
			}
		}
		return counter;

	}

	//Returns an array list of cells that correlate to the inputed name.
	public ArrayList<String> lookup(String name) {
		ArrayList<String> cells = new ArrayList<>();
		if (!contains(name)) {
			throw new IllegalArgumentException("lookup: name not found");
		}
		for (int i = 0; i < index.length; i++) {
			if( index[i].getName().equals(Character.toString(Character.toUpperCase(name.charAt(0))))){
				Entry current = index[i];
				while (!current.next.isSeparator()){
					if(name.equals(current.next.getName())){
						Card currCell = (Card) current.next;
						cells.add(currCell.getCell());
					}
					current = current.next;
				}
			}
		}
		return cells;
	}

	//Implemented toString Method that prints every Entry available.
	public String toString() {
		Entry current = index[0];

		StringBuilder b = new StringBuilder();
		while (current.next!=index[0]) {
			b.append(current.toString()+"\n");
			current=current.next;
		}
		b.append(current.toString()+"\n");		
		return b.toString();
	}



	//Adds a card to rolodex. Will throw exception if card with same name and cell exist.
	public void addCard(String name, String cell) {
	    if (contains(name)) {
	        ArrayList<String> check = lookup(name);
	        for (String c : check) {
	            if (c.equals(cell)) {
	                throw new IllegalArgumentException("addCard: duplicate entry");
	            }
	        }
	    }
	    char letter = name.toUpperCase().charAt(0);
	    int ascii = (int) letter;
	    int location = ascii - 65;
	    Entry current = index[location];
		while(!current.next.isSeparator() && (name.compareTo(current.next.getName()) >= 0)){
			Card currCard = (Card) current.next;
			if(name.equals(current.next.getName()) && cell.equals(currCard.getCell())){
				throw new IllegalArgumentException("addCard: duplicate entry");						
			}
			current = current.next;
		}
		Entry temp = current.next;
		current.next = new Card(current, temp, name, cell);
		current.next.next.prev = current.next;
	}



				 
		 
	
	// Removes a card from the rolodex. If the name or cell does not exist, it will throw an exception.
	public void removeCard(String name, String cell) {
		if (contains(name) != true) {
		    	throw new IllegalArgumentException("removeCard: name does not exist");
		 }
		char letter = name.toUpperCase().charAt(0);
		int ascii = (int) letter;
		int location = ascii - 65;
		Entry temp = index[location];
		if (location == 25) {
			while (temp != index[0]) {
				if (name.equals(temp.getName()) && cell.equals(((Card) temp).getCell())) {
					temp.prev.next = temp.next;
					temp.next.prev = temp.prev;
					return;
				}
				temp = temp.next;
				}
		}
	 else { 
		while (temp != index[location + 1]) {
			if (name.equals(temp.getName()) && cell.equals(((Card) temp).getCell())) {
				temp.prev.next = temp.next;
				temp.next.prev = temp.prev;
				return;
			}
			temp = temp.next;
			}
	 }
		throw new IllegalArgumentException("removeCard: cell for that name does not exist");
	}
	
	// Removes all cards inside rolodex for specified name. Will throw exception if name does not exist.
	public void removeAllCards(String name) {
		if (contains(name) != true) {
		    	throw new IllegalArgumentException("removeAllCards: name does not exist");
		}
		char letter = name.toUpperCase().charAt(0);
		int ascii = (int) letter;
		int location = ascii - 65;
		Entry temp = index[location];
		if (location == 25) {
			while (temp != index[0]) {
				if (name.equals(temp.getName())) {
					temp.prev.next = temp.next;
					temp.next.prev = temp.prev;
				
				}
				temp = temp.next;
				}
		}
		else
			while (temp != index[location + 1]) {
				if (name.equals(temp.getName())) {
					temp.prev.next = temp.next;
					temp.next.prev = temp.prev;
				
				}
				temp = temp.next;
				}
		}

	// Cursor operations
	// Initializes cursor to 'A' separator.
	public void initializeCursor() {
		    cursor = index[0];

	}
	//Moves cursor to next separator
	public void nextSeparator() {
		while (cursor.next.isSeparator() != true) {
			cursor = cursor.next;
		}
		cursor = cursor.next;
	}

	//Moves cursor to next card or separator depending on what is in rolodex.
	public void nextEntry() {
		cursor = cursor.next;

	}

	//Returns the string position of the cursor.
	public String currentEntryToString() {
		return cursor.toString();
		}
	}



