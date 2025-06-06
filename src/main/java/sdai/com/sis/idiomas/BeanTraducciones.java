package sdai.com.sis.idiomas;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import org.primefaces.component.outputlabel.OutputLabel;

/**
 * @date 04/06/2025
 * @author Sergio_M
 * @since VERSIONDCOREENCURSO
 */
@Named
@ViewScoped
public class BeanTraducciones implements Serializable {

    @Inject
    private TraductorDElementos traductorDElementos;

    public String getTraduccion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent component = UIComponent.getCurrentComponent(facesContext);
        String id = component.getId();
        String traduccion = this.traductorDElementos.obtenerTraduccion(id);
        return traduccion;
    }

    public String getTraduccionLabel() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent component = UIComponent.getCurrentComponent(facesContext);
        if (component instanceof OutputLabel) {
            OutputLabel componente = (OutputLabel) UIComponent.getCurrentComponent(facesContext);
            String id = componente.getFor();
            String traduccion = this.traductorDElementos.obtenerTraduccion(id);
            return traduccion;
        }
        return getTraduccion();
    }

}
