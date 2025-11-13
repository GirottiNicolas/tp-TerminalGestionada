package warehouse;

import warehouse.IVisitorReporte;

public interface IElementoVisitable {

	public void accept(IVisitorReporte visitor);
	
}
