package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class StudentDatabaseTest {

	private List<String> getLines() {
		try {
			return Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Test
	void testStudentDatabase() {
		List<String> input = getLines();
		assertNotNull(input);

		StudentDatabase db = new StudentDatabase(input);
		assertNotNull(db);
	}

	@Test
	void testForJMBAG() {
		List<String> input = getLines();
		input.add("123 name surname 5");
		StudentDatabase db = new StudentDatabase(input);
		StudentRecord s = new StudentRecord("123", "name", "surname", 5);
		assertEquals(s, db.forJMBAG("123"));
		assertEquals(5, db.forJMBAG("123").getGrade());
	}

	@Test
	void testFilter() {
		List<String> input = getLines();
		StudentDatabase db = new StudentDatabase(input);

		int number1 = db.filter(s -> false).size();
		assertEquals(0, number1);
		;

		int number2 = db.filter(s -> true).size();
		assertNotEquals(0, number2);
	}

}
