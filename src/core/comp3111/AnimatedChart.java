package core.comp3111;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * 
 */

public class AnimatedChart extends Chart implements Serializable {
	private ArrayList<String> numCols;
	private String x;
	private String y;
	private int timeRange;
	
	public AnimatedChart(DataTable dt) throws ChartException {
		data = dt;
		
	}
	
	/*
	 * Requirements : Line Chart, 1 Numeric Column
	 */
	@Override
	protected boolean isLegal() {
		
		return false;
	}
	
	
}
