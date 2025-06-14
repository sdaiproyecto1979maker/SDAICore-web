package sdai.com.sis.sistema;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sdai.com.sis.cachesdsistema.IGlobalCaches;
import sdai.com.sis.logs.LogDSistema;
import sdai.com.sis.procesosdsesion.IGestorDProcesos;
import sdai.com.sis.procesosdsesion.rednodal.ProcesoDSesionLocal;
import sdai.com.sis.procesosdsesion.rednodal.ProcesosDSesionLocal;
import sdai.com.sis.sesionesdusuario.ISesionRegistrada;
import sdai.com.sis.utilidades.FacesUtil;

/**
 * @date 29/05/2025
 * @author Sergio_M
 * @since 0.0.0.0-RELEASE
 */
@Named
@RequestScoped
public class BeanIndex {

    @Inject
    private ArranqueDSistema arranqueDSistema;
    @Inject
    private ISesionRegistrada sesionRegistrada;
    @Inject
    private LogDSistema logDSistema;
    @Inject
    private IAtributosDSistema atributosDSistema;
    @Inject
    private IGlobalCaches globalCaches;
    @Inject
    private ProcesosDSesionLocal procesosDSesionLocal;
    @Inject
    private IGestorDProcesos gestorDProcesos;

    @PostConstruct
    public void init() {
        try {
            this.arranqueDSistema.procesarArranque();
            if (!this.sesionRegistrada.isSesionRegistrada()) {
                this.sesionRegistrada.registrarSesion();
                String log = this.sesionRegistrada.generarLogDSistema();
                this.logDSistema.writeMSG(log);
            }
            this.globalCaches.iniciarContenedores();
            String codigoDProceso = this.atributosDSistema.getValorDAtributoString(KSistema.AtributosDSistema.Atributos.PROCINICIO);
            ProcesoDSesionLocal procesoDSesionLocal = this.procesosDSesionLocal.getInstancia(codigoDProceso);
            this.gestorDProcesos.iniciar(procesoDSesionLocal);
            FacesUtil.dispath("/pagina.xhtml");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getIndex() {
        return "";
    }

}
