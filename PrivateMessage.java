
import java.io.Serializable;

public class PrivateMessage extends Message implements Serializable{
	private String destinatar;
	public PrivateMessage( String expeditor, String mesaj, String destinatar ){
		super(expeditor, mesaj);
		this.destinatar = destinatar;
	}
	public String format() {
		return "(priv)" + super.format();
	}
	public String getDestinatar() {
		return destinatar;
	}
}