public class Position {
	private int x_des;
	private int y_des;

	private int x_src;
	private int y_src;

	public Position(int x_src, int y_src, int x_des, int y_des){
		this.x_src = x_src;
		this.y_src = y_src;
		this.x_des = x_des;
		this.y_des = y_des;
	}

	public int getX_des() {
		return x_des;
	}

	public int getX_src() {
		return x_src;
	}

	public int getY_des() {
		return y_des;
	}

	public int getY_src() {
		return y_src;
	}

	public void setX_des(int x_des) {
		this.x_des = x_des;
	}

	public void setX_src(int x_src) {
		this.x_src = x_src;
	}

	public void setY_des(int y_des) {
		this.y_des = y_des;
	}

	public void setY_src(int y_src) {
		this.y_src = y_src;
	}

	public String toString(){
		return x_src + " " + y_src + "-" + x_des + " " + y_des;
	}
}