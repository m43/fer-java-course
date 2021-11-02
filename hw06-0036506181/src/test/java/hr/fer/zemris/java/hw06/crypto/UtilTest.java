package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
class UtilTest {

	static String[] validHEX = { "11", "00", "99", "1234", "1122334455667788991010111122221313141415151616171718181919",
			"0000", "aa", "a0a0", "0a0a", "ffff", "fafa", "afaf", "1f1f1f1f1f1f", "f1f1f1f1f1", "f8", "aaaa",
			"123123abcabc", "0123456789aabbccddeeff", "AAAA", "AF54" };

	@Test
	void testHextobyteWithEmptyString() {
		assertEquals(0, Util.hextobyte("").length);
	}

	@Test
	void testHextobyteWithNullPointer() {
		assertThrows(NullPointerException.class, () -> Util.hextobyte(null));
	}

	@Test
	void testHextobyteWithInvalidInputOfUnevenLength() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("123"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("1"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("1aa"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("123ff"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("f123a"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("f"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("a"));
	}

	@Test
	void testHextobyteWithInvalidInputOfInvalidCharacters() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("aaaR"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("rr"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("rara"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("\"\""));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("čččć"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("pjpj"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("joseph"));
	}

	@Test
	void testHextobyteWithValidUpperCaseLetterInString() {
		assertEquals(4, Util.hextobyte("AAFF0123").length);
	}

	@Test
	void testHextobyte() {
		for (String s : validHEX) {
			assertEquals(s.length() / 2, Util.hextobyte(s).length);
		}
	}

	@Test
	void testHextobyteBasic() {
		byte[] zvečevo = Util.hextobyte("01aE22");

		assertEquals(1, zvečevo[0]);
		assertEquals(-82, zvečevo[1]);
		assertEquals(34, zvečevo[2]);
	}

	@Test
	void testBytetohexBasic() {
		assertEquals("01ae22", Util.bytetohex(new byte[] { 1, -82, 34 }));
	}

	@Test
	void testbytetoHexWithNullPointer() {
		assertThrows(NullPointerException.class, () -> Util.bytetohex(null));
	}

	@Test
	void testIsValidHEXString() {
		for(String s: validHEX) {
			assertTrue(Util.isValidHEXString(s));
		}
		assertFalse(Util.isValidHEXString("e"));
		assertFalse(Util.isValidHEXString("1"));
		assertFalse(Util.isValidHEXString("eee"));
		assertFalse(Util.isValidHEXString("123"));
		assertFalse(Util.isValidHEXString("e3e"));
		assertFalse(Util.isValidHEXString("qw"));
		assertFalse(Util.isValidHEXString("qwae"));
		assertFalse(Util.isValidHEXString("aaaqaa"));
		assertFalse(Util.isValidHEXString("QW"));
		assertFalse(Util.isValidHEXString("AAQAAA"));
		assertFalse(Util.isValidHEXString("aaQaaa"));
		assertFalse(Util.isValidHEXString("00Q000"));
		assertFalse(Util.isValidHEXString("00q000"));
		assertFalse(Util.isValidHEXString("...."));
		assertFalse(Util.isValidHEXString("??"));
		assertFalse(Util.isValidHEXString("nevalja"));
	}

	@Test
	void testCombinationOfBothConversions() {
		for (String s : validHEX) {
			assertEquals(s.toLowerCase(), Util.bytetohex(Util.hextobyte(s)));
		}
	}

}
