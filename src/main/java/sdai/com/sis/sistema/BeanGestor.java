package sdai.com.sis.sistema;

import jakarta.faces.application.FacesMessage;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.metadata.ConstraintDescriptor;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.primefaces.PrimeFaces;
import sdai.com.sis.excepciones.ErrorGeneral;
import sdai.com.sis.excepciones.UsuarioCaducado;
import sdai.com.sis.idiomas.TraductorDExcepciones;
import sdai.com.sis.procesosdsesion.IGestorDProcesos;
import sdai.com.sis.procesosdsesion.KProcesosDSesion;
import sdai.com.sis.utilidades.Util;
import sdai.com.sis.validaciones.ExcepcionIntegridad;

/**
 * @date 04/06/2025
 * @author Sergio_M
 * @since VERSIONDCOREENCURSO
 */
public abstract class BeanGestor implements Serializable {

    @Inject
    private IGestorDProcesos gestorDProcesos;
    @Inject
    private TraductorDExcepciones traductorDExcepciones;

    public String procesarAccion(String codigoDAccion) {
        try {
            String codigoDProceso = this.gestorDProcesos.procesarAccion(codigoDAccion);
            this.gestorDProcesos.setCodigoDProceso(codigoDProceso);
            return "/pagina_menu.xhtml";
        } catch (ExcepcionIntegridad ex) {
            StringBuilder stringBuilder = new StringBuilder();
            List<ConstraintViolation<Object>> errores = ex.getErrores();
            for (ConstraintViolation<Object> error : errores) {
                ConstraintDescriptor constraintDescriptor = error.getConstraintDescriptor();
                Map<String, Object> atributos = constraintDescriptor.getAttributes();
                stringBuilder.append(this.traductorDExcepciones.obtenerTraduccion(error.getMessage(), atributos));
                stringBuilder.append("\n");
            }
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Errores de validación", stringBuilder.toString());
            PrimeFaces.current().dialog().showMessageDynamic(facesMessage, true);
            return "";
        } catch (UsuarioCaducado usuarioCaducado) {
            this.gestorDProcesos.setCodigoDProceso(KProcesosDSesion.ProcesosDSesion.PRNEWSECRE);
            return "/pagina.xhtml";
        } catch (ErrorGeneral ex) {
            String codigo = ex.getCodigoDError();
            if (Util.isCadenaNoVacia(codigo)) {
                String[] atributos = ex.getAtributos();
                String mensaje = this.traductorDExcepciones.obtenerTraduccion(codigo, atributos);
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error general", mensaje);
                PrimeFaces.current().dialog().showMessageDynamic(facesMessage, true);
            } else {
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error general", ex.getMessage());
                PrimeFaces.current().dialog().showMessageDynamic(facesMessage, true);
            }
            return "";
        }
    }

}
