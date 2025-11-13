package warehouse;

public interface IVisitorReporte {
	
	public void visitBuque(Buque buque);
    public void visitCarga(Carga carga);

    public String getReporte();
	
}
