package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Implementation of {@link LSystemBuilder} that assures the functionality of
 * configuring and building an concrete Lindermayer system according to user
 * defined configuration.
 * 
 * @author Frano Rajič
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Dictionary to map symbols to their respective productions
	 */
	Dictionary<Character, String> productions = new Dictionary<>();

	/**
	 * Dictionary to map symbols with respective commands
	 */
	Dictionary<Character, Command> commands = new Dictionary<>();

	/**
	 * Length of one unit
	 */
	private double unitLength = 0.1;
	/**
	 * Unit length scaler, to keep the dimensions of the drawn fractal more or less
	 * the same in spite of changing the depth of construction
	 */
	private double unitLengthDegreeScaler = 1.0;

	/**
	 * Origin, the vector where to start drawing
	 */
	private Vector2D origin = new Vector2D(0, 0);

	/**
	 * Starting angle, in degrees
	 */
	private double angle = 0;

	/**
	 * Axiom of system
	 */
	private String axiom = "";

	/**
	 * Get the unit length
	 * 
	 * @return the unit length
	 */
	public double getUnitLength() {
		return unitLength;
	}

	/**
	 * Get the unit length scaler
	 * 
	 * @return the unit length scaler
	 */
	public double getUnitLengthDegreeScaler() {
		return unitLengthDegreeScaler;
	}

	/**
	 * Get the origin
	 * 
	 * @return the origin
	 */
	public Vector2D getOrigin() {
		return origin;
	}

	/**
	 * Get the angle
	 * 
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Get the axiom
	 * 
	 * @return the axiom
	 */
	public String getAxiom() {
		return axiom;
	}

	/**
	 * Inner implementation of a {@link LSystem}
	 * 
	 * @author Frano Rajič
	 */
	private class LSystemInner implements LSystem {

		/**
		 * Construct a new inner LSystem
		 */
		public LSystemInner() {

		}

		@Override
		public String generate(int level) {
			if (level < 0) {
				throw new IllegalArgumentException("Level cannot be negative!");
			}

			if (level == 0)
				return (String) axiom;

			String ancestor = generate(level - 1);

			StringBuilder sb = new StringBuilder();
			for (char c : ancestor.toCharArray()) {
				if (productions.get(c) != null) {
					sb.append(productions.get(c));
				} else {
					sb.append(c);
				}
			}

			return sb.toString();
		}

		@Override
		public void draw(int level, Painter painter) {
			Vector2D turtleDirection = new Vector2D(1, 0);
			turtleDirection.rotate(angle * Math.PI / 180.);

			TurtleState turtleStartState = new TurtleState(origin, turtleDirection, Color.BLACK,
					unitLength * Math.pow(unitLengthDegreeScaler, level));

			Context context = new Context();
			context.pushState(turtleStartState);

			String generatedFlow = generate(level);

			Command command;
			for (char c : generatedFlow.toCharArray()) {
				if (isBlank(c))
					continue;
				command = commands.get(Character.valueOf(c));
				if (command != null) {
					command.execute(context, painter);
				}
			}
		}

		/**
		 * Method to check if given character could be described as blank. Blank chars
		 * are new line, \t, \r and space
		 * 
		 * @param c to check if it is blank
		 * @return true if given char is blank
		 */
		private boolean isBlank(char c) {
			return c == '\r' || c == ' ' || c == '\n' || c == '\t';
		}
	}

	@Override
	public LSystem build() {
		return new LSystemInner();
	}

	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for (String line : lines) {
			String[] parts = line.split("\\s+");
			if (parts.length <= 1) {
				continue;
			}

			try {
				switch (parts[0]) {
				case "origin":
					setOrigin(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
					break;
				case "angle":
					setAngle(Double.parseDouble(parts[1]));
					break;
				case "unitLength":
					setUnitLength(Double.parseDouble(parts[1]));
					break;
				case "unitLengthDegreeScaler":
					String expression = line.replaceFirst("unitLengthDegreeScaler", "").replaceAll("\\s+", "");

					double divident = Double.parseDouble(expression.split("/")[0]);
					double divisor = 1;

					if (expression.split("/").length == 2) {
						divisor = Double.parseDouble(expression.split("/")[1]);
					}

					setUnitLengthDegreeScaler(divident / divisor);
					break;
				case "command":
					if (parts[1].length() != 1) {
						throw new IllegalArgumentException("Invalid command");
					}
					String commandBody = line.replaceFirst("command", "").trim().substring(2);
					registerCommand(parts[1].charAt(0), commandBody);
					break;
				case "axiom":
					setAxiom(parts[1]);
					break;
				case "production":
					if (parts[1].length() != 1) {
						throw new IllegalArgumentException("Invalid production");
					}
					registerProduction(parts[1].charAt(0), parts[2]);
					break;
				default:
					throw new IllegalArgumentException("Invalid line given --->" + line + "<---");
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Invalid number of arguments given on line --->" + line + "<---");
			}
		}

		return this;
	}

	@Override
	/**
	 * Method to call to register a new command: </br>
	 * Commands that can be registered:</br>
	 * "draw s": Moves the drawing pointer (the turtle implementation) in the
	 * direction where it is pointing to for the value of the effective unit length,
	 * leaving a trail with configured color. It also updates the position of the
	 * pointer</br>
	 * "skip s": Like draw, only without leaving a trail behind (does not draw)
	 * (just like skipping, jumping over the given length)</br>
	 * "scale s": Change the effective unit length by multiplying with given factor,
	 * for example "scale 0.75"</br>
	 * "rotate a": Rotates the direction where the turtle is looking by angle given
	 * in degrees. Rotation is done in the mathematically positive angle direction.
	 * For example "rotate -90"</br>
	 * "push": Push the current state of the turtle onto the stack</br>
	 * "pop": Deletes the current turtle state from stack, leaving the state that
	 * was previous on the top state</br>
	 * "color rrggbb": update the drawing color. For example "color ff0000"
	 * activates the red color</br>
	 */
	public LSystemBuilder registerCommand(char symbol, String action) {
		commands.put(symbol, createCommand(action));
		return this;
	}

	/**
	 * Create a new command by parsing given string with command. The legit commands
	 * are draw, skip, scale, rotate, push, pop, color.
	 * 
	 * @param s the string to parse to get the command from
	 * @return the parsed command
	 */
	private Command createCommand(String s) {
		String[] parts = s.split("\\s+");

		// unchecked parts length... Not sure if exception should be throws somewhere
		switch (parts[0].toLowerCase()) {
		case "draw":
			return new DrawCommand(Integer.valueOf(parts[1]));
		case "skip":
			return new SkipCommand(Integer.valueOf(parts[1]));
		case "scale":
			return new ScaleCommand(Double.valueOf(parts[1]));
		case "rotate":
			return new RotateCommand(Double.valueOf(parts[1]));
		case "push":
			return new PushCommand();
		case "pop":
			return new PopCommand();
		case "color":
			return new ColorCommand(Color.decode("#" + parts[1]));
		default:
			// should never be reached
			// if reached should I throw an exception?
			break;
		}

		return null;
	}

	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengtDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengtDegreeScaler;
		return this;
	}

}
