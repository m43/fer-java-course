package hr.fer.zemris.java.hw17.jvdraw.geometry;

/**
 * Class models an observer as in observer design pattern where the subject is
 * modeled by {@link DrawingModel}. This observer tracks the addition, removal
 * and the change of subjects.
 * 
 * @author Frano Rajič
 */
public interface DrawingModelListener {

	/**
	 * One or more objects of the subject given as source have been added to given
	 * index range defined by [index0, index1]. Note that the interval defined by
	 * index0 to index1 is inclusive
	 * 
	 * @param source the source model
	 * @param index0 the starting index of the addition
	 * @param index1 the ending index of the addition
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * One or more objects of the subject given as source have been removed from
	 * given index range defined by [index0, index1]. Note that the interval defined
	 * by index0 to index1 is inclusive
	 * 
	 * @param source the source model
	 * @param index0 the starting index of the removal
	 * @param index1 the ending index of the removal
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * One or more objects of the subject given as source have been changed in given
	 * index range defined by [index0, index1]. Note that the interval defined by
	 * index0 to index1 is inclusive
	 * 
	 * @param source the source model
	 * @param index0 the starting index of the change range
	 * @param index1 the ending index of the change range
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);

}
