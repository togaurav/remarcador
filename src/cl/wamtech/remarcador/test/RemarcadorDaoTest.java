package cl.wamtech.remarcador.test;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.ServletRequestUtils;

import cl.wamtech.remarcador.dao.RemarcadorDao;
import cl.wamtech.remarcador.model.Usuario;

public class RemarcadorDaoTest
{
    /**
     * Logger for debugging purposes
     */
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RemarcadorDaoTest.class);

    private static ApplicationContext ctx = null;
    private static RemarcadorDao remarcadorDao;
    protected static JdbcTemplate jdbcTemplate;

	static {
//		BasicConfigurator.configure();
		ctx = new ClassPathXmlApplicationContext("remarcador-servlet.xml");
		remarcadorDao = (RemarcadorDao)ctx.getBean("remarcadorDao");
		jdbcTemplate = new JdbcTemplate((org.apache.commons.dbcp.BasicDataSource)ctx.getBean("dataSource"));
	}
    
	/**
	 * @return the jdbcTemplate
	 */
	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		RemarcadorDaoTest.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 
	 * @return
	 */
	public static RemarcadorDao getRemarcadorDao() {
		return remarcadorDao;
	}

	/**
	 * 
	 * @param remarcadorDao
	 */
	public static void setRemarcadorDao(RemarcadorDao remarcadorDao) {
		RemarcadorDaoTest.remarcadorDao = remarcadorDao;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public static void test_getCriterioObject() throws Exception {
		Map<String, Object> criterios = new HashMap<String, Object>();
        JSONObject resultado = new JSONObject();
        
//      criterios.put("fechaInicial", 1281097800);
//      criterios.put("fechaFinal", 1283776200);
//		select * from t_bigint where fecha_ts = 1281626477 and id_canal = 111
//		Map<String, Object> criterios = new HashMap<String, Object>();
//		criterios.put("fechaRegistro", 1281626477);
//		criterios.put("idCanal", 111);
//		System.out.println(Util.listToJsonArray(remarcadorDao.getCriterioObject(Perfil.class, criterios), true, Util.REGLA_EXCLUIR_NULL));
//		System.out.println(Util.listToJsonArray(remarcadorDao.getCriterioObject(Remarcador.class, null), true, Util.REGLA_EXCLUIR_NULL));
//		System.out.println(Util.listToJsonArray(remarcadorDao.getCriterioObject(Variable.class, null), true, Util.REGLA_EXCLUIR_NULL));
        

//        System.out.println(remarcadorDao.getObject(RemarcadorDao.REMARCADOR_CALCULADO, criterios, true));
        
//        List list =  remarcadorDao.getObjects(RemarcadorDao.REMARCADORES_MEDIDOS_POR_PERIODO, criterios, -1, Integer.MAX_VALUE, false);

//        for (int i = 0; i < list.size(); i++) {
//        	Map map = (Map) list.get(i);
//        	System.out.println(map);
//			
//		}
        
//        List<Integer> codigosModem = new ArrayList<Integer>();
//        codigosModem.add(640);
//        List<Integer> idsCanal = new ArrayList<Integer>();
//        idsCanal.add(107);
//        
//
//        criterios.put("codigosModem", codigosModem);
//        criterios.put("idsCanal",idsCanal);
        
//        System.out.println(Util.listToJsonArray(remarcadorDao.getCriterioObject(Variable.class, criterios), true, Util.REGLA_EXCLUIR_NULL));
//		System.out.println(remarcadorDao.getObjects(RemarcadorDao.REMARCADORES_MEDIDOS_POR_PERIODO, criterios, -1, Integer.MAX_VALUE, false).toArray());
//        criterios.put("idRemarcador", 0);
//        System.out.println(remarcadorDao.getObject(RemarcadorDao.VARIABLE_POR_REMARCADOR, criterios, false));
        
//        criterios.put("codigoModem", 638);
//        criterios.put("idCanal", 109);
//        criterios.put("fechaInicial", 1283299200);
//        criterios.put("fechaFinal", 1285891200);
//        System.out.println(remarcadorDao.getObjects(RemarcadorDao.DETALLE_REMARCADOR, criterios, Integer.MIN_VALUE, Integer.MAX_VALUE, true));
        

        criterios.put("usuario", "jguajardo");
        criterios.put("password", "111111");        
        Usuario usuario = (Usuario) remarcadorDao.getObject(RemarcadorDao.USUARIO_LOGIN, criterios, false);
        System.out.println(usuario);
	}
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	RemarcadorDaoTest.test_getCriterioObject();
	}

}