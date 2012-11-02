package semiring;

import java.util.List;

interface Semiring {

	/*private List<T> A;
	
	private T identity;
	
	private T annihilator;*/

	/*List<T> getA() {
		return A;
	}

	void setA(List<T> a) {
		A = a;
	}*/
	
	double add(List<Double> d);
	
	double multiply(List<Double> d);
	
	/*T getIdentity() {
		return identity;
	}

	void setIdentity(T identity) {
		this.identity = identity;
	}

	T getAnnihilator() {
		return annihilator;
	}

	void setAnnihilator(T annihilator) {
		this.annihilator = annihilator;
	}*/

}
