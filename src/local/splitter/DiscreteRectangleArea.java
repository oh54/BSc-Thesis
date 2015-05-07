package local.splitter;

public class DiscreteRectangleArea {
	boolean leftConstraint;
	boolean rightConstraint;
	boolean topConstraint;
	boolean bottomConstraint;
	int wr;
	int hr;
	int upperLeftX;
	int upperLeftY;
	int width;
	int height;
	
	
	public DiscreteRectangleArea(int baseWidth, int baseHeight, int wr, int hr, int baseX, int baseY){
		this.height = baseHeight;
		this.width = baseWidth;
		this.wr = wr;
		this.hr = hr;
		this.upperLeftX = baseX;
		this.upperLeftY = baseY;
		this.leftConstraint = false;
		this.rightConstraint = false;
		this.topConstraint = false;
		this.bottomConstraint = false;
	}
	
	public void setLeftConstraint() {
		this.leftConstraint = true;
	}
	public void setRightConstraint() {
		this.rightConstraint = true;
	}
	public void setTopConstraint() {
		this.topConstraint = true;
	}
	public void setBottomConstraint() {
		this.bottomConstraint = true;
	}
	public int getUpperLeftX() {
		return upperLeftX;
	}

	public int getUpperLeftY() {
		return upperLeftY;
	}
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void conformToConstraints(){
		if (!leftConstraint){
			upperLeftX -= wr;
			width += wr;
		}
		if (!topConstraint){
			upperLeftY -= hr;
			height += hr;
		}
		height += !bottomConstraint ? hr : 0;
		width += !rightConstraint ? wr : 0;
	}

	
}
