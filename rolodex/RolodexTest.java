package rolodex;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Assertions;
public class RolodexTest{
	
	//@Before
	
	@Test
	public void testRolodex() {
		Rolodex r = new Rolodex();
		assertEquals(r.size(), 0);
	}
	
	@Test
	public void testSize() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		r.addCard("Gelleane", "420");
		assertEquals(r.size(), 2);
	}
	
	@Test
	public void testContains() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		assertTrue(r.contains("Ayush"));
	}
	
	@Test
	public void testAdd() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		
		Rolodex j = new Rolodex();
		
		assertNotEquals(r.toString(), j.toString());
	}

	@Test
	public void testRemoveCard() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		r.addCard("Gelleane", "420");
		r.removeCard("Ayush", "6969");
		
		Rolodex j = new Rolodex();
		j.addCard("Gelleane", "420");
		
		assertEquals(r.toString(), j.toString());
	}
	
	@Test
	public void testRemoveAllCards() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		r.addCard("Ayush", "789");
		r.addCard("Gelleane", "420");
		r.removeAllCards("Ayush");
		
		Rolodex j = new Rolodex();
		j.addCard("Gelleane", "420");
		
		assertEquals(r.toString(), j.toString());
	}
	
	@Test
	public void testLookup() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		r.addCard("Ayush", "420");
		
		ArrayList<String> testAL = new ArrayList<String>();
		testAL.add("6969");
		testAL.add("420");

		
		assertEquals(r.lookup("Ayush").toString(), testAL.toString());
	}
	
	@Test
	public	void testToString() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		r.addCard("EzOng", "420");
		r.removeCard("Ayush", "6969");
		
		Rolodex j = new Rolodex();
		j.addCard("EzOng", "420");
		
		assertEquals(r.toString(), j.toString());
	}
	
	@Test
	public 	void testInitializeCursor() {
		Rolodex r = new Rolodex();
		r.initializeCursor();
		
		assertEquals(r.currentEntryToString(), "Separator A");
	}
	
	@Test
	public void testNextSeparator() {
		Rolodex r = new Rolodex();
		r.initializeCursor();
		r.nextSeparator();
		
		assertEquals(r.currentEntryToString(), "Separator B");
	}
	
	@Test
	public void testNextEntry() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		r.initializeCursor();
		r.nextEntry();
		
		assertEquals(r.currentEntryToString(), "Name: Ayush, Cell: 6969");
	}
	
	@Test
	public void testCurrentEntryToString() {
		Rolodex r = new Rolodex();
		r.initializeCursor();
		
		assertEquals(r.currentEntryToString(), "Separator A");
	}
	
	//THROWS
	
	
	
	
	
	@Test
	public 	void testRemoveAllCardsNameNull() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeAllCards(null);});
	}
	
	@Test
	public	void testRemoveAllCardsNameInvalid() {
		Rolodex r = new Rolodex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeAllCards("Ayush1");});
	}
	
	@Test
	public	void testRemoveAllCardsNotExist() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeAllCards("Gelleane");});
	}
	
	@Test
	public void testRemoveCardNameNull() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeCard(null,"6969");});
	}
		
	@Test
	public	void testRemoveCardNameInvalid() {
		Rolodex r = new Rolodex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeCard("Ayush1","6969");});
	}
	
	@Test
	public	void testRemoveCardNotExist() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.removeCard("Gelleane","6969");});
	}
	
	
	
	@Test
	public	void testLookupNameInvalid() {
		Rolodex r = new Rolodex();
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.lookup("Ayush1");});
	}
	
	@Test
	public	void testLookupNotExist() {
		Rolodex r = new Rolodex();
		r.addCard("Ayush", "6969");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {r.lookup("Gelleane");});
	}
	

}