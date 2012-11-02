package semiring;

import java.util.ArrayList;
import java.util.List;


class OneBestSemiring implements Semiring {
	
	List<Double> elements;

	Double identity = 1.0;
	
	Double annihilator = 0.0;
	
	OneBestSemiring() {
		elements = new ArrayList<Double>();
	}
	
	List<Double> getElements() {
		return elements;
	}

	void setElements(List<Double> elements) {
		this.elements = elements;
	}

	@Override
	public	double add(List<Double> xList) {
		double max = xList.get(0);
		for (double x : xList) {
			if (max > x) {
				max = x;
			}
		}
		return max;
	}
	
	int argmax(List<Double> xList) {
		int argmax = 0;
		for (int i = 1; i < xList.size(); ++i) {
			if (xList.get(i) > xList.get(argmax)) {
				argmax = i;
			}
		}
		return argmax;
	}

	@Override
	public double multiply(List<Double> xList) {
		double product = 1.0;
		for (double x : xList) {
			product *= x;
		}
		return product;
	}

}
