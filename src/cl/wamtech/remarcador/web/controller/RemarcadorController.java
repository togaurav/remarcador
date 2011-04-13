package cl.wamtech.remarcador.web.controller;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cl.wamtech.remarcador.dao.RemarcadorDao;
import cl.wamtech.remarcador.model.CentroCosto;
import cl.wamtech.remarcador.model.Cuenta;
import cl.wamtech.remarcador.model.Remarcador;
import cl.wamtech.remarcador.model.Usuario;
import cl.wamtech.remarcador.model.Variable;
import cl.wamtech.remarcador.util.CustomerContextHolder;
import cl.wamtech.remarcador.util.CustomerType;
import cl.wamtech.remarcador.util.DateUtil;
import cl.wamtech.remarcador.util.Util;

public class RemarcadorController extends MultiActionController {

    protected final Log log = LogFactory.getLog(getClass());

    private RemarcadorDao remarcadorDao;
    
	/**
	 * @return the remarcadorDao
	 */
	public RemarcadorDao getRemarcadorDao() {
		return remarcadorDao;
	}

	/**
	 * @param remarcadorDao the remarcadorDao to set
	 */
	public void setRemarcadorDao(RemarcadorDao remarcadorDao) {
		this.remarcadorDao = remarcadorDao;
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	protected ModelAndView renderJson(final JSONObject json) {
		if (log.isInfoEnabled()) {
			log.info("returning view in json format");
		}		

		return new ModelAndView(new View () {
			
			/* (non-Javadoc)
			 * @see org.springframework.web.servlet.View#render(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
			 */
			@SuppressWarnings("unchecked")
			public void render(Map model, HttpServletRequest req, HttpServletResponse resp) throws Exception {

				resp.setCharacterEncoding("UTF-8");
				resp.getWriter().write(null != json ? json.toString() : "{}");
			}

			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("Login...");
        return new ModelAndView("login/login");
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    public void validarLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("Validar login...");
        String user = ServletRequestUtils.getRequiredStringParameter(request, "usuario");
        String pass = ServletRequestUtils.getRequiredStringParameter(request, "password");
        
        Map<String, Object> criterios = new HashMap<String, Object>();
        criterios.put("usuario", user);
        criterios.put("password", pass);
        
        CustomerContextHolder.clearCustomerType();
        Usuario usuario = (Usuario) this.getRemarcadorDao().getObject(RemarcadorDao.USUARIO_LOGIN, criterios, false);

        if(usuario != null) {
        	HttpSession session = request.getSession(true);
    		session.getServletContext().setAttribute("usuario", usuario);
    		response.sendRedirect("main.htm?perform=remarcador");
        } else {
        	response.sendRedirect("no-tiene-acceso.html");
        }
    }
    
    /**
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws JSONException
     */
    public ModelAndView remarcador(HttpServletRequest request, HttpServletResponse response) throws ServletException, JSONException {
        log.info("Remarcador...");
        HttpSession session = request.getSession(true);
		Usuario usuario = (Usuario)session.getServletContext().getAttribute("usuario");
		
        CustomerContextHolder.clearCustomerType();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("centrosCostos", this.getRemarcadorDao().getCriterioObject(CentroCosto.class, null));
        model.put("cuentas", this.getRemarcadorDao().getCriterioObject(Cuenta.class, null));
        model.put("horas", DateUtil.getHorasOMinutos("HORA").get("resultado"));
        model.put("minutos", DateUtil.getHorasOMinutos("MINUTO").get("resultado"));
        model.put("usuario", Util.beanToJson(usuario, false));
        return new ModelAndView("remarcador/remarcador", model);
    }
    
    /**
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws JSONException
     */
	@SuppressWarnings("unchecked")
	public ModelAndView getRemarcadorCalculado(HttpServletRequest request, HttpServletResponse response) throws ServletException, JSONException {
        log.info("Remarcador calculado...");
        Map<String, Object> criterios = new HashMap<String, Object>();
        JSONArray resultado = new JSONArray();
        
        String fechaInicial = ServletRequestUtils.getRequiredStringParameter(request, "fechaInicial");
        String fechaFinal = ServletRequestUtils.getRequiredStringParameter(request, "fechaFinal");
        int horaFechaInicial = ServletRequestUtils.getRequiredIntParameter(request, "horaFechaInicial");
        int horaFechaFinal = ServletRequestUtils.getRequiredIntParameter(request, "horaFechaFinal");
        int minutosFechaInicial = ServletRequestUtils.getRequiredIntParameter(request, "minutosFechaInicial");
        int minutosFechaFinal = ServletRequestUtils.getRequiredIntParameter(request, "minutosFechaFinal");
        int idCentroCosto = ServletRequestUtils.getIntParameter(request, "idCentroCosto", -1);
        int idCuenta = ServletRequestUtils.getIntParameter(request, "idCuenta", -1);

        JSONObject fechaInicialSeparator = DateUtil.separateDate(fechaInicial)
        									.put("hour", horaFechaInicial).put("minutes", minutosFechaInicial);
        JSONObject fechaFinalSeparator = DateUtil.separateDate(fechaFinal)
        									.put("hour", horaFechaFinal).put("minutes", minutosFechaFinal);
        
        GregorianCalendar calendarFechaInicial = DateUtil.getCustomDateCalendar(fechaInicialSeparator);
        GregorianCalendar calendarFechaFinal = DateUtil.getCustomDateCalendar(fechaFinalSeparator);
        
        Long timeInMillisFechaInicial = (calendarFechaInicial.getTimeInMillis() - 14400000) / 1000;
        Long timeInMillisFechaFinal = (calendarFechaFinal.getTimeInMillis() - 14400000) / 1000;

        criterios.put("fechaInicial", timeInMillisFechaInicial);
        criterios.put("fechaFinal", timeInMillisFechaFinal);
                
        CustomerContextHolder.setCustomerType(CustomerType.WAM_MANAGER);
        List<Map<String, Object>> totalRemarcadoresMedidos = this.getRemarcadorDao().getObjects(RemarcadorDao.REMARCADORES_MEDIDOS_POR_PERIODO, criterios, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
        
        List<Integer> codigosModem = new ArrayList<Integer>();
        List<Integer> idsCanal = new ArrayList<Integer>();

        for(Map<String, Object> map : totalRemarcadoresMedidos) {
        	codigosModem.add(Integer.parseInt(map.get("codigo_modem").toString()));
        	idsCanal.add(Integer.parseInt(map.get("id_canal").toString()));
		}
        
        criterios = new HashMap<String, Object>();
        criterios.put("codigosModem", codigosModem);
        criterios.put("idsCanal", idsCanal);
        criterios.put("centroCostoId", idCentroCosto);
        criterios.put("cuentaId", idCuenta);
        
        JSONArray remarcadoresMedidosCoincidentes = null;
        
        if(!totalRemarcadoresMedidos.isEmpty()) {
        	CustomerContextHolder.clearCustomerType();
            remarcadoresMedidosCoincidentes = Util.listToJsonArray(remarcadorDao.getCriterioObject(Variable.class, criterios), true, Util.REGLA_EXCLUIR_NULL);
            
            if(!remarcadoresMedidosCoincidentes.isEmpty()) {
            	CustomerContextHolder.setCustomerType(CustomerType.WAM_MANAGER);
            	criterios = new HashMap<String, Object>();
            	
            	for(Object object: remarcadoresMedidosCoincidentes) {
            		JSONObject json = new JSONObject(object.toString());
	                criterios.put("codigoModem", json.getInt("codigoModem"));
	                criterios.put("idCanal", json.getInt("idCanal"));
	                
	                criterios.put("fecha", timeInMillisFechaInicial);
	                json.put("remarcadorIncial", this.getRemarcadorDao().getObject(RemarcadorDao.REMARCADOR_CALCULADO, criterios, true));
	                
	                criterios.put("fecha", timeInMillisFechaFinal);
	                json.put("remarcadorFinal", this.getRemarcadorDao().getObject(RemarcadorDao.REMARCADOR_CALCULADO, criterios, true));
	                
	                resultado.add(json);
				}
            }
        }

        return this.renderJson(
        		new JSONObject()
        		.put("resultado", resultado)
        		.put("timeInMillisFechaInicial", timeInMillisFechaInicial)
        		.put("timeInMillisFechaFinal", timeInMillisFechaFinal));
    }
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView getDetalleRemarcador(HttpServletRequest request, HttpServletResponse response) throws ServletException, JSONException {
        log.info("Detalle remarcador...");
        Map<String, Object> criterios = new HashMap<String, Object>();
        criterios.put("idRemarcador", ServletRequestUtils.getRequiredIntParameter(request, "idRemarcador"));
        
        CustomerContextHolder.clearCustomerType();
        Map<String, Object> variable = (Map<String, Object>) this.getRemarcadorDao().getObject(RemarcadorDao.VARIABLE_POR_REMARCADOR, criterios, false);
        
        criterios = new HashMap<String, Object>();
        criterios.put("codigoModem", variable.get("CODIGO_MODEM"));
        criterios.put("idCanal", variable.get("ID_CANAL"));
        criterios.put("fechaInicial", ServletRequestUtils.getRequiredIntParameter(request, "fechaInicial"));
        criterios.put("fechaFinal", ServletRequestUtils.getRequiredIntParameter(request, "fechaFinal"));
       
        CustomerContextHolder.setCustomerType(CustomerType.WAM_MANAGER);
        return this.renderJson(new JSONObject().put("resultado", this.getRemarcadorDao().getObjects(RemarcadorDao.DETALLE_REMARCADOR, criterios, Integer.MIN_VALUE, Integer.MAX_VALUE, true)));
    }
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ModelAndView editarRemarcador(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("Editar remarcador...");
        Remarcador remarcador = (Remarcador)Util.jsonToBean(ServletRequestUtils.getRequiredStringParameter(request, "remarcador"), Remarcador.class);
        this.getRemarcadorDao().creaActualiza(remarcador);
        return this.renderJson(new JSONObject().put("ok", "ok"));
    }
}