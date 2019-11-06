package albanez.mathsolver;

enum Side {
	LEFT, RIGHT;
	
	static Side getOtherSide(Side s) {
		return s == LEFT ? RIGHT : LEFT;
	}
}
