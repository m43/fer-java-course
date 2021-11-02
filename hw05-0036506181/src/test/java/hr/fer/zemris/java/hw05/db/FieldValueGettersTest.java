package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class FieldValueGettersTest {
	
	@Test
	void testExample() {
		StudentRecord record = new StudentRecord("1 Perić Pero 5");
		
		assertEquals("Pero", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("Perić", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("1", FieldValueGetters.JMBAG.get(record));
	}

	@Test
	void testFirstName() {
		StudentRecord record1 = new StudentRecord("1 Perić Pero 5");
		StudentRecord record2 = new StudentRecord("1 Perić Perića Junior Pero 5");
		StudentRecord record3 = new StudentRecord("1   123*123      JOSIP     5");
		
		assertEquals("Pero", FieldValueGetters.FIRST_NAME.get(record1));
		assertEquals("Pero", FieldValueGetters.FIRST_NAME.get(record2));
		assertEquals("JOSIP", FieldValueGetters.FIRST_NAME.get(record3));
	}

	@Test
	void testLastName() {
		StudentRecord record1 = new StudentRecord("1 Perić Pero 5");
		StudentRecord record2 = new StudentRecord("1 Perić Perića Junior Pero 5");
		StudentRecord record3 = new StudentRecord("1   123*123      Pero     5");
		
		assertEquals("Perić", FieldValueGetters.LAST_NAME.get(record1));
		assertEquals("Perić Perića Junior", FieldValueGetters.LAST_NAME.get(record2));
		assertEquals("123*123", FieldValueGetters.LAST_NAME.get(record3));
	}
	
	@Test
	void testJMBAG() {
		StudentRecord record1 = new StudentRecord("123 Perić Pero 5");
		StudentRecord record2 = new StudentRecord("321 Perić Perića Junior Pero 5");
		StudentRecord record3 = new StudentRecord("12345678   123*123      Pero     5");
		
		assertEquals("123", FieldValueGetters.JMBAG.get(record1));
		assertEquals("321", FieldValueGetters.JMBAG.get(record2));
		assertEquals("12345678", FieldValueGetters.JMBAG.get(record3));
	}
}
