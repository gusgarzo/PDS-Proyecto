package pds.dao;

public abstract class FactoriaDAO {
	
	
	public static final String DAO_JPA = "pds.dao.JPAFactoriaDAO";

	private static FactoriaDAO unicaInstancia = null;
	
	/**
	 *
	 * Crea un tipo de factoria DAO.
	 * Solo existe el tipo TDSFactoriaDAO
	 */
	public static FactoriaDAO getInstancia(String tipo) throws Exception{
		if (unicaInstancia == null)
			try { 	
				unicaInstancia=(FactoriaDAO) Class.forName(tipo).newInstance();
			} catch (Exception e) {			
		} 
		return unicaInstancia;
	}
	

	public static FactoriaDAO getInstancia() throws Exception{
		return getInstancia(FactoriaDAO.DAO_JPA);
	}

	protected FactoriaDAO (){}
	
	
	public abstract IAdaptadorUsuarioDAO getAdaptadorUsuarioDAO();	
	public abstract IAdaptadorCursoDAO getAdaptadorCursoDAO();
	public abstract IAdaptadorAlumnoDAO getAdaptadorAlumnoDAO();
	public abstract IAdaptadorCreadorCursosDAO getAdaptadorCreadorCursosDAO();
	public abstract IAdaptadorEstadisticasDAO getAdaptadorEstadisticasDAO();
	
	
	
}
