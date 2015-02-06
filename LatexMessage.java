
import java.io.Serializable;

public class LatexMessage extends Message implements Serializable {
 	private String expeditor, latex;
	public LatexMessage( String expeditor, String latex ){
		super(expeditor, latex);
                this.latex=latex;
	}
	public String format() {
		return latex;
	}  
}
