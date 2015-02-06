
import java.io.Serializable;

public class Message implements Serializable {
	private String expeditor, mesaj;
	public Message( String expeditor, String mesaj ){
		this.expeditor = expeditor;
		this.mesaj = mesaj;
	}
	public String format() {
		return expeditor + " : " + mesaj;
	}
}