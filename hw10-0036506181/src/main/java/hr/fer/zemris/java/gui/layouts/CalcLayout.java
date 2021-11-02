package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Class models an calculator like layout that offers an grid of {@value #ROWS}
 * rows x {@value #COLUMNS} columns, where (1,1) till
 * (1,{@value #FIRST_BLOCK_SIZE}) represents the same, big component usually
 * used to show the result on a calculator.
 * 
 * @author Frano Rajič
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * The number of rows the calculator will have
	 */
	public final static int ROWS = 5;

	/**
	 * The number of columns the calculator will have
	 */
	public final static int COLUMNS = 7;

	/**
	 * The number of components the first block in the calculator will have
	 */
	public final static int FIRST_BLOCK_SIZE = 5;

	/**
	 * Defines the gap that both rows and columns will have.
	 */
	private final int vgap;

	/**
	 * Map used to map all the components of the calculator to their respective
	 * position inside calculator layout. Position is defined with
	 * {@link RCPosition}.
	 */
	private Map<Component, RCPosition> components = new HashMap<>();

	/**
	 * The minimal width of all components.
	 */
	private int minComponentWidth = 0;

	/**
	 * The minimal height of all components.
	 */
	private int minComponentHeight = 0;

	/**
	 * The maximal width of all components.
	 */
	private int maxComponentWidth = 0;

	/**
	 * The maximal height of all components.
	 */
	private int maxComponentHeight = 0;

	/**
	 * The preferred width of all components.
	 */
	private int preferredComponentWidth = 0;

	/**
	 * The preferred height of all components.
	 */
	private int preferredComponentHeight = 0;

	/**
	 * Flag used to track if {@link #setSizes()} should be called again.
	 */
	private boolean sizeUnknown = true;

	/**
	 * Initialize default layout. Default layout has no gaps between rows and
	 * columns.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Initialize layout with given gap between rows and columns. The same gap is
	 * used for both.
	 * 
	 * @param vgap the gap between rows and columns
	 */
	public CalcLayout(int vgap) {
		this.vgap = vgap;
	}

	@Override
	/**
	 * Method is unsupported. Should never be called when dealing with
	 * {@link CalcLayout}
	 */
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("This method is unsupported and should never be called");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		sizeUnknown = true;
		if (components.remove(comp) == null) {
			throw new UnsupportedOperationException("There is no such component..");
		}
	}

	/**
	 * Help method to set all relevant sizes by looking at the preferences of all
	 * the components
	 */
	private void setSizes() {
		minComponentWidth = 0;
		minComponentHeight = 0;
		maxComponentWidth = 0;
		maxComponentHeight = 0;
		preferredComponentWidth = 0;
		preferredComponentHeight = 0;

		for (Entry<Component, RCPosition> e : components.entrySet()) {
			// is the component the first component spanning from 1,1 to 1,5
			boolean big = false;
			if (e.getValue().getColumn() == 1 && e.getValue().getColumn() == 1) {
				big = true;
			}

			Dimension minDimensions = e.getKey().getMinimumSize();
			if (minDimensions != null) {
				if (big) {
					minComponentWidth = Math.max(
							(int) Math.ceil(
									(minDimensions.width - (FIRST_BLOCK_SIZE - 1) * vgap) / (double) FIRST_BLOCK_SIZE),
							minComponentWidth);
				} else {
					minComponentWidth = Math.max(minDimensions.width, minComponentWidth);
				}
				minComponentHeight = Math.max(minDimensions.height, minComponentHeight);
			}

			Dimension maxDimensions = e.getKey().getMaximumSize();
			if (maxDimensions != null) {
				if (big) {
					maxComponentWidth = Math.max(
							(int) Math.floor(
									(maxDimensions.width - (FIRST_BLOCK_SIZE - 1) * vgap) / (double) FIRST_BLOCK_SIZE),
							maxComponentWidth);
				} else {
					maxComponentWidth = Math.max(maxDimensions.width, maxComponentWidth);
				}
				maxComponentHeight = Math.max(maxDimensions.height, maxComponentHeight);
			}

			Dimension preferredDimensions = e.getKey().getPreferredSize();
			if (preferredDimensions != null) {
				if (big) {
					preferredComponentWidth = Math.max(
							(preferredDimensions.width - (FIRST_BLOCK_SIZE - 1) * vgap) / FIRST_BLOCK_SIZE,
							preferredComponentWidth);
				} else {
					preferredComponentWidth = Math.max(preferredDimensions.width, preferredComponentWidth);
				}
				preferredComponentHeight = Math.max(preferredDimensions.height, preferredComponentHeight);
			}
		}

		minComponentWidth = minComponentHeight * COLUMNS + vgap * (COLUMNS - 1);
		minComponentHeight = minComponentHeight * ROWS + vgap * (ROWS - 1);

		maxComponentWidth = maxComponentHeight * COLUMNS + vgap * (COLUMNS - 1);
		maxComponentHeight = maxComponentHeight * ROWS + vgap * (ROWS - 1);

		preferredComponentWidth = preferredComponentWidth * COLUMNS + vgap * (COLUMNS - 1);
		preferredComponentHeight = preferredComponentHeight * ROWS + vgap * (ROWS - 1);
	}

	/**
	 * Help method to add insets of given container to given dimensions object.
	 * 
	 * @param parent the container to take insets from
	 * @param dim    the dimensions without insets
	 * @return newly created dimensions object
	 */
	private Dimension getDimensionWithInset(Container parent, Dimension dim) {
		Insets insets = parent.getInsets();
		int width = insets.right + insets.left + dim.width;
		int height = insets.top + insets.bottom + dim.height;

		return new Dimension(width, height);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		if (sizeUnknown) {
			setSizes();
		}

		return getDimensionWithInset(parent, new Dimension(preferredComponentWidth, preferredComponentHeight));
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		if (sizeUnknown) {
			setSizes();
		}

		return getDimensionWithInset(parent, new Dimension(minComponentWidth, minComponentHeight));
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int width = parent.getWidth() - insets.left - insets.right;
		int height = parent.getHeight() - insets.top - insets.bottom;

		double oneBlockWidth = (width - (COLUMNS - 1) * vgap) / (double) COLUMNS;
		double oneBlockHeight = (height - (ROWS - 1) * vgap) / (double) ROWS;

		for (Entry<Component, RCPosition> e : components.entrySet()) {
			int x1, x2;
			int y1, y2;
			RCPosition position = e.getValue();

			if (position.getRow() == 1 && position.getColumn() == 1) {
				x1 = 0;
				y1 = 0;
				x2 = (int) ((oneBlockWidth + vgap) * FIRST_BLOCK_SIZE) - vgap;
				y2 = (int) oneBlockHeight;
			} else {
				x1 = (int) ((oneBlockWidth + vgap) * (position.getColumn() - 1));
				y1 = (int) ((oneBlockHeight + vgap) * (position.getRow() - 1));
				x2 = (int) ((oneBlockWidth + vgap) * (position.getColumn())) - vgap;
				y2 = (int) ((oneBlockHeight + vgap) * (position.getRow())) - vgap;
			}

			if (position.getColumn() == COLUMNS) {
				x1 = (int) x1;
				x2 = width;
			}
			if (position.getRow() == ROWS) {
				y1 = (int) y1;
				y2 = height;
			}

			e.getKey().setBounds((int) x1 + insets.left, (int) y1 + insets.top, (x2 - x1), (y2 - y1));
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp);
		Objects.requireNonNull(constraints);
		Objects.requireNonNull(comp);

		RCPosition position;
		try {
			position = RCPosition.parse(constraints);
		} catch (IllegalArgumentException e) {
			throw new CalcLayoutException("Illegal constraints given, consider this: " + e.getMessage());
		}

		if (position.getColumn() > COLUMNS || position.getColumn() <= 0 || position.getRow() > ROWS
				|| position.getRow() <= 0
				|| (position.getRow() == 1 && position.getColumn() <= FIRST_BLOCK_SIZE && position.getColumn() != 1)) {
			throw new CalcLayoutException(
					"This component cannot be added because of invalid position constraints given. Invalid position: row - "
							+ position.getRow() + ", column - " + position.getColumn());
		}

		if (components.values().contains(position)) {
			for (Entry<Component, RCPosition> e : components.entrySet()) {
				if (comp == e.getKey()) {
					if (position.equals(e.getValue()))
						return;
				}
			}
			throw new CalcLayoutException("Position already taken!");
		}

		components.put(comp, position);
		sizeUnknown = true;
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		if (sizeUnknown) {
			setSizes();
		}

		return getDimensionWithInset(target, new Dimension(maxComponentWidth, maxComponentHeight));
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		return;
	}

}
