package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

@SuppressWarnings("javadoc")
class LSystemBuilderImplTest {

	@Test
	void testLSystemImplGenerate() {

		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		builder.setAxiom("F");
		builder.registerProduction('F', "F+F--F+F");

		LSystem lsys = builder.build();
		assertEquals("F", lsys.generate(0));
		assertEquals("F+F--F+F", lsys.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", lsys.generate(2));
	}

}
